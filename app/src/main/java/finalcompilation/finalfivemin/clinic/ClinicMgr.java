package finalcompilation.finalfivemin.clinic;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import finalcompilation.finalfivemin.database.ClinicDatabaseHelper;
import finalcompilation.finalfivemin.entity.Clinic;

/**
 * Created by Aiqing on 2/27/17.
 */

public class ClinicMgr {
    private ArrayList<Clinic> clinics;
    ClinicDatabaseHelper clinicDb;

    public ClinicMgr(Context ctx){
        clinicDb = new ClinicDatabaseHelper(ctx);
        clinics = new ArrayList<>();
        ArrayList<Clinic> clinicBuffer = new ArrayList<>();
        clinicBuffer.addAll(clinicDb.getAllDbClinics());

        // check whether the database for clinic has already been initiated
        if (clinicBuffer == null || clinicBuffer.size() == 0){
            Log.d("1","db is empty");
            clinicDb.setInitialClinics();
            clinicBuffer.addAll(clinicDb.getAllDbClinics());
        }
        Log.d("2","db not empty");
        clinics.addAll(clinicBuffer);
    }


    public void AddClinic(Clinic n){
        clinics.add(n);
        clinicDb.addClinic(n);

    }



    //========= Getter and Setter =========

    public ArrayList<Clinic> getClinics() {
        return clinics;
    }

    public void setClinics(ArrayList<Clinic> clinics) {
        this.clinics = clinics;
    }
}

