package finalcompilation.finalfivemin.forum;

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
import android.widget.Toast;

import finalcompilation.finalfivemin.BottomNavigationViewHelper;
import finalcompilation.finalfivemin.Constants;
import finalcompilation.finalfivemin.R;
import finalcompilation.finalfivemin.UserMainActivity;
import finalcompilation.finalfivemin.article.ArticleMainActivity;
import finalcompilation.finalfivemin.clinic.ClinicsMain;
import finalcompilation.finalfivemin.firstaid.FirstAidMainActivity;

public class ForumSubmitQuestion extends AppCompatActivity{

    EditText qnsText;
    Button submitQns;
    private int catID;
    private int userID;
    private int qnsID;
    ForumMgr fm = new ForumMgr(this);
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_submit_question);
        qnsText = (EditText) findViewById(R.id.qnsText);
        submitQns = (Button) findViewById(R.id.submitQns);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            catID = bundle.getInt("CatID");
            userID = bundle.getInt(Constants.USER_ID_INTENT);
        }

        AddQns();

        //bottom nav bar
        final Context myContext = this;
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_forum);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        /*Bundle bundle = getIntent().getExtras();
                        userID = bundle.getInt(Constants.USER_ID_INTENT); */
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
    public void AddQns(){
        submitQns.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        /* Bundle bundle = getIntent().getExtras();
                        if (bundle != null) {
                            catID = bundle.getInt("CatID");
                            userID = bundle.getInt(Constants.USER_ID_INTENT);
                        } */
                        System.out.println("In submit question page: "+catID + " and " + userID);
                        boolean isInserted = fm.addQns(qnsText.getText().toString(), catID, userID);
                        if (isInserted == true)
                            Toast.makeText(ForumSubmitQuestion.this, "ForumQuestion submitted", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(ForumSubmitQuestion.this, "ForumQuestion not submitted", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ForumSubmitQuestion.this, ForumMain.class);
                        intent.putExtra(Constants.USER_ID_INTENT, userID);
                        startActivity(intent);
                    }
                }
        );
    }
}
