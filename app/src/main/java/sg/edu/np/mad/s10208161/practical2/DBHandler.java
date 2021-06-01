package sg.edu.np.mad.s10208161.practical2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

public class DBHandler extends SQLiteOpenHelper {
    public DBHandler(@Nullable Context context) {
        super(context, "Users.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE User (Name TEXT, Description TEXT, Id INTEGER PRIMARY KEY AUTOINCREMENT, Followed INTEGER)");

        for (int i = 0; i < 20; i++)
        {
            Random rd = new Random();
            int randomIntForName = rd.nextInt(10000000);
            int randomIntForDesc = rd.nextInt(10000000);
            boolean randBool = rd.nextBoolean();
            User u = new User();
            u.setUserName("Name" + randomIntForName);
            u.setUserDesc("Description" + randomIntForDesc);
            u.setBool(randBool);
            u.setUserId(i);

            int followInt;
            if (u.getFollowBool())
            {
                followInt = 1;
            }
            else
            {
                followInt = 0;
            }
            // attempted to call addUser(u) method to add user into database inside the for loop but it does not work. Thus i have no choice but to use the insert statement
            db.execSQL("INSERT INTO User (Name, Description, Followed) VALUES('" + u.getUserName() + "','" + u.getUserDesc() + "','" + followInt + "')");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS User");
            onCreate(db);
        }
    }

    // method to add user into db
    public void addUser(User u)
    {
        ContentValues values = new ContentValues();
        values.put("Name", u.getUserName());
        values.put("Description", u.getUserDesc());

        if (u.getFollowBool())
        {
            values.put("Followed", 1);
        }
        else
        {
            values.put("Followed", 0);
        }

        SQLiteDatabase db = getWritableDatabase();
        db.insert("User", null, values);
    }

    public ArrayList<User> getUsers()
    {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM User", null);

        User u = null;
        ArrayList<User> userList = new ArrayList<>();

        while(cursor.moveToNext())
        {
            u = new User();
            u.setUserName(cursor.getString(0));
            u.setUserDesc(cursor.getString(1));
            u.setUserId(cursor.getInt(2));
            if (cursor.getInt(3) == 1)
            {
                u.setBool(true);
            }
            else
            {
                u.setBool(false);
            }

            userList.add(u);
        }
        cursor.close();
        db.close();

        return userList;
    }

    /**
     * SELECT * FROM Users
     * @param name
     * @return
     */
    public User getSpecificUsers(String name)
    {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE name = \"" + name + "\"", null);
        User u = null;

        if (cursor.moveToFirst())
        {
            u = new User();
            u.setUserName(cursor.getString(0));
            u.setUserDesc(cursor.getString(1));
            u.setUserId(cursor.getInt(2));
            if (cursor.getInt(3) == 1)
            {
                u.setBool(true);
            }
            else
            {
                u.setBool(false);
            }
        }
        cursor.close();
        db.close();
        return u;
    }

    /**
     * Update user
     * @params user
     * */

    public void updateUser(User user)
    {
        ContentValues values = new ContentValues();
        values.put("Name", user.getUserName());
        values.put("Description", user.getUserDesc());

        if (user.getFollowBool())
        {
            values.put("Followed", 1);
        }
        else
        {
            values.put("Followed", 0);
        }

        SQLiteDatabase db = getWritableDatabase();
        String queryId = String.valueOf(user.getUserId());

        db.update("User", values, "Id = ?", new String[]{queryId});
    }

}
