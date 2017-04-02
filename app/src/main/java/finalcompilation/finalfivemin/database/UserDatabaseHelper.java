package finalcompilation.finalfivemin.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import finalcompilation.finalfivemin.entity.User;

public class UserDatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "UserManager.db";

    // User table name
    private static final String TABLE_USER = "user";

    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_USER_CREDENTIAL = "user_credential";
    private static final String COLUMN_USER_PROFESSIONAL = "user_professional";
    private static final String COLUMN_USER_PREFERENCE = "user_preference";

    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT," + COLUMN_USER_CREDENTIAL
            + " TEXT," + COLUMN_USER_PROFESSIONAL + " INTEGER," + COLUMN_USER_PREFERENCE + " INTEGER" + ")";

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    /**
     * Constructor
     *
     * @param context
     */
    public UserDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);

        // Create tables again
        onCreate(db);

    }

    /**
     * This method is to create user record
     *
     * @param user
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_CREDENTIAL, "Doctor");
        values.put(COLUMN_USER_PROFESSIONAL, 0);
        values.put(COLUMN_USER_PREFERENCE, 0);

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    /**
     * This method to update user record
     *
     * @param user
     */
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_CREDENTIAL,user.getCredential());
        values.put(COLUMN_USER_PROFESSIONAL,user.getProfessional());
        values.put(COLUMN_USER_PREFERENCE,user.getPreference());

        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    public int keepUserID(String email) {
        int userID = 0;
        // array of columns to fetch
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " where user_email = '" + email+"'", null);
        while (cursor.moveToNext()) {
            userID = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID));

        }
        cursor.close();
        db.close();


        return userID;
    }

    public User getUser(int id){
        User user = new User();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor d = db.rawQuery("SELECT * FROM " + TABLE_USER + " where user_id = '" + id+"'", null);
        if (d != null)
            if(d.moveToFirst()){
                user.setId(id);
                user.setEmail(d.getString(d.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setName(d.getString(d.getColumnIndex(COLUMN_USER_NAME)));
                user.setPassword(d.getString(d.getColumnIndex(COLUMN_USER_PASSWORD)));
                user.setCredential(d.getString(d.getColumnIndex(COLUMN_USER_CREDENTIAL)));
                user.setProfessional(Integer.parseInt(d.getString(d.getColumnIndex(COLUMN_USER_PROFESSIONAL))));
                user.setPreference(Integer.parseInt(d.getString(d.getColumnIndex(COLUMN_USER_PREFERENCE))));

            }
        d.close();;
        db.close();
        return user;
    }

}

