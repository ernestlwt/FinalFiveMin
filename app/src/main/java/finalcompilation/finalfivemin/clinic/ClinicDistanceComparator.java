package finalcompilation.finalfivemin.clinic;

import com.google.android.gms.maps.model.LatLng;

import java.util.Comparator;

import finalcompilation.finalfivemin.entity.Clinic;

public class ClinicDistanceComparator implements Comparator<Clinic> {
    private LatLng poi; //point of interest
    private static final int RADIUS_OF_EARTH = 6371;


    public ClinicDistanceComparator(LatLng poi){
        this.poi = poi;
    }

    @Override
    public int compare(Clinic c1, Clinic c2) {
        double distanceA = calculateDistance(c1.getLocation());
        double distanceB = calculateDistance(c2.getLocation());
        if(distanceA < distanceB)
            return -1;
        else if (distanceA == distanceB)
            return 0;
        else
            return 1;
    }

    public double calculateDistance(LatLng a) {

        Double latDistance = Math.toRadians(a.latitude - poi.latitude);
        Double lonDistance = Math.toRadians(a.longitude - poi.longitude);
        Double b = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(poi.latitude)) * Math.cos(Math.toRadians(a.latitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(b), Math.sqrt(1 - b));

        double distance = RADIUS_OF_EARTH * c * 1000; // convert to meters, remove if not needed
        distance = Math.pow(distance, 2);
        return Math.sqrt(distance);
    }
}
