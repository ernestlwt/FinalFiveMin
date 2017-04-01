package finalcompilation.finalfivemin.clinic;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;


import java.util.ArrayList;

import finalcompilation.finalfivemin.BottomNavigationViewHelper;
import finalcompilation.finalfivemin.Constants;
import finalcompilation.finalfivemin.R;
import finalcompilation.finalfivemin.UserMainActivity;
import finalcompilation.finalfivemin.article.ArticleMainActivity;
import finalcompilation.finalfivemin.database.ClinicDatabaseHelper;
import finalcompilation.finalfivemin.entity.ClinicReview;
import finalcompilation.finalfivemin.firstaid.FirstAidMainActivity;
import finalcompilation.finalfivemin.forum.ForumMain;

public class ClinicWriteReview extends AppCompatActivity {

    private static Button submitReviewButton;
    private static EditText clinicReview;
    private static RatingBar clinicRating;
    private BottomNavigationView bottomNavigationView;
    ClinicDatabaseHelper clinicDb;
    int clinicID;
    int userID;
    ArrayList<ClinicReview> reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clinic_write_review);
        clinicDb = new ClinicDatabaseHelper(this);

        userID = getIntent().getIntExtra(Constants.USER_ID_INTENT,0);
        clinicID = getIntent().getIntExtra("clinicid",0);
        onButtonClickListenerForReview();

        //bottom nav bar
        final Context myContext = this;
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_clinic);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Intent nextIntent;
                        switch (item.getItemId()) {
                            case R.id.bottom_nav_clinic:
                                nextIntent = new Intent(myContext, ClinicsMain.class);
                                nextIntent.putExtra(Constants.USER_ID_INTENT, userID);
                                startActivity(nextIntent);
                                return true;
                            case R.id. bottom_nav_forum:
                                nextIntent = new Intent(myContext, ForumMain.class);
                                nextIntent.putExtra(Constants.USER_ID_INTENT, userID);
                                startActivity(nextIntent);
                                return true;
                            case R.id. bottom_nav_firstaid:
                                nextIntent = new Intent(myContext, FirstAidMainActivity.class);
                                nextIntent.putExtra(Constants.USER_ID_INTENT, userID);
                                startActivity(nextIntent);
                                return true;
                            case R.id. bottom_nav_article:
                                nextIntent = new Intent(myContext, ArticleMainActivity.class);
                                nextIntent.putExtra(Constants.USER_ID_INTENT, userID);
                                startActivity(nextIntent);
                                return true;
                            case R.id. bottom_nav_settings:
                                nextIntent = new Intent(myContext, UserMainActivity.class);
                                nextIntent.putExtra(Constants.USER_ID_INTENT, userID);
                                startActivity(nextIntent);
                                return true;
                        }
                        return false;
                    }
                });

    }

    public void onButtonClickListenerForReview(){
        clinicRating = ((RatingBar) findViewById(R.id.ClinicRatingBar));
        clinicReview = (EditText) findViewById(R.id.edit_review);
        submitReviewButton = (Button) findViewById(R.id.submitReview);

        submitReviewButton.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        int rating = (int)clinicRating.getRating();
                        String review = clinicReview.getText().toString();
                        ClinicReview clinicreview = new ClinicReview(clinicID, rating, userID, review);

                        if (review.isEmpty() && rating == 0){
                            Toast.makeText(ClinicWriteReview.this,"Cannot submit empty review",Toast.LENGTH_LONG).show();
                        }
                        else{
                        clinicDb.addReview(clinicreview);
                        clinicDb.clinicUpdateRating(clinicID, rating);
                        Toast.makeText(ClinicWriteReview.this,"Review submitted successfully",Toast.LENGTH_LONG).show();
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        ClinicWriteReview.super.onBackPressed();
                        }

                    }
                }
        );
    }
}
