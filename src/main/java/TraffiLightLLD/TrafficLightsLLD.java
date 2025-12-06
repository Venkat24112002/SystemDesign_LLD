package TraffiLightLLD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


enum TrafficSignalLights {
    RED,
    GREEN,
    YELLOW
}

class TrafficLight{
    private TrafficSignalLights state;
    private String direction;

    public TrafficLight(String direction) {
        this.state = TrafficSignalLights.RED;
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    public TrafficSignalLights getTrafficSignalLight() {
        return state;
    }

    public void setTrafficSignalLight(TrafficSignalLights trafficSignalLight) {
        this.state = trafficSignalLight;
    }
}


class Intersection {
    private List<TrafficLight> lights;

    public Intersection(List<String> directions) {
        lights = new ArrayList<>();
        for (String direction : directions) {
            lights.add(new TrafficLight(direction));
        }
    }

    public List<TrafficLight> getLights() {
        return lights;
    }

    public TrafficSignalLights getLightByDirection(String direction) {
        for (TrafficLight trafficLight : lights) {
            if (trafficLight.getDirection().equals(direction)) return trafficLight.getTrafficSignalLight();
        }
        return null;
    }
}

class TrafficLightScheduler {
    private Intersection intersection;
    private Integer greenTime = 3;
    private  Integer yellowTime = 1;

    public TrafficLightScheduler(Intersection intersection) {
        this.intersection = intersection;
    }

    public void startCycle(int times) throws InterruptedException {
        List<TrafficLight> lights = intersection.getLights();
        int n = lights.size();

        for(int i=0;i<times;i++) {
            for(int j=0;j<n;j++) {
                for(TrafficLight light:lights) {
                    light.setTrafficSignalLight(TrafficSignalLights.RED);
                }

                TrafficLight currLight = lights.get(j);
                currLight.setTrafficSignalLight(TrafficSignalLights.GREEN);
                System.out.println("green");
                Thread.sleep(greenTime*1000);
                currLight.setTrafficSignalLight(TrafficSignalLights.YELLOW);
            }
        }
    }
}



public class TrafficLightsLLD {

    public static void main(String[] args) throws InterruptedException {
        List<String> directions = Arrays.asList("NORTH");
        Intersection intersection = new Intersection(directions);
        TrafficLightScheduler trafficLightScheduler = new TrafficLightScheduler(intersection);
        trafficLightScheduler.startCycle(2);
    }
}
