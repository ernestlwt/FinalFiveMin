package finalcompilation.finalfivemin.forum;
import android.content.Context;

import java.util.ArrayList;

import finalcompilation.finalfivemin.database.ForumDatabaseHelper;
import finalcompilation.finalfivemin.entity.ForumAnswer;
import finalcompilation.finalfivemin.entity.ForumQuestion;
import finalcompilation.finalfivemin.entity.User;

/**
 * Created by Denise on 23-Mar-17.
 */

public class ForumMgr {

    ForumDatabaseHelper myDb;
    ArrayList<ForumQuestion> qnsList = new ArrayList<ForumQuestion>();
    ArrayList<ForumAnswer> ansList = new ArrayList<ForumAnswer>();
    ArrayList<User> userList = new ArrayList<User>();

    public ForumMgr(Context context){
        myDb = new ForumDatabaseHelper(context);
    }

    public ArrayList<ForumQuestion> getQns(int catID){
        qnsList = myDb.getQns(catID);
        return qnsList;
    }

    public ArrayList<ForumAnswer> getAns(int qnsID){
        ansList = myDb.getAns(qnsID);
        return ansList;
    }

    public boolean addQns(String qns, int catID, int userID){
        ForumQuestion question = new ForumQuestion(userID, qns, catID);
        if(question == null){
            System.out.println("not created");
        }
        return myDb.addQns(question);
    }

    public boolean addAns(String ans,int qnsID, int userID){
        ForumAnswer answer = new ForumAnswer(userID, ans, qnsID);
        return myDb.addAns(answer);
    }

    public ArrayList<User> getInterestedUser(int qnsID){
        return myDb.getInterestedUser(qnsID);
    }

    public String getQnsText(int qnsID){
        return myDb.getQnsText(qnsID);
    }

    public ArrayList<ForumQuestion> getUserQns(int userID){
        return myDb.getUserQns(userID);
    }


}

