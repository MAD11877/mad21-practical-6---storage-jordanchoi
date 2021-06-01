package sg.edu.np.mad.s10208161.practical2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static sg.edu.np.mad.s10208161.practical2.ListActivity.userList;

public class MainActivity extends AppCompatActivity {

//  my codes - not even sure if this is how you comment in Java
//  create new user object and init params as specified.

//  following line no longer required for practical 4
//  User u1 = new User("MAD", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", 0, false);

//  Create User u variable and instantiate.
    User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//      boilerplate codes in the following 2 lines
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      receive intent from the created intent from ListActivity.java
        Intent mainActivity = getIntent();

//      assign the received information passed from the intent and assign it to randNum
        Bundle ud = mainActivity.getBundleExtra("UserData");

//      reassign variable u to retrieve the targeted User object based on the passed index and retrieve from the list
        u = userList.get(ud.getInt("index"));

/*      No longer required for practical 4
        u1.name = ud.getString("Name");
        u1.description = ud.getString("Desc");
        u1.id = ud.getInt("Id",-1);
        u1.followed = ud.getBoolean("followStatus", false);
*/

//      changing the title text and description text to specifications
        TextView titleText = findViewById(R.id.titleText);
        titleText.setText(ud.getString("Name"));
        TextView descText = findViewById(R.id.descText);
        descText.setText(ud.getString("Desc"));

//      changing the button texts
        Button messageBtn = findViewById(R.id.messageBtn);
        messageBtn.setText("Message");
        Button followBtn = findViewById(R.id.followBtn);

//      calls the method to change the button texts according to the bool value of followed in the user class.
        followBtnText(u, followBtn);
        DBHandler db = new DBHandler(this);
//      add onClickListener to the followBtn to handle click events from the button
        followBtn.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v)
                    {
//                      call the toggleFollow method in the user object and changes the value of followed accordingly.
                        u.toggleFollow();
//                      updates the followBtn text by calling the method as above.
                        followBtnText(u, followBtn);
//                      creates toast message accordingly to the value of followed in the user object.
                        if (u.getFollowBool()) {
                            Toast.makeText(getApplicationContext(), "Followed", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplication(),"Unfollowed",Toast.LENGTH_SHORT).show();
                        }
                        db.getWritableDatabase();
                        db.updateUser(u);
                    }
        });
    }

//  method to change button text, based on the user's following bool property value
    public void followBtnText(User u, Button followBtn) {
//      check if the user is followed and change relevant button texts. set as method would be better?
        if (u.getFollowBool()) {
            followBtn.setText("Unfollow");
        }
        else {
            followBtn.setText("Follow");
        }
    }

    /* alternative method to do event listener

    public void btn3OnClick(View v) {
        Button btn3 = (Button) v;
        btn3.setText("Hi");
    }
     */

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("follow", u.getFollowBool());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        u.setBool(savedInstanceState.getBoolean("follow"));
        Button followBtn = findViewById(R.id.followBtn);
        followBtnText(u,followBtn);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("debug","start");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("debug","stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("debug","destroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("debug","pause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("debug","resume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("debug","restart");
    }
}