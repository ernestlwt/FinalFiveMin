package finalcompilation.finalfivemin.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.util.ArrayList;

import finalcompilation.finalfivemin.clinic.ClinicKmlProcessor;
import finalcompilation.finalfivemin.entity.Clinic;
import finalcompilation.finalfivemin.entity.ClinicReview;

/**
 * Created by Aiqing on 3/18/17.
 */

public class ClinicDatabaseHelper extends SQLiteOpenHelper {


    // ============Maybe you need to change the DATABASE_NAME during integration??
    private static final String DATABASE_NAME = "Clinic.db";

    private static final String TAG = "ClinicDatabaseHelper";

    private static final String CLINIC_TABLE = "clinic_table";
    private static final String COL1_1 = "CLINICID";
    private static final String COL1_2 = "CLINICNAME";
    private static final String COL1_3 = "CLINICADDRESS";
    private static final String COL1_4 = "CLINICLAT";
    private static final String COL1_5 = "CLINICLNG";
    private static final String COL1_6 = "CLINICRATING";
    private static final String COL1_7 = "CLINICNUMOFRATING";
    private static final String COL1_8 = "CLINICCONTACT";

    private static final String REVIEW_TABLE = "review_table";
    private static final String COL2_1 = "CLINICID";
    private static final String COL2_2 = "USERID";
    private static final String COL2_3 = "REVIEW";
    private static final String COL2_4 = "RATING";

    private static final String USER_FAVCLINIC_TABLE = "favclinic_table";
    private static final String COL3_1 = "USERID";
    private static final String COL3_2 = "CLINICID";


    //variable introduced for quick fix
    private Context context;

    public ClinicDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + CLINIC_TABLE + " (CLINICID INTEGER, CLINICNAME TEXT, CLINICADDRESS TEXT, CLINICLAT REAL, CLINICLNG REAL, CLINICRATING REAL, CLINICNUMOFRATING INT, CLINICCONTACT TEXT)");
        db.execSQL("CREATE TABLE " + REVIEW_TABLE + " (CLINICID INTEGER, USERID INTEGER, REVIEW TEXT, RATING INTEGER)");
        db.execSQL("CREATE TABLE " + USER_FAVCLINIC_TABLE + " (USERID INTEGER, CLINICID INTEGER) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CLINIC_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + REVIEW_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + USER_FAVCLINIC_TABLE);
        onCreate(db);
    }

    public void addClinic(Clinic c) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL1_1, c.getID());
        values.put(COL1_2, c.getClinicName());
        values.put(COL1_3, c.getAddress());
        LatLng location = c.getLocation();
        double lat = location.latitude;
        double lng = location.longitude;
        values.put(COL1_4, lat);
        values.put(COL1_5, lng);
        values.put(COL1_6, c.getRating());
        values.put(COL1_7, c.getNumOfRating());
        values.put(COL1_8, c.getContact());

        // Inserting Row
        db.insert(CLINIC_TABLE, null, values);
        db.close();
    }

    public void addReview(ClinicReview review) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL2_1, review.getClinicID());
        values.put(COL2_2, review.getUserID());
        values.put(COL2_3, review.getReview());
        values.put(COL2_4, review.getRating());

        // Inserting Row
        db.insert(REVIEW_TABLE, null, values);

        db.close();
    }

    // for debugging purpose
    public int getReviewCount() {
        String countQuery = "SELECT  * FROM " + REVIEW_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    public void addFavorite(int clinicID, int userID){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL3_1, userID);
        values.put(COL3_2, clinicID);

        // Inserting Row
        db.insert(USER_FAVCLINIC_TABLE, null, values);
        db.close();
    }

    public ArrayList<Clinic> getAllDbClinics(){
        ArrayList<Clinic> clinics = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + CLINIC_TABLE , null);
        if (cursor != null){
            if(cursor.moveToFirst()){
            do {
                Clinic c = new Clinic();
                int id = cursor.getInt(cursor.getColumnIndex("CLINICID"));
                c.setID(id);
                String name = cursor.getString(cursor.getColumnIndex("CLINICNAME"));
                c.setClinicName(name);
                String address = cursor.getString(cursor.getColumnIndex("CLINICADDRESS"));
                c.setAddress(address);
                double lat = cursor.getDouble(cursor.getColumnIndex("CLINICLAT"));
                double lng = cursor.getDouble(cursor.getColumnIndex("CLINICLNG"));
                LatLng location = new LatLng(lat,lng);
                c.setLocation(location);
                double rating = cursor.getDouble(cursor.getColumnIndex("CLINICRATING"));
                c.setRating(rating);
                int numOfRating = cursor.getInt(cursor.getColumnIndex("CLINICNUMOFRATING"));
                c.setNumOfRating(numOfRating);
                String contact  = cursor.getString(cursor.getColumnIndex("CLINICCONTACT"));
                c.setContact(contact);
                clinics.add(c);


            } while(cursor.moveToNext());}
            db.close();
        return clinics;
        }
        db.close();
        return null;
    }


    public ArrayList<Integer> getSavedClinicIDs(int userID){
        ArrayList<Integer> clinicIDs = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + USER_FAVCLINIC_TABLE  + " where USERID = " + userID, null);
        if (cursor != null){
            if(cursor.moveToFirst()){
                do{
                    Integer id = (Integer)cursor.getInt(cursor.getColumnIndex("CLINICID"));
                    clinicIDs.add(id);
                }while(cursor.moveToNext());}
            db.close();
            return clinicIDs;
            }
        db.close();
        ArrayList<Integer> empty = new ArrayList<>();
        return empty;
    }

        public ArrayList<Clinic> getSavedClinics(ArrayList<Integer> ids){
            ArrayList<Clinic> clinics = new ArrayList<>();
            ArrayList<Integer> IDs = new ArrayList<>();
            IDs.addAll(ids);
            SQLiteDatabase db = this.getWritableDatabase();
            for (Integer id: IDs){
            Cursor cursor = db.rawQuery("SELECT * FROM " + CLINIC_TABLE  + " where CLINICID = " + id, null);
                if (cursor != null){
                    if(cursor.moveToFirst()){
                        Clinic c = new Clinic();
                        int ID = cursor.getInt(cursor.getColumnIndex("CLINICID"));
                        c.setID(ID);
                        String name = cursor.getString(cursor.getColumnIndex("CLINICNAME"));
                        c.setClinicName(name);
                        String address = cursor.getString(cursor.getColumnIndex("CLINICADDRESS"));
                        c.setAddress(address);
                        double lat = cursor.getDouble(cursor.getColumnIndex("CLINICLAT"));
                        double lng = cursor.getDouble(cursor.getColumnIndex("CLINICLNG"));
                        LatLng location = new LatLng(lat,lng);
                        c.setLocation(location);
                        double rating = cursor.getDouble(cursor.getColumnIndex("CLINICRATING"));
                        c.setRating(rating);
                        int numOfRating = cursor.getInt(cursor.getColumnIndex("CLINICNUMOFRATING"));
                        c.setNumOfRating(numOfRating);
                        String contact  = cursor.getString(cursor.getColumnIndex("CLINICCONTACT"));
                        c.setContact(contact);
                        clinics.add(c);
                    }
                }
                cursor.close();
            }
            db.close();
            if (clinics.size() != 0)
                return clinics;
            else {
                ArrayList<Clinic> emptylist = new ArrayList<>();
                return emptylist;
            }
        }



    public ArrayList<ClinicReview> getSelectedReview(int clinicID){
        ArrayList<ClinicReview> reviews = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor d = db.rawQuery("SELECT * FROM " + REVIEW_TABLE + " where CLINICID = " + clinicID, null);

        if (d.getCount() == 0){
            System.out.println("cursor zero size");
        }
        else
            System.out.println("cursor size not zero");
        if (d != null)
            if(d.moveToFirst()){
                do{
                    ClinicReview review = new ClinicReview();
                    review.setClinicID(clinicID);
                    int userID  = d.getInt(d.getColumnIndex("USERID"));
                    review.setUserID(userID);
                    String content = d.getString(d.getColumnIndex("REVIEW"));
                    review.setReview(content);
                    int rating = d.getInt(d.getColumnIndex("RATING"));
                    review.setRating(rating);
                    reviews.add(review);
                } while(d.moveToNext());
            }
        db.close();
        return reviews;
    }


    public void setInitialClinics (){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Clinic> clinics = new ArrayList<Clinic>();

        clinics = ClinicKmlProcessor.kmlToClinic(context, "MOH_CHAS_CLINICS.kml");
        // CHANGE TO YOUR DIRECTORY FOR ABOVE LINE

        for (Clinic clinic: clinics){
            addClinic(clinic);
        }
        db.close();
    }

    public void clinicUpdateRating(int clinicID, int ratingAdded){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + CLINIC_TABLE + " where CLINICID = "+clinicID, null);
        if (c != null) {
            if (c.moveToFirst()) {
                double old_rating = c.getDouble(c.getColumnIndex("CLINICRATING"));
                int numOfRating = c.getInt(c.getColumnIndex("CLINICNUMOFRATING"));
                double new_rating = (old_rating * numOfRating + ratingAdded) / (numOfRating + 1);
                String strSQL1 = "UPDATE CLINIC_TABLE SET CLINICRATING = " + new_rating + " WHERE CLINICID = " + clinicID;
                String strSQL2 = "UPDATE CLINIC_TABLE SET CLINICNUMOFRATING = " + (numOfRating + 1) + " WHERE CLINICID = " + clinicID;
                db.execSQL(strSQL1);
                db.execSQL(strSQL2);
        }}
        db.close();
    }

}
