package finalcompilation.finalfivemin;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import finalcompilation.finalfivemin.article.ArticleMainActivity;
import finalcompilation.finalfivemin.clinic.ClinicFavList;
import finalcompilation.finalfivemin.clinic.ClinicsMain;
import finalcompilation.finalfivemin.database.UserDatabaseHelper;
import finalcompilation.finalfivemin.entity.User;
import finalcompilation.finalfivemin.firstaid.FirstAidMainActivity;
import finalcompilation.finalfivemin.forum.ForumMain;
import finalcompilation.finalfivemin.forum.UserPageForumAnswer;
import finalcompilation.finalfivemin.forum.UserPageForumQuestion;

public class UserMainActivity extends AppCompatActivity {
    int userID;
    User user;
    UserDatabaseHelper userDB;

    Context ctx;

    TextView usernameText;
    TextView clinicText;
    TextView questionText;
    RadioButton shapeRB;
    RadioButton asiaoneRB;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main);


        userDB = new UserDatabaseHelper(this);
        userID = getIntent().getIntExtra(Constants.USER_ID_INTENT,0);
        user = userDB.getUser(userID);

        ctx = this;

        usernameText = (TextView) findViewById(R.id.user_name);
        clinicText = (TextView) findViewById(R.id.user_clinic);
        questionText = (TextView) findViewById(R.id.user_question);
        shapeRB = (RadioButton) findViewById(R.id.radio_shape);
        asiaoneRB = (RadioButton) findViewById(R.id.radio_asiaone);

        usernameText.setText(user.getName().toUpperCase());

        switch(user.getPreference()){
            case Constants.ARTICLE_SOURCE_SHAPE:
                shapeRB.setChecked(true);
                break;
            case Constants.ARTICLE_SOURCE_ASIAONE:
                asiaoneRB.setChecked(true);
                break;
        }
        onClickClinic();
        onClickQuestion();

        //bottom nav bar
        final Context myContext = this;
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_settings);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
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

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_shape:
                if (checked)
                    user.setPreference(Constants.ARTICLE_SOURCE_SHAPE);
                    userDB.updateUser(user);
                    break;
            case R.id.radio_asiaone:
                if (checked)
                    user.setPreference(Constants.ARTICLE_SOURCE_ASIAONE);
                    userDB.updateUser(user);
                    break;
        }
    }

    public void onClickClinic(){
        clinicText.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent intent = new Intent(ctx, ClinicFavList.class);
                        intent.putExtra(Constants.USER_ID_INTENT,userID);
                        startActivity(intent);
                    }
                }
        );
    }

    public void onClickQuestion(){
        questionText.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent intent = new Intent(ctx, UserPageForumQuestion.class);
                        intent.putExtra(Constants.USER_ID_INTENT,userID);
                        startActivity(intent);
                    }
                }
        );
    }
}
