package finalcompilation.finalfivemin.forum;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import finalcompilation.finalfivemin.BottomNavigationViewHelper;
import finalcompilation.finalfivemin.Constants;
import finalcompilation.finalfivemin.R;
import finalcompilation.finalfivemin.UserMainActivity;
import finalcompilation.finalfivemin.article.ArticleMainActivity;
import finalcompilation.finalfivemin.clinic.ClinicsMain;
import finalcompilation.finalfivemin.database.ForumDatabaseHelper;
import finalcompilation.finalfivemin.entity.User;
import finalcompilation.finalfivemin.firstaid.FirstAidMainActivity;

public class ForumSubmitAnswer extends AppCompatActivity {
    int catID ;
    int qnsID ;
    int userID;
    ForumDatabaseHelper myDb;
    EditText ansText;
    Button submitAns;
    TextView qnsToBeAns;
    String qns;
    ForumMgr fm = new ForumMgr(this);
    private BottomNavigationView bottomNavigationView;


    NotificationCompat.Builder notification;
    private static final int uniqueID = 12345;

    //User user;
    //Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_submit_ans);

        notification  = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);


        myDb = new ForumDatabaseHelper(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            catID = bundle.getInt("CatID");
            qnsID = bundle.getInt("QnsID");
            userID = bundle.getInt(Constants.USER_ID_INTENT);
            System.out.println("got from showAns userID : " + userID);
        }

        qnsToBeAns = (TextView) findViewById(R.id.qnsToBeAns);
        qns = fm.getQnsText(qnsID);
        qnsToBeAns.setText(qns);
        ansText = (EditText) findViewById(R.id.ansText);
        submitAns = (Button) findViewById(R.id.submitAns);
        AddAns();

        //bottom nav bar
        final Context myContext = this;
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_forum);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        //Bundle bundle = getIntent().getExtras();
                        //userID = bundle.getInt(Constants.USER_ID_INTENT);
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
    public void AddAns(){
        submitAns.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = fm.addAns(ansText.getText().toString(),qnsID, userID);
                        if (isInserted == true) {
                            Toast.makeText(ForumSubmitAnswer.this, "ForumAnswer submitted", Toast.LENGTH_LONG).show();

                            //checking if user is interested party
                            ArrayList<User> interestedUser = new ArrayList<User>();
                            interestedUser = fm.getInterestedUser(qnsID);
                            for (int i=0; i<interestedUser.size(); i++){
                                User user = interestedUser.get(i);
                                System.out.println("Interested user: " + user.getId()+ "and answerer: "+ userID);
                                if (user.getId() == userID){
                                    System.out.println("NOTIFICATION PROCESSING");
                                    System.out.println(user.getId() + " and " + userID);
                                    //Build the notification
                                    notification.setSmallIcon(R.drawable.logo);
                                    notification.setTicker("This is the ticker");
                                    notification.setWhen(System.currentTimeMillis());
                                    notification.setContentTitle("A question you liked was answered!");
                                    notification.setContentText(qns);

                                    Intent intent = new Intent(ForumSubmitAnswer.this, UserPageForumQuestion.class);
                                    intent.putExtra(Constants.USER_ID_INTENT, userID);
                                    PendingIntent pendingIntent = PendingIntent.getActivity(ForumSubmitAnswer.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                    notification.setContentIntent(pendingIntent);

                                    //Builds notification and issues it
                                    NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                    nm.notify(uniqueID, notification.build());
                                }
                            }

                        }
                        else
                            Toast.makeText(ForumSubmitAnswer.this, "ForumAnswer not submitted", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ForumSubmitAnswer.this, ForumMain.class);
                        intent.putExtra(Constants.USER_ID_INTENT, userID);
                        startActivity(intent);
                    }
                }
        );
    }
}
