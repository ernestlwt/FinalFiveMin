package finalcompilation.finalfivemin.entity;

/**
 * Created by Aiqing on 3/13/17.
 */

public class ClinicReview {

    private int clinicID;
    private int rating;
    private int userID;
    private String review;

    public ClinicReview(){};

    public ClinicReview(int id, int r, int userid, String review){
        this.clinicID = id;
        this.rating = r;
        this.userID = userid;
        this.review = review;
    }

    public int getClinicID() {
        return clinicID;
    }

    public void setClinicID(int id) {
        this.clinicID = id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
