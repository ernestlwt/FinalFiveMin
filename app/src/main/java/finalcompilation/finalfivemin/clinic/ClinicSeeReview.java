package finalcompilation.finalfivemin.clinic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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

/**
 * Created by Aiqing on 3/13/17.
 */

public class ClinicSeeReview extends AppCompatActivity {

    Context cxt = this;
    RecyclerView clinicreview;
    ClinicReviewAdaptor reviewAdaptor;
    ClinicDatabaseHelper clinicDb;
    ArrayList<ClinicReview> reviews;
    TextView nullmessage;
    private BottomNavigationView bottomNavigationView;

    int clinicID;
    int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clinic_see_review);
        clinicID = getIntent().getIntExtra("clinicid",0);
        userID = getIntent().getIntExtra(Constants.USER_ID_INTENT,0);

        clinicDb = new ClinicDatabaseHelper(this);

        reviews = clinicDb.getSelectedReview(clinicID);

        if (reviews.size() == 0){
            nullmessage = (TextView) findViewById(R.id.reviewmsg);
        }

        else{ // set up recyclerview
            clinicreview = (RecyclerView) findViewById(R.id.clinicreview);
            clinicreview.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(cxt);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            clinicreview.setLayoutManager(layoutManager);
            reviewAdaptor = new ClinicReviewAdaptor(reviews,cxt);
            clinicreview.setAdapter(reviewAdaptor);
        }

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


}
