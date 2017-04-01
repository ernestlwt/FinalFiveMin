package finalcompilation.finalfivemin.clinic;

import java.util.Comparator;

import finalcompilation.finalfivemin.entity.Clinic;

/**
 * Created by Aiqing on 3/13/17.
 */

public class ClinicRatingComparator implements Comparator<Clinic>{

    public ClinicRatingComparator(){}

    @Override
    public int compare(Clinic o1, Clinic o2) {
        double r1 = o1.getRating();
        double r2 = o2.getRating();
        if (r1 == r2)
            return 0;
        else if (r1 > r2){
            return -1;
        }
        else
            return 1;
    }

}
