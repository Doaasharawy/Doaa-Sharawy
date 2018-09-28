package javaclasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hp on 12/22/2017.
 */

public class Bus {
    private String name;
    ArrayList<Station> stations = new ArrayList<>();

    public Bus() {
    }

    public Bus(String name, ArrayList<Station> stations) {
        this.name = name;
        this.stations = stations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Station> getStations() {
        return stations;
    }

    public void setStations(ArrayList<Station> stations) {
        this.stations = stations;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("stations", stations);

        return result;
    }

}
