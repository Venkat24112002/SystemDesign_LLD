package MeetingScheduler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

enum MeetingStatus {
    SCHEDULED, CANCELLED
}

class User {
    private final String email;
    private final String name;

    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() { return email; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return name + " (" + email + ")";
    }
}

class MeetingRoom {
    private final int roomId;
    private final String name;
    private final int capacity;

    public MeetingRoom(int roomId, String name, int capacity) {
        this.roomId = roomId;
        this.name = name;
        this.capacity = capacity;
    }

    public int getRoomId() { return roomId; }
    public String getName() { return name; }
    public int getCapacity() { return capacity; }

    @Override
    public String toString() {
        return name + " (Capacity: " + capacity + ")";
    }
}

class Meeting {
    private final String meetingId;
    private final String title;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final int roomId;
    private final List<String> attendees;
    private final String organizer;
    private MeetingStatus status;

    public Meeting(String meetingId, String title, LocalDateTime startTime,
                   LocalDateTime endTime, int roomId, List<String> attendees, String organizer) {
        this.meetingId = meetingId;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.roomId = roomId;
        this.attendees = new ArrayList<>(attendees);
        this.organizer = organizer;
        this.status = MeetingStatus.SCHEDULED;
    }

    // Core business method in model
    public boolean hasTimeConflict(LocalDateTime start, LocalDateTime end) {
        return this.startTime.isBefore(end) && this.endTime.isAfter(start);
    }

    public boolean isActive() {
        return status == MeetingStatus.SCHEDULED;
    }

    public void cancel() {
        this.status = MeetingStatus.CANCELLED;
    }

    // Getters
    public String getMeetingId() { return meetingId; }
    public String getTitle() { return title; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public int getRoomId() { return roomId; }
    public List<String> getAttendees() { return new ArrayList<>(attendees); }
    public String getOrganizer() { return organizer; }
    public MeetingStatus getStatus() { return status; }

}

// ==================== NOTIFICATION SERVICE ====================
interface NotificationService {
    void notifyMeetingBooked(Meeting meeting);
    void notifyMeetingCancelled(Meeting meeting);
}

class ConsoleNotificationService implements NotificationService {
    @Override
    public void notifyMeetingBooked(Meeting meeting) {
        System.out.println("\n📧 BOOKING CONFIRMATION");
        System.out.println("Meeting: " + meeting.getTitle());
        System.out.println("Time: " + meeting.getStartTime() + " to " + meeting.getEndTime());
        System.out.println("Organizer: " + meeting.getOrganizer());
        System.out.print("Attendees notified: ");
        System.out.println(String.join(", ", meeting.getAttendees()));
        System.out.println();
    }

    @Override
    public void notifyMeetingCancelled(Meeting meeting) {
        System.out.println("\n❌ CANCELLATION NOTICE");
        System.out.println("CANCELLED: " + meeting.getTitle());
        System.out.println("Was scheduled: " + meeting.getStartTime() + " to " + meeting.getEndTime());
        System.out.print("Cancellation sent to: " + meeting.getOrganizer() + ", ");
        System.out.println(String.join(", ", meeting.getAttendees()));
        System.out.println();
    }
}


class MeetingScheduler {
    // Data storage - all in one place
    private final Map<String, Meeting> meetings = new HashMap<>();
    private final Map<Integer, MeetingRoom> rooms = new HashMap<>();
    private final NotificationService notificationService;
    private int meetingCounter = 1;

    public MeetingScheduler(NotificationService notificationService) {
        this.notificationService = notificationService;
        initializeRooms();
    }

    private void initializeRooms() {
        rooms.put(1, new MeetingRoom(1, "Conference Room A", 10));
        rooms.put(2, new MeetingRoom(2, "Meeting Room B", 6));
        rooms.put(3, new MeetingRoom(3, "Small Room C", 4));
        rooms.put(4, new MeetingRoom(4, "Board Room", 12));
    }

    // ==================== CORE BOOKING METHODS ====================
    public String bookMeeting(String title, LocalDateTime startTime, LocalDateTime endTime,
                              String organizer, List<String> attendees, int roomId) {

        // Validation (not necessary for interview)
        validateBookingRequest(title, startTime, endTime, organizer, roomId);

        if (!isRoomAvailable(roomId, startTime, endTime)) {
            throw new IllegalArgumentException("Room is not available for the given time slot");
        }

        // Create and save meeting
        String meetingId = generateMeetingId();
        Meeting meeting = new Meeting(meetingId, title, startTime, endTime,
                roomId, attendees, organizer);
        meetings.put(meetingId, meeting);

        // Notify
        notificationService.notifyMeetingBooked(meeting);

        return meetingId;
    }

    public String bookMeetingAutoRoom(String title, LocalDateTime startTime, LocalDateTime endTime,
                                      String organizer, List<String> attendees, int requiredCapacity) {

        // Find best available room
        Optional<MeetingRoom> bestRoom = getAvailableRooms(startTime, endTime).stream()
                .filter(room -> room.getCapacity() >= requiredCapacity)
                .min(Comparator.comparingInt(MeetingRoom::getCapacity));

        if (bestRoom.isEmpty()) {
            throw new IllegalArgumentException("No suitable room available for " + requiredCapacity + " people");
        }

        return bookMeeting(title, startTime, endTime, organizer, attendees, bestRoom.get().getRoomId());
    }

    public boolean cancelMeeting(String meetingId) {
        Meeting meeting = meetings.get(meetingId);
        if (meeting == null || !meeting.isActive()) {
            return false;
        }

        meeting.cancel();
        notificationService.notifyMeetingCancelled(meeting);
        return true;
    }

    // ==================== QUERY METHODS ====================
    public boolean isRoomAvailable(int roomId, LocalDateTime startTime, LocalDateTime endTime) {
        return meetings.values().stream()
                .filter(Meeting::isActive)
                .filter(meeting -> meeting.getRoomId() == roomId)
                .noneMatch(meeting -> meeting.hasTimeConflict(startTime, endTime));
    }

    public List<MeetingRoom> getAvailableRooms(LocalDateTime startTime, LocalDateTime endTime) {
        return rooms.values().stream()
                .filter(room -> isRoomAvailable(room.getRoomId(), startTime, endTime))
                .collect(Collectors.toList());
    }

    public List<Meeting> getDaySchedule(LocalDate date) {
        return meetings.values().stream()
                .filter(Meeting::isActive)
                .filter(meeting -> meeting.getStartTime().toLocalDate().equals(date))
                .sorted(Comparator.comparing(Meeting::getStartTime))
                .collect(Collectors.toList());
    }

    public List<Meeting> getRoomSchedule(int roomId, LocalDate date) {
        return getDaySchedule(date).stream()
                .filter(meeting -> meeting.getRoomId() == roomId)
                .collect(Collectors.toList());
    }

    public MeetingRoom getRoom(int roomId) {
        return rooms.get(roomId);
    }

    public List<MeetingRoom> getAllRooms() {
        return new ArrayList<>(rooms.values());
    }

    // ==================== HELPER METHODS ====================
    private void validateBookingRequest(String title, LocalDateTime startTime,
                                        LocalDateTime endTime, String organizer, int roomId) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Meeting title is required");
        }

        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("Start and end times are required");
        }

        if (!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }

        if (startTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot schedule meetings in the past");
        }

        if (organizer == null || organizer.trim().isEmpty()) {
            throw new IllegalArgumentException("Organizer is required");
        }

        if (!rooms.containsKey(roomId)) {
            throw new IllegalArgumentException("Room with ID " + roomId + " does not exist");
        }
    }

    private String generateMeetingId() {
        return "MTG-" + (meetingCounter++);
    }
}



public class MeetingSchedulerDemo {
}
