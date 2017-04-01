package finalcompilation.finalfivemin.forum;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle; //ListView
import android.support.v7.app.NotificationCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import finalcompilation.finalfivemin.BottomNavigationViewHelper;
import finalcompilation.finalfivemin.Constants;
import finalcompilation.finalfivemin.R;
import finalcompilation.finalfivemin.UserMainActivity;
import finalcompilation.finalfivemin.article.ArticleMainActivity;
import finalcompilation.finalfivemin.clinic.ClinicsMain;
import finalcompilation.finalfivemin.database.UserDatabaseHelper;
import finalcompilation.finalfivemin.entity.User;
import finalcompilation.finalfivemin.firstaid.FirstAidMainActivity;


public class ForumMain extends AppCompatActivity{

    //ForumDatabaseHelper myDb;
    private int userID;
    NotificationCompat.Builder notification;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_main);


        String[] category = {"Arthritis", "Colds", "Cough", "Diabetes", "Fever", "Hand, Food and Mouth Disease", "HIV/AIDS"};
        ListAdapter catAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, category);
        ListView categoryListView = (ListView) findViewById(R.id.categoryListView); //reference to listview
        categoryListView.setAdapter(catAdapter);    //look at catAdapter to know what to convert to list items

        Bundle bundle = getIntent().getExtras();
        userID = bundle.getInt(Constants.USER_ID_INTENT);
        Context myctx = this;
        UserDatabaseHelper udb = new UserDatabaseHelper(myctx);
        User u = (udb).getUser(userID);
        u.setProfessional(1);
        udb.updateUser(u);
        categoryListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){ //listener for the list so the list wait for someone to click smth
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//to know what is clicked

                        // take out bundle and put back in
                        /*
                        Bundle bundle = getIntent().getExtras();
                        if (bundle != null) {
                            userID = bundle.getInt(Constants.USER_ID_INTENT);


                        }
                        */

                        Intent i = new Intent(ForumMain.this, ForumDisplayQuestion.class);
                        i.putExtra("Category", String.valueOf(parent.getItemAtPosition(position)));
                        i.putExtra("CatID", position);
                        i.putExtra(Constants.USER_ID_INTENT, userID);
                        startActivity(i);
                    }
               }
        );

        //bottom nav bar
        final Context myContext = this;
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_forum);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Bundle bundle = getIntent().getExtras();
                        userID = bundle.getInt(Constants.USER_ID_INTENT);
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
