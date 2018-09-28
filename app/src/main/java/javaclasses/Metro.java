package javaclasses;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hp on 12/22/2017.
 */

public class Metro {
    private String name,longitude, latitude,lineNum;

    public Metro() {
    }

    public Metro(String name, String longitude, String latitude, String lineNum) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.lineNum = lineNum;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLineNum() {
        return lineNum;
    }

    public void setLineNum(String lineNum) {
        this.lineNum = lineNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("longitude", longitude);
        result.put("latitude", latitude);
        result.put("lineNum", lineNum);

        return result;
    }

}
