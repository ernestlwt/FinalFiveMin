package finalcompilation.finalfivemin.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import finalcompilation.finalfivemin.entity.ForumAnswer;
import finalcompilation.finalfivemin.entity.ForumQuestion;
import finalcompilation.finalfivemin.entity.User;

/**
 * Created by Denise on 02-Mar-17.
 */

public class ForumDatabaseHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "Forum.db";
    private static final String TAG = "ForumDatabaseHelper";

    private static final String TABLE_NAME_1 = "question_table";
    private static final String COL1_1 = "QNSID";
    private static final String COL1_2 = "QNSTEXT";
    private static final String COL1_3 = "CATID";
    private static final String COL1_4 = "USERID";

    private static final String TABLE_NAME_2 = "answer_table";
    private static final String COL2_1 = "ANSID";
    private static final String COL2_2 = "ANSTEXT";
    private static final String COL2_3 = "QNSID";
    private static final String COL2_4 = "PROFID";

    private static final String TABLE_NAME_3 = "category_table";
    private static final String COL3_1 = "CATID";
    private static final String COL3_2 = "CATNAME";

    UserDatabaseHelper udb;


    public ForumDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        udb = new UserDatabaseHelper(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME_1 + " (QNSID INTEGER PRIMARY KEY AUTOINCREMENT, QNSTEXT TEXT, CATID INTEGER, USERID INTEGER)");
        db.execSQL("CREATE TABLE " + TABLE_NAME_2 + " (ANSID INTEGER PRIMARY KEY AUTOINCREMENT, ANSTEXT TEXT, QNSID INTEGER, PROFID INTEGER)");
        //db.execSQL("CREATE TABLE " + TABLE_NAME_3 + " (CATID INTEGER PRIMARY KEY AUTOINCREMENT, CATNAME TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_1);
        onCreate(db);
    }

    public ArrayList<ForumQuestion> getQns(int catID){
        ArrayList<ForumQuestion> qnsList = new ArrayList<ForumQuestion>();
        System.out.println("Choice = " + catID);
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println(catID);
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME_1 + " where CATID = "+ catID, null);
        if (c != null)
            if(c.moveToFirst()){
                do{
                    int qnsID = Integer.parseInt(c.getString(c.getColumnIndex("QNSID")));
                    int askerID = Integer.parseInt(c.getString(c.getColumnIndex("USERID")));
                    String qns = c.getString(c.getColumnIndex("QNSTEXT"));
                    ForumQuestion question = new ForumQuestion(qnsID, askerID, qns, catID);
                    qnsList.add(question);
                } while(c.moveToNext());
            }
        return qnsList;
    }

    public ArrayList<ForumAnswer> getAns(int qnsID){
        ArrayList<ForumAnswer> ansList = new ArrayList<ForumAnswer>();
        System.out.println("QnsID = " +qnsID);
        SQLiteDatabase db  = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME_2 + " where QNSID = "+qnsID, null);
        if (c != null)
            if(c.moveToFirst()){
                do{
                    int ansID = Integer.parseInt(c.getString(c.getColumnIndex("ANSID")));
                    int answererID = Integer.parseInt(c.getString(c.getColumnIndex("PROFID")));
                    String ans = c.getString(c.getColumnIndex("ANSTEXT"));
                    ForumAnswer answer = new ForumAnswer(ansID, answererID, ans);
                    ansList.add(answer);
                } while(c.moveToNext());
            }
        return ansList;
    }

    public boolean addQns(ForumQuestion question){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1_2, question.getQns());
        contentValues.put(COL1_3, question.getCatID());
        contentValues.put(COL1_4, question.getAskerID());

        long result = db.insert(TABLE_NAME_1, null, contentValues);

        if (result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean addAns(ForumAnswer answer){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2_2, answer.getAns());
        contentValues.put(COL2_3, answer.getQnsID());
        contentValues.put(COL2_4, answer.getAnswererID());

        long result = db.insert(TABLE_NAME_2, null, contentValues);

        if (result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public String getQnsText(int qns){
        SQLiteDatabase db = this.getWritableDatabase();
        String qnsText = "error";
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME_1 + " where QNSID = "+qns, null);
        if (c != null)
            if(c.moveToFirst()){
                qnsText = c.getString(c.getColumnIndex("QNSTEXT"));
            }
        c.close();
        System.out.println(qnsText);
        return qnsText;
    }

    public ArrayList<User> getInterestedUser(int qnsID){
        ArrayList<User> userList = new ArrayList<User>();
        System.out.println("QnsID = " +qnsID);
        SQLiteDatabase db  = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT USERID FROM " + TABLE_NAME_1 + " where QNSID = "+qnsID, null);
        if (c != null)
            if(c.moveToFirst()){
                do{
                    int userID = Integer.parseInt(c.getString(c.getColumnIndex("USERID")));
                    User user = udb.getUser(userID);
                    userList.add(user);
                } while(c.moveToNext());
            }
        return userList;
    }

    public ArrayList<ForumQuestion> getUserQns(int userID){
        ArrayList<ForumQuestion> qnsList = new ArrayList<ForumQuestion>();
        System.out.println("userID = " + userID);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME_1 + " where USERID = "+ userID, null);
        if (c != null)
            if(c.moveToFirst()){
                do{
                    int qnsID = Integer.parseInt(c.getString(c.getColumnIndex("QNSID")));
                    int askerID = Integer.parseInt(c.getString(c.getColumnIndex("USERID")));
                    String qns = c.getString(c.getColumnIndex("QNSTEXT"));
                    int catID = Integer.parseInt(c.getString(c.getColumnIndex("CATID")));
                    ForumQuestion question = new ForumQuestion(qnsID, askerID, qns, catID);
                    qnsList.add(question);
                } while(c.moveToNext());
            }
        return qnsList;
    }
}
