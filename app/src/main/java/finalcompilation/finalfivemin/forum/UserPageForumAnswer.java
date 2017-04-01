package finalcompilation.finalfivemin.forum;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;

import finalcompilation.finalfivemin.BottomNavigationViewHelper;
import finalcompilation.finalfivemin.Constants;
import finalcompilation.finalfivemin.R;
import finalcompilation.finalfivemin.UserMainActivity;
import finalcompilation.finalfivemin.article.ArticleMainActivity;
import finalcompilation.finalfivemin.clinic.ClinicsMain;
import finalcompilation.finalfivemin.database.UserDatabaseHelper;
import finalcompilation.finalfivemin.entity.ForumAnswer;
import finalcompilation.finalfivemin.entity.User;
import finalcompilation.finalfivemin.firstaid.FirstAidMainActivity;

public class UserPageForumAnswer extends AppCompatActivity {

    ForumMgr fm = new ForumMgr(this);
    int catID;
    int userID;
    int qnsID;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<ForumAnswer> ansList = new ArrayList<ForumAnswer>();
    ArrayList<String> ansText = new ArrayList<String>();
    ArrayList<String> nameList = new ArrayList<String>();
    ArrayList<String> credList = new ArrayList<String>();

    User user = new User();
    UserDatabaseHelper userDB = new UserDatabaseHelper(this);

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_page_forum_answer);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            qnsID = bundle.getInt("QnsID");
            System.out.println("got from showQns qnsID : " + qnsID);
            //fm.getAns(catID, qnsID);
            userID = bundle.getInt("UserID");
            System.out.println("got from showQns userID : " + userID);

            //ansList = myDb.getAns(catID, qnsID);
            ansList = fm.getAns(qnsID);

            for(int i=0; i<ansList.size(); i++){
                ForumAnswer ans = ansList.get(i);
                ansText.add(ans.getAns());
                user = userDB.getUser(userID);
                nameList.add(user.getName());
                credList.add(user.getCredential());
            }
        }

        recyclerView = (RecyclerView) findViewById(R.id.ansRecView);
        adapter = new ForumRecyclerAdapter(ansText, nameList, credList);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

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
