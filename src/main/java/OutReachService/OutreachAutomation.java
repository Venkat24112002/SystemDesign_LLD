package OutReachService;

import java.time.LocalDateTime;
import java.util.List;

/*
 * ============================
 * DOMAIN MODELS
 * ============================
 */

class Customer {
    String id;
    String name;
    String email;

    Customer(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}

class TimeSlot {
    LocalDateTime start;
    LocalDateTime end;

    TimeSlot(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }
}

/*
 * ============================
 * CALENDAR SERVICE (ABSTRACTION)
 * ============================
 */

interface CalendarService {
    List<TimeSlot> getFreeSlots(String organizerId);
    boolean bookSlot(String organizerId, TimeSlot slot, Customer customer);
}

class GoogleCalendarService implements CalendarService {

    @Override
    public List<TimeSlot> getFreeSlots(String organizerId) {
        System.out.println("Checking free slots in Google Calendar for: " + organizerId);
        return List.of(
                new TimeSlot(
                        LocalDateTime.now().plusHours(1),
                        LocalDateTime.now().plusHours(2)
                )
        );
    }

    @Override
    public boolean bookSlot(String organizerId, TimeSlot slot, Customer customer) {
        System.out.println("Booked slot in Google Calendar for " + customer.email);
        return true;
    }
}

/*
 * ============================
 * STRATEGY: EMAIL CONTENT
 * ============================
 */

interface EmailContentStrategy {
    String generate(Customer customer, TimeSlot slot);
}

class SalesEmailStrategy implements EmailContentStrategy {

    @Override
    public String generate(Customer customer, TimeSlot slot) {
        return "Hi " + customer.name + ",\n\n"
                + "We’d love to walk you through our product.\n"
                + "Scheduled demo:\n"
                + slot.start + " to " + slot.end + "\n\n"
                + "Regards,\nSales Team";
    }
}

class RecruitmentEmailStrategy implements EmailContentStrategy {

    @Override
    public String generate(Customer customer, TimeSlot slot) {
        return "Hi " + customer.name + ",\n\n"
                + "We’d like to discuss an opportunity with you.\n"
                + "Interview slot:\n"
                + slot.start + " to " + slot.end + "\n\n"
                + "Regards,\nHiring Team";
    }
}

/*
 * ============================
 * EMAIL SENDING SERVICE
 * ============================
 */

interface EmailService {
    void send(String to, String content);
}

class SmtpEmailService implements EmailService {

    @Override
    public void send(String to, String content) {
        System.out.println("\n--- Sending Email ---");
        System.out.println("To: " + to);
        System.out.println(content);
        System.out.println("--------------------\n");
    }
}

/*
 * ============================
 * ORCHESTRATOR
 * ============================
 */

class OutreachService {

    private final CalendarService calendarService;
    private final EmailService emailService;

    OutreachService(CalendarService calendarService, EmailService emailService) {
        this.calendarService = calendarService;
        this.emailService = emailService;
    }

    public void sendOutreach(
            Customer customer,
            String organizerId,
            EmailContentStrategy emailStrategy   // STRATEGY injected
    ) {

        // 1. Fetch free slots
        List<TimeSlot> slots = calendarService.getFreeSlots(organizerId);
        if (slots.isEmpty()) {
            throw new RuntimeException("No free slots available");
        }

        // 2. Pick slot (can be another strategy later)
        TimeSlot selectedSlot = slots.get(0);

        // 3. Book calendar
        calendarService.bookSlot(organizerId, selectedSlot, customer);

        // 4. Generate content using STRATEGY
        String content = emailStrategy.generate(customer, selectedSlot);

        // 5. Send email
        emailService.send(customer.email, content);
    }
}

/*
 * ============================
 * MAIN RUNNER
 * ============================
 */

public class OutreachAutomation {

    public static void main(String[] args) {

        Customer customer = new Customer(
                "1",
                "Venkat",
                "venkat@gmail.com"
        );

        String organizerId = "sales.john@company.com";

        CalendarService calendarService = new GoogleCalendarService();
        EmailService emailService = new SmtpEmailService();

        OutreachService outreachService =
                new OutreachService(calendarService, emailService);

        // STRATEGY SELECTION (runtime decision)
        EmailContentStrategy strategy = new SalesEmailStrategy();
        // EmailContentStrategy strategy = new RecruitmentEmailStrategy();

        outreachService.sendOutreach(customer, organizerId, strategy);
    }
}


//Check calendar → Pick first slot → Generate fixed email → Send
//Problem:
//Not flexible
//No conversation
//No reasoning
//No personalization
//🔹 Conversational AI System (with LLM Agent)
//Here, LLM is the decision-maker, not the executor.
//
//        Key Principle (VERY IMPORTANT)
//LLM decides WHAT to do, tools decide HOW to do it
//
//🧩 Components in Agentic Design
//1️⃣ User Message
//Example:
//Copy code
//"I want to schedule a demo sometime next week"
//        2️⃣ LLM Agent (Brain 🧠)
//LLM:
//Understands intent
//Extracts constraints
//Decides steps
//Chooses tools
//Example reasoning:
//Intent: BOOK_MEETING
//Need calendar slots
//Then send email
//3️⃣ Tools (Executors 🛠)
//Tools are wrappers over existing services:
//Calendar Tool → uses CalendarService
//Email Tool → uses EmailService
//LLM cannot book calendars directly — tools do it.
//4️⃣ Agent Orchestrator
//Sends user input to LLM
//Parses LLM response
//Invokes tools in order
//Maintains flow
//🔁 Overall Flow (Say this in interview)
//markdown
//Copy code
//User → LLM Agent
//     → LLM reasons & plans
//     → Agent executes tools
//     → Calendar booked
//     → Email sent
/*
 * ============================
 * TOOL INTERFACE (AGENT TOOLS)
 * ============================
 */

//interface Tool {
//    String name();
//    String execute(String input);
//}
//
///*
// * ============================
// * CALENDAR TOOL
// * ============================
// */
//
//class CalendarTool implements Tool {
//
//    private final CalendarService calendarService;
//    private final Customer customer;
//    private final String organizerId;
//
//    CalendarTool(CalendarService calendarService, Customer customer, String organizerId) {
//        this.calendarService = calendarService;
//        this.customer = customer;
//        this.organizerId = organizerId;
//    }
//
//    @Override
//    public String name() {
//        return "calendar_tool";
//    }
//
//    @Override
//    public String execute(String input) {
//        List<TimeSlot> slots = calendarService.getFreeSlots(organizerId);
//        TimeSlot selected = slots.get(0);
//        calendarService.bookSlot(organizerId, selected, customer);
//        return "Meeting booked at " + selected;
//    }
//}
//
///*
// * ============================
// * EMAIL TOOL
// * ============================
// */
//
//class EmailTool implements Tool {
//
//    private final EmailService emailService;
//    private final Customer customer;
//
//    EmailTool(EmailService emailService, Customer customer) {
//        this.emailService = emailService;
//        this.customer = customer;
//    }
//
//    @Override
//    public String name() {
//        return "email_tool";
//    }
//
//    @Override
//    public String execute(String input) {
//        emailService.send(customer.email, input);
//        return "Email sent";
//    }
//}
//
///*
// * ============================
// * LLM CLIENT (MOCK)
// * ============================
// */
//
//interface LLMClient {
//    AgentPlan reason(String userMessage);
//}
//
//class MockLLMClient implements LLMClient {
//
//    @Override
//    public AgentPlan reason(String userMessage) {
//        // Simulated LLM reasoning
//        List<ToolCall> steps = new ArrayList<>();
//        steps.add(new ToolCall("calendar_tool", "book meeting"));
//        steps.add(new ToolCall("email_tool", "Your demo has been scheduled successfully."));
//        return new AgentPlan(steps);
//    }
//}
//
///*
// * ============================
// * AGENT DATA STRUCTURES
// * ============================
// */
//
//class ToolCall {
//    String toolName;
//    String input;
//
//    ToolCall(String toolName, String input) {
//        this.toolName = toolName;
//        this.input = input;
//    }
//}
//
//class AgentPlan {
//    List<ToolCall> toolCalls;
//
//    AgentPlan(List<ToolCall> toolCalls) {
//        this.toolCalls = toolCalls;
//    }
//}
//
///*
// * ============================
// * LLM AGENT (CORE)
// * ============================
// */
//
//class OutreachAgent {
//
//    private final LLMClient llmClient;
//    private final Map<String, Tool> tools = new HashMap<>();
//
//    OutreachAgent(LLMClient llmClient, List<Tool> toolList) {
//        this.llmClient = llmClient;
//        for (Tool tool : toolList) {
//            tools.put(tool.name(), tool);
//        }
//    }
//
//    public void handleMessage(String userMessage) {
//
//        System.out.println("User: " + userMessage);
//
//        AgentPlan plan = llmClient.reason(userMessage);
//
//        for (ToolCall call : plan.toolCalls) {
//            Tool tool = tools.get(call.toolName);
//            tool.execute(call.input);
//        }
//    }
//}
