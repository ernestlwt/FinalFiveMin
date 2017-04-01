package finalcompilation.finalfivemin.clinic;

import java.util.Comparator;

import finalcompilation.finalfivemin.entity.Clinic;

/**
 * Created by Aiqing on 3/13/17.
 */

public class ClinicNameComparator implements Comparator<Clinic>{

    public ClinicNameComparator(){}

    @Override
    public int compare(Clinic o1, Clinic o2) {
        String name1 = o1.getClinicName();
        String name2 = o2.getClinicName();
        if (name1.equals(name2))
            return 0;
        else if (name1.compareToIgnoreCase(name2) < 0){
            return -1;
        }
        else
            return 1;
    }

}
