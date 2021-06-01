package sg.edu.np.mad.s10208161.practical2;

import java.util.ArrayList;

public class User {
    private String name;
    private String description;
    private int id;
    private boolean followed;

    public User() {
    }

    // method created in the user class - to change the followed value in this object when called.
    public void toggleFollow() {
        followed = !followed;
    }

    // methods for get & set, not sure if i am doing it right.
    public void setUserName(String n)
    {
        name = n;
    }

    public String getUserName()
    {
        return name;
    }

    public void setUserDesc(String desc)
    {
        description = desc;
    }

    public String getUserDesc()
    {
        return description;
    }

    public void setUserId(int idNum)
    {
        id = idNum;
    }

    public int getUserId()
    {
        return id;
    }

    public void setBool(boolean follow)
    {
        followed = follow;
    }

    public boolean getFollowBool()
    {
        return followed;
    }
}
