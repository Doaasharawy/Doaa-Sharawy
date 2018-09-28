package javaclasses;

/**
 * Created by hp on 12/22/2017.
 */

public class Station {

    private String name, longitude, latitude;
    double sourceDistance, destinationDestance;

    public Station() {
    }

    public Station(String name, String longitude, String latitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public double getSourceDistance() {
        return sourceDistance;
    }

    public void setSourceDistance(double sourceDistance) {
        this.sourceDistance = sourceDistance;
    }

    public double getDestinationDestance() {
        return destinationDestance;
    }

    public void setDestinationDestance(double destinationDestance) {
        this.destinationDestance = destinationDestance;
    }
}
