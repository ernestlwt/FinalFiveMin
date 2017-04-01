package finalcompilation.finalfivemin.clinic;


import android.content.Context;

import java.util.ArrayList;

import finalcompilation.finalfivemin.database.ClinicDatabaseHelper;
import finalcompilation.finalfivemin.entity.ClinicReview;

/**
 * Created by Aiqing on 3/13/17.
 */

public class ClinicReviewMgr {

    ClinicDatabaseHelper clinicDb;
    private ArrayList<ClinicReview> reviews;


    public ClinicReviewMgr(){}

    public ClinicReviewMgr(int clinicID, Context ctx){
        clinicDb = new ClinicDatabaseHelper(ctx);
        reviews = new ArrayList<>();
        reviews.addAll(clinicDb.getSelectedReview(clinicID));
    }

    // getters and setters

    public ArrayList<ClinicReview> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<ClinicReview> reviews) {
        this.reviews = reviews;
    }
}
