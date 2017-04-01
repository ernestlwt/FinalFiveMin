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
import android.widget.Toast;


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


public class ClinicMapDirection extends AppCompatActivity implements OnMapReadyCallback{

    GoogleMap mMap;
    String clinicName;
    LatLng address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clinic_map_direction);
        clinicName =  getIntent().getStringExtra("clinicname");
        Bundle bundle = getIntent().getParcelableExtra("bundle");
        address = bundle.getParcelable("latlng");

        if (googleServicesAvailable()) {
            Toast.makeText(this, "Successfully connect to google map", Toast.LENGTH_LONG).show();
            initMap();
        }

    }

    private void initMap(){
        SupportMapFragment mapfragment = ((SupportMapFragment) this.getSupportFragmentManager().findFragmentById(
                R.id.map_direction));
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
        goToClinicLocation(address);
    }


    private void goToClinicLocation(LatLng ll) {
        int zoom = 12;
        mMap.addMarker(new MarkerOptions().position(ll));
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll,zoom);
        mMap.moveCamera(update);
    }

}
