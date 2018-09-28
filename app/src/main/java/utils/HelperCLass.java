package utils;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import javaclasses.Bus;

/**
 * Created by hp on 1/21/2018.
 */

public class HelperCLass {
    public static LatLng destinationLocation = new LatLng(0, 0);
    public static LatLng sourceLocation = new LatLng(0, 0);

    public static String sourceLocationName = "";
    public static String destinationLocationName = "";


    public static Bus selectedBus = new Bus();
    public static ArrayList<Bus> busArrayList = new ArrayList<>();


}
