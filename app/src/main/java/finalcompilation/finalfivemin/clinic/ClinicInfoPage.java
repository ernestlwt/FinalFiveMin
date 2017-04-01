package finalcompilation.finalfivemin.clinic;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import finalcompilation.finalfivemin.BottomNavigationViewHelper;
import finalcompilation.finalfivemin.Constants;
import finalcompilation.finalfivemin.R;
import finalcompilation.finalfivemin.UserMainActivity;
import finalcompilation.finalfivemin.article.ArticleMainActivity;
import finalcompilation.finalfivemin.database.ClinicDatabaseHelper;
import finalcompilation.finalfivemin.firstaid.FirstAidMainActivity;
import finalcompilation.finalfivemin.forum.ForumMain;

public class ClinicInfoPage extends AppCompatActivity {

    static Context ctx;
    TextView clinicName;
    RatingBar rating;
    TextView contact;
    TextView address;
    TextView writereview;
    TextView navigation;
    TextView save;
    LatLng location;
    TextView review;
    ImageView icon_review;
    ImageView icon_save;
    ImageView icon_navigation;
    private BottomNavigationView bottomNavigationView;
    int userID;
    int clinicID;
    ClinicDatabaseHelper clinicDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        setContentView(R.layout.clinic_info_page);
        clinicName = (TextView) findViewById(R.id.clinicName);
        rating = (RatingBar) findViewById(R.id.ratingBar2);
        contact = (TextView) findViewById(R.id.clinicContact);
        address = (TextView) findViewById(R.id.address);
        writereview = (TextView) findViewById(R.id.write_review);
        navigation = (TextView)  findViewById(R.id.Navigation_Display);
        review = (TextView) findViewById(R.id.reviews);
        save = (TextView) findViewById(R.id.Save_Display);
        icon_review = (ImageView) findViewById(R.id.image_review);
        icon_save = (ImageView) findViewById(R.id.image_clinicsave);
        icon_navigation = (ImageView) findViewById(R.id.image_navigation);

        clinicID = getIntent().getIntExtra("clinicid",0);
        userID = getIntent().getIntExtra(Constants.USER_ID_INTENT,0);
        clinicName.setText(getIntent().getStringExtra("name"));
        contact.setText(getIntent().getStringExtra("contact"));
        address.setText(getIntent().getStringExtra("address"));
        rating.setRating((float)getIntent().getDoubleExtra("rating",0));
        Bundle bundle = getIntent().getParcelableExtra("bundle");
        location = bundle.getParcelable("latlng");

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


        onClickReview();
        onClickNavigation();
        onClickWriteReview();
        onClickSave();
    }

    public void onClickSave(){
        icon_save.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){

                        clinicDb = new ClinicDatabaseHelper(ctx);
                        clinicDb.addFavorite(clinicID,userID);
                        Toast.makeText(ctx,"Successfully saved to favorite list",Toast.LENGTH_LONG).show();
                    }
                }
        );
        save.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){

                        clinicDb = new ClinicDatabaseHelper(ctx);
                        clinicDb.addFavorite(clinicID,userID);
                        Toast.makeText(ctx,"Successfully saved to favorite list",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void onClickWriteReview(){
        writereview.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent intentClinicWriteReview = new Intent (ctx,ClinicWriteReview.class);
                        intentClinicWriteReview.putExtra(Constants.USER_ID_INTENT,userID);
                        intentClinicWriteReview.putExtra("clinicid",clinicID);
                        startActivity(intentClinicWriteReview);
                    }
                }
        );
        icon_review.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent intentClinicWriteReview = new Intent (ctx,ClinicWriteReview.class);
                        intentClinicWriteReview.putExtra(Constants.USER_ID_INTENT,userID);
                        intentClinicWriteReview.putExtra("clinicid",clinicID);
                        startActivity(intentClinicWriteReview);
                    }
                }
        );
    }


    public void onClickReview(){
        review.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent intentClinicReview = new Intent (ctx,ClinicSeeReview.class);
                        intentClinicReview.putExtra("clinicid",clinicID);
                        intentClinicReview.putExtra(Constants.USER_ID_INTENT,userID);
                        startActivity(intentClinicReview);
                    }
                }
        );
    }

    public void onClickNavigation(){
        navigation.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent intentClinicNavigation = new Intent (ctx,ClinicMapDirection.class);
                        intentClinicNavigation.putExtra("clinicid",clinicID);
                        intentClinicNavigation.putExtra("clinicname",clinicName.getText());
                        intentClinicNavigation.putExtra(Constants.USER_ID_INTENT,userID);
                        Bundle latlng = new Bundle();
                        latlng.putParcelable("latlng", location);
                        intentClinicNavigation.putExtra("bundle",latlng);
                        startActivity(intentClinicNavigation);
                    }
                }
        );
        icon_navigation.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent intentClinicNavigation = new Intent (ctx,ClinicMapDirection.class);
                        intentClinicNavigation.putExtra("clinicid",clinicID);
                        intentClinicNavigation.putExtra(Constants.USER_ID_INTENT,userID);
                        Bundle latlng = new Bundle();
                        latlng.putParcelable("latlng", location);
                        intentClinicNavigation.putExtra("bundle",latlng);
                        startActivity(intentClinicNavigation);
                    }
                }
        );
    }
}
