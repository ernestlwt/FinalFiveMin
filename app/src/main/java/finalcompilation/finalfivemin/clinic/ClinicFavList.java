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
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;

import finalcompilation.finalfivemin.BottomNavigationViewHelper;
import finalcompilation.finalfivemin.Constants;
import finalcompilation.finalfivemin.R;
import finalcompilation.finalfivemin.UserMainActivity;
import finalcompilation.finalfivemin.article.ArticleMainActivity;
import finalcompilation.finalfivemin.database.ClinicDatabaseHelper;
import finalcompilation.finalfivemin.entity.Clinic;
import finalcompilation.finalfivemin.firstaid.FirstAidMainActivity;
import finalcompilation.finalfivemin.forum.ForumMain;

/**
 * Created by Aiqing on 3/26/17.
 */

public class ClinicFavList extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Context cxt = this;
    RecyclerView recyclerView;
    ClinicFavAdaptor adaptor;
    int userID;
    ArrayList<Clinic> favClinics = new ArrayList<>();
    ClinicDatabaseHelper clinicDb;
    TextView noSavedClinic;

    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clinics_saved);
        noSavedClinic = (TextView) findViewById(R.id.savedclinicmsg);

        userID = getIntent().getIntExtra(Constants.USER_ID_INTENT,0);
        clinicDb = new ClinicDatabaseHelper(this);
        ArrayList<Integer> ids = clinicDb.getSavedClinicIDs(userID);
        favClinics = clinicDb.getSavedClinics(ids);

        if (favClinics.size() == 0){
            noSavedClinic.setText("You do not have any saved clinics yet");
        }

        else{// set up recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.favclinicrecyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(cxt);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adaptor = new ClinicFavAdaptor(favClinics, cxt, userID);
        recyclerView.setAdapter(adaptor);

        }

        //bottom nav bar
        final Context myContext = this;
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_settings);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        return;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        return;
    }
}

