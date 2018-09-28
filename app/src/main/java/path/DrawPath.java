package path;

import android.graphics.Color;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javaclasses.GPSDistanceTime;
import javaclasses.Station;
//import com.google.android.maps.GeoPoint;
//import com.google.gson.JsonObject;

public class DrawPath {

    // make Maps URL
    public static String makeURLWalking(double sourcelat, double sourcelog,
                                        double destlat, double destlog) {
        StringBuilder urlString = new StringBuilder();
        urlString.append("http://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");// from
        urlString.append(Double.toString(sourcelat));
        urlString.append(",");
        urlString.append(Double.toString(sourcelog));
        urlString.append("&destination=");// to
        urlString.append(Double.toString(destlat));
        urlString.append(",");
        urlString.append(Double.toString(destlog));
        urlString.append("&sensor=false&mode=walking&alternatives=true");
        return urlString.toString();
    }


    public static String makeURLDriving(double sourcelat, double sourcelog,
                                        double destlat, double destlog) {
        StringBuilder urlString = new StringBuilder();
        urlString.append("http://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");// from
        urlString.append(Double.toString(sourcelat));
        urlString.append(",");
        urlString.append(Double.toString(sourcelog));
        urlString.append("&destination=");// to
        urlString.append(Double.toString(destlat));
        urlString.append(",");
        urlString.append(Double.toString(destlog));
        urlString.append("&sensor=false&mode=driving&alternatives=true");
        return urlString.toString();
    }


//    public void findDistanceAndDuration(String result) {
//
//        // Tranform the string into a json object
//        Log.v("distance", result);
//        try {
//            final JSONObject json = new JSONObject(result);
//            JSONArray routeArray = json.getJSONArray("routes");
//            JSONObject routes = routeArray.getJSONObject(0);
//
//            Log.v("wasl", "1");
//            JSONArray legs = routes.getJSONArray("legs");
//            Log.v("wasl", "2");
//            JSONObject distanceObject = legs.getJSONObject(0);
//            Log.v("wasl", "3");
//
//            JSONObject distance = distanceObject.getJSONObject("distance");
//            Log.v("distance equal", distance.getString("text"));
//
//            JSONObject duration = distanceObject.getJSONObject("duration");
//            Log.v("duration equal", duration.getString("text"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    public GPSDistanceTime findDistanceAndDuration(String result) {

        // Tranform the string into a json object
        Log.v("distance", result);
        try {
            final JSONObject json = new JSONObject(result);
            JSONArray routeArray = json.getJSONArray("routes");
            JSONObject routes = routeArray.getJSONObject(0);

            Log.v("wasl", "1");
            JSONArray legs = routes.getJSONArray("legs");
            Log.v("wasl", "2");
            JSONObject distanceObject = legs.getJSONObject(0);
            Log.v("wasl", "3");

            JSONObject distance = distanceObject.getJSONObject("distance");
            Log.v("distance equal", distance.getString("text"));

            JSONObject duration = distanceObject.getJSONObject("duration");
            Log.v("duration equal", duration.getString("text"));

            return new GPSDistanceTime(distance.getString("text"), duration.getString("text"));
        } catch (JSONException e) {


            return null;

        }
    }

    public String findDistance(String result) {

        // Tranform the string into a json object
        Log.v("distance", result);
        try {
            final JSONObject json = new JSONObject(result);
            JSONArray routeArray = json.getJSONArray("routes");
            JSONObject routes = routeArray.getJSONObject(0);

            Log.v("wasl", "1");
            JSONArray legs = routes.getJSONArray("legs");
            Log.v("wasl", "2");
            JSONObject distanceObject = legs.getJSONObject(0);
            Log.v("wasl", "3");

            JSONObject distance = distanceObject.getJSONObject("distance");
            Log.v("distance equal", distance.getString("text"));
            return distance.getString("text");

        } catch (JSONException e) {
            e.printStackTrace();
            return "-1";
        }
    }

    // draw path between two points
    public void drawPath(String result, GoogleMap mMap) {

        try {
            // Tranform the string into a json object
            Log.v("distance", result);
            final JSONObject json = new JSONObject(result);
            JSONArray routeArray = json.getJSONArray("routes");
            JSONObject routes = routeArray.getJSONObject(0);

            Log.v("wasl", "1");
            JSONArray legs = routes.getJSONArray("legs");
            Log.v("wasl", "2");
            JSONObject distanceObject = legs.getJSONObject(0);
            Log.v("wasl", "3");

            JSONObject distance = distanceObject.getJSONObject("distance");
            Log.v("distance equal", distance.getString("text"));

            JSONObject duration = distanceObject.getJSONObject("duration");
            Log.v("duration equal", duration.getString("text"));

            // Log.v("distance equal", distanceSize);
            JSONObject overviewPolylines = routes
                    .getJSONObject("overview_polyline");

            String encodedString = overviewPolylines.getString("points");
            List<LatLng> list = decodePoly(encodedString);

            for (int z = 0; z < list.size() - 1; z++) {

                LatLng src = list.get(z);
                LatLng dest = list.get(z + 1);
                // Log.v("points ", src.latitude + " , " + src.longitude);
                // Log.v("map", mMap.toString());
                // Log.v("points ", dest.latitude + " , " + dest.longitude);
                Polyline line = mMap.addPolyline(new PolylineOptions()
                        .add(new LatLng(src.latitude, src.longitude),
                                new LatLng(dest.latitude, dest.longitude))
                        .width(10).color(Color.BLUE).geodesic(true));
            }

        } catch (JSONException e) {

        }
    }

    // /////////////////////////////////////
    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    // /************************************ calculate distance
    // ******************************************/
    // public double CalculationByDistance(GeoPoint StartP, GeoPoint EndP) {
    // int Radius = 6371;// radius of earth in Km
    // double lat1 = StartP.getLatitudeE6() / 1E6;
    // double lat2 = EndP.getLatitudeE6() / 1E6;
    // double lon1 = StartP.getLongitudeE6() / 1E6;
    // double lon2 = EndP.getLongitudeE6() / 1E6;
    // double dLat = Math.toRadians(lat2 - lat1);
    // double dLon = Math.toRadians(lon2 - lon1);
    // double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
    // + Math.cos(Math.toRadians(lat1))
    // * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
    // * Math.sin(dLon / 2);
    // double c = 2 * Math.asin(Math.sqrt(a));
    // double valueResult = Radius * c;
    // double km = valueResult / 1;
    // DecimalFormat newFormat = new DecimalFormat("####");
    // int kmInDec = Integer.valueOf(newFormat.format(km));
    // double meter = valueResult % 1000;
    // int meterInDec = Integer.valueOf(newFormat.format(meter));
    // Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
    // + " Meter   " + meterInDec);
    //
    // return Radius * c;
    // }

//    public static double CalculationByDistance(LatLng StartP, LatLng EndP) {
//        double theta = StartP.longitude - EndP.longitude;
//        double dist = Math.sin(deg2rad(StartP.latitude))
//                * Math.sin(deg2rad(EndP.latitude))
//                + Math.cos(deg2rad(StartP.latitude))
//                * Math.cos(deg2rad(EndP.latitude))
//                * Math.cos(deg2rad(theta));
//        dist = Math.acos(dist);
//        dist = rad2deg(dist);
//        dist = dist * 60 * 1.1515;
//        Log.i("distance", "CalculationByDistance: " + dist);
//        return (dist);
//    }


    public static double CalculationByDistance(LatLng StartP, LatLng EndP) {
        double theta = StartP.longitude - EndP.longitude;
        double dist = Math.sin(deg2rad(StartP.latitude))
                * Math.sin(deg2rad(EndP.latitude))
                + Math.cos(deg2rad(StartP.latitude))
                * Math.cos(deg2rad(EndP.latitude))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344; // km

        Log.i("distance", "CalculationByDistance: " + dist);
        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

//	public static double CalculationByDistance(LatLng StartP, LatLng EndP) {
//		int Radius = 6371;// radius of earth in Km
//
//		double lat1 = StartP.latitude;
//		double lat2 = EndP.latitude;
//		double lon1 = StartP.longitude;
//		double lon2 = EndP.longitude;
//		double dLat = Math.toRadians(lat2 - lat1);
//		double dLon = Math.toRadians(lon2 - lon1);
//		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
//				+ Math.cos(Math.toRadians(lat1))
//				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
//				* Math.sin(dLon / 2);
//		double c = 2 * Math.asin(Math.sqrt(a));
//		double valueResult = Radius * c;
//		double km = valueResult / 1;
//		DecimalFormat newFormat = new DecimalFormat("####");
//		int kmInDec = Integer.valueOf(newFormat.format(km));
//		double meter = valueResult % 1000;
//		int meterInDec = Integer.valueOf(newFormat.format(meter));
//		Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
//				+ " Meter   " + meterInDec);
//
////		return Radius * c;
//		return valueResult;
//	}

    /************ get the smallest distance *********************/
    public static int getSmallestDistanceIndex(ArrayList<Station> points, String source) {
        Station currentPoint;
        int minIndex = -1;
        double minDistance = 10000000;

        for (int i = 0; i < points.size() - 1; i++) {
            currentPoint = points.get(i);


            if (source.equalsIgnoreCase("source")) {
                if (currentPoint.getSourceDistance() < minDistance) {
                    minIndex = i;
                    minDistance = currentPoint.getSourceDistance();
                }
            } else {
                if (currentPoint.getDestinationDestance() < minDistance) {
                    minIndex = i;
                    minDistance = currentPoint.getDestinationDestance();
                }


            }

        }
        return minIndex;
    }

//    /************ get the smallest distance *********************/
//    public static int getSmallestDistanceIndex(ArrayList<LatLng> points,
//                                               LatLng currentLocation) {
//        LatLng currentPoint, nextPoint;
//        int minIndex = -1;
//        for (int i = 0; i < points.size() - 1; i++) {
//            currentPoint = points.get(i);
//            nextPoint = points.get(i + 1);
//            if (CalculationByDistance(currentLocation, currentPoint) < CalculationByDistance(
//                    currentLocation, nextPoint))
//                minIndex = i;
//            else
//                minIndex = i + 1;
//
//        }
//        return minIndex;
//    }

    /************ get the smallest distance *********************/
    public static int getSmallestDistanceIndex(ArrayList<LatLng> points,
                                               LatLng currentLocation) {
        LatLng currentPoint;
        int minIndex = -1;
        double minDistance = 10000000;

        for (int i = 0; i < points.size(); i++) {
            currentPoint = points.get(i);
            if (CalculationByDistance(currentLocation, currentPoint) < minDistance) {
                minIndex = i;
                minDistance = CalculationByDistance(currentLocation, currentPoint);
            }

        }
        return minIndex;
    }
}
