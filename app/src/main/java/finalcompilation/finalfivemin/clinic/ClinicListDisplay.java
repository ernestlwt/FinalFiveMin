package finalcompilation.finalfivemin.clinic;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;


import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;

import finalcompilation.finalfivemin.BottomNavigationViewHelper;
import finalcompilation.finalfivemin.Constants;
import finalcompilation.finalfivemin.R;
import finalcompilation.finalfivemin.UserMainActivity;
import finalcompilation.finalfivemin.article.ArticleMainActivity;
import finalcompilation.finalfivemin.entity.Clinic;
import finalcompilation.finalfivemin.firstaid.FirstAidMainActivity;
import finalcompilation.finalfivemin.forum.ForumMain;

public class ClinicListDisplay extends AppCompatActivity implements SearchView.OnQueryTextListener,AdapterView.OnItemSelectedListener{

    Context cxt = this;
    Button displayAll;
    RecyclerView recyclerView;
    ClinicListAdaptor adaptor;
    SearchView clinicSearch;
    Spinner sortspinner;
    Spinner filterspinner;
    int userID;
    private BottomNavigationView bottomNavigationView;

    ArrayList<Clinic> clinicOrigin;
    ArrayList<Clinic> clinics = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clinic_list_display);

        userID = getIntent().getIntExtra(Constants.USER_ID_INTENT,0);

        clinicOrigin = new ClinicMgr(this).getClinics();
        clinics.addAll(clinicOrigin);

        // set up recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(cxt);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        clinicSearch = (SearchView) findViewById(R.id.clinic_search);
        setupSearchView();
        adaptor = new ClinicListAdaptor(clinics, clinicOrigin, cxt,userID);
        recyclerView.setAdapter(adaptor);

        onDisplayAllButtonClick();

        filterspinner = (Spinner) findViewById(R.id.clinic_filter);
        filterspinner.setAdapter(adaptor.clinic_filter_adapter);
        filterspinner.setOnItemSelectedListener(this);

        // set up sorting spinner
        sortspinner = (Spinner) findViewById(R.id.clinic_sorter);
        //clinic_sort_adapter = ArrayAdapter.createFromResource(this,
        //        R.array.clinic_sorter_array, android.R.layout.simple_spinner_item);
        //clinic_sort_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortspinner.setAdapter(adaptor.clinic_sort_adapter);
        sortspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                switch (pos){
                    case 0:
                        // put default sorting as by name
                        Collections.sort(clinics, new ClinicNameComparator());
                        break;

                    case 1:
                        double lat = 1.3466137;
                        double lng = 103.6819953;
                        LatLng ll = new LatLng(lat,lng);
                        Collections.sort(clinics, new ClinicDistanceComparator(ll));
                        break;

                    case 2:
                        Collections.sort(clinics, new ClinicRatingComparator());
                        break;

                    case 3:
                        Collections.sort(clinics, new ClinicNameComparator());
                        break;
                }
                //refresh adaptor
                adaptor.notifyDataSetChanged();
                return;
            }

            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

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

    private void onDisplayAllButtonClick() {
        displayAll = (Button) findViewById(R.id.clinic_display_all);
        displayAll.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        filterspinner.setSelection(0);
                        sortspinner.setSelection(0);
                        clinicSearch.setQuery("", false);
                        clinicSearch.clearFocus();
                        clinics.clear();
                        clinics.addAll(clinicOrigin);
                        adaptor.notifyDataSetChanged();
                    }
                }
        );
    }


    private void setupSearchView() {
        clinicSearch.setIconifiedByDefault(false);
        clinicSearch.setOnQueryTextListener(this);
        clinicSearch.setSubmitButtonEnabled(true);
        clinicSearch.setQueryHint("Search Here");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id){
        adaptor.filterSpinner(pos);
        adaptor.notifyDataSetChanged();
        return;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        return;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adaptor.filterText(newText);
        //adaptor.filterText(newText);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }



}
