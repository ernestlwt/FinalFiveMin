package finalcompilation.finalfivemin.clinic;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import finalcompilation.finalfivemin.BottomNavigationViewHelper;
import finalcompilation.finalfivemin.Constants;
import finalcompilation.finalfivemin.R;
import finalcompilation.finalfivemin.UserMainActivity;
import finalcompilation.finalfivemin.article.ArticleMainActivity;
import finalcompilation.finalfivemin.entity.Clinic;
import finalcompilation.finalfivemin.firstaid.FirstAidMainActivity;
import finalcompilation.finalfivemin.forum.ForumMain;


public class ClinicsMain extends AppCompatActivity implements OnMapReadyCallback{

    private Button searchClinic;
    private GoogleMap mMap;
    static int userID;
    Context context;
    ArrayList<Clinic> clinics;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clinic_main);
        context=this;

        clinics = new ClinicMgr(this).getClinics();


        //===========   get user id intent, name may change according to integrated naming=======
        userID = getIntent().getIntExtra(Constants.USER_ID_INTENT,0);

        onClickText();
        if (googleServicesAvailable()) {
            Toast.makeText(this, "Successfully connect to google map", Toast.LENGTH_LONG).show();
            initMap();
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
                        Bundle bundle = getIntent().getExtras();
                        int userID = bundle.getInt(Constants.USER_ID_INTENT);
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


    public void onClickText(){
        searchClinic = (Button) findViewById(R.id.search_clinics);
        searchClinic.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent intentClinicList = new Intent (context,ClinicListDisplay.class);
                        intentClinicList.putExtra(Constants.USER_ID_INTENT,userID);
                        context.startActivity(intentClinicList);
                    }
                }
        );
    }

    private void initMap(){
        SupportMapFragment mapfragment = ((SupportMapFragment) this.getSupportFragmentManager().findFragmentById(
                R.id.map));
        mapfragment.getMapAsync(this);
    }

    public boolean googleServicesAvailable(){
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if(isAvailable == ConnectionResult.SUCCESS){return true;}
        else if(api.isUserResolvableError(isAvailable)){
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        }
        else{Toast.makeText(this,"Cannot connect to play services", Toast.LENGTH_LONG).show();}
        return false;
    }


    @Override
    public void onMapReady(GoogleMap googlemap){
         mMap = googlemap;
         goToUserLocation();
         addMarkers();
    }

    private void addMarkers(){
       for (Clinic clinic: clinics){
          LatLng ll = clinic.getLocation();
          mMap.addMarker(new MarkerOptions().position(ll).title(clinic.getClinicName()));
       }
    }


    private void goToUserLocation() {
        double lat = 1.3466137;
        double lng = 103.6819953;
        int zoom = 11;
        LatLng ll = new LatLng(lat,lng);
        mMap.addMarker(new MarkerOptions().position(ll).title("School of Computer Science and Engineering"));
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll,zoom);
        mMap.moveCamera(update);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */

    /* how to load?
    public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
    */

}
