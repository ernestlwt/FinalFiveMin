package finalcompilation.finalfivemin.forum;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import finalcompilation.finalfivemin.BottomNavigationViewHelper;
import finalcompilation.finalfivemin.Constants;
import finalcompilation.finalfivemin.R;
import finalcompilation.finalfivemin.UserMainActivity;
import finalcompilation.finalfivemin.article.ArticleMainActivity;
import finalcompilation.finalfivemin.clinic.ClinicsMain;
import finalcompilation.finalfivemin.entity.ForumQuestion;
import finalcompilation.finalfivemin.firstaid.FirstAidMainActivity;

public class UserPageForumQuestion extends AppCompatActivity {

    //private int catID;
    private int userID;
    ArrayList<ForumQuestion> qnsList = new ArrayList<ForumQuestion>();
    ForumMgr fm = new ForumMgr(this);
    private int qnsID;
    ForumQuestion qns;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_page_question);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //catID = Integer.parseInt(bundle.get("CatID").toString());
            userID = bundle.getInt("UserID");
            System.out.println("from main activity UserID : " + userID);
            //System.out.println("from main activity CatID : "+ catID);

            qnsList = fm.getUserQns(userID);
            ArrayList<String> qnsText = new ArrayList<String>();
            for (int i=0; i<qnsList.size();i++){
                qns = qnsList.get(i);
                qnsText.add(qns.getQns());
            }

            ArrayAdapter<String> qnsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, qnsText);
            ListView qnsListView = (ListView) findViewById(R.id.qnsListView); //reference to listview
            qnsListView.setAdapter(qnsAdapter);    //look at catAdapter to know what to convert to list items

            qnsListView.setOnItemClickListener(
                    new AdapterView.OnItemClickListener(){ //listener for the list so the list wait for someone to click smth
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//to know what is clicked
                            System.out.println("qns number clicked : "+position);
                            Intent i = new Intent(UserPageForumQuestion.this, UserPageForumAnswer.class);
                            qns = qnsList.get(position);
                            qnsID = qns.getQnsID();
                            i.putExtra("QnsID", qnsID);
                            i.putExtra("UserID", userID);
                            startActivity(i);
                        }
                    }
            );
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
}
