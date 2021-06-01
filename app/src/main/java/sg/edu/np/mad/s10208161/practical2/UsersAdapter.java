package sg.edu.np.mad.s10208161.practical2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class UsersAdapter extends RecyclerView.Adapter<UserViewHolder> {
    // for layout
    Context context;
    // for the constructor parameter
    ArrayList<User> userList;

    // Constructor for UsersAdapter
    public UsersAdapter(Context c, ArrayList<User> ul)
    {
        context = c;
        userList = ul;
    }

    // compulsory override methods for RV adapter
    @NonNull
    @Override
    // creating the view holder
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // set the layout for the viewholder
        View item = null;

        // display different layout depending on the viewType returned from getItemViewType() method.
        if (viewType == 1) {
            item = LayoutInflater.from(parent.getContext()).inflate(R.layout.vh_user, parent, false);
        }
        else {
            item = LayoutInflater.from(parent.getContext()).inflate(R.layout.vh_user_seven, parent, false);
        }

        return new UserViewHolder(item);
    }

    // setting the texts for each of the viewholder based on the position.
    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User u = userList.get(position);

        holder.name.setText(u.getUserName());
        holder.desc.setText(u.getUserDesc());
        holder.displayPicture.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Profile").setMessage("MADness").setCancelable(false).setPositiveButton("View", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent mainActivity = new Intent(context, MainActivity.class);
                        Bundle userData = new Bundle();
                        userData.putString("Name", u.getUserName());
                        userData.putString("Desc", u.getUserDesc());
                        userData.putInt("Id", u.getUserId());
                        userData.putBoolean("followStatus", u.getFollowBool());
                        userData.putInt("index", position);
                        mainActivity.putExtra("UserData", userData);
                        context.startActivity(mainActivity);
                    }
                }).setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // cancels the dialog
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    // find the total number of viewholders required by returning the size of the userList to Android OS. the rest are handled by android to populate the viewholders
    @Override
    public int getItemCount() {
        return userList.size();
    }

    @Override
    public int getItemViewType(int position) {
        // retrieve the username for operation
        String name = userList.get(position).getUserName();
        if(name.charAt(name.length()-1) == '7')
        {
            return 0;
        }
        return 1;
    }
}
