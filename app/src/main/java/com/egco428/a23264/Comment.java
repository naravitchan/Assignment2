package com.egco428.a23264;


public class Comment {
    private long id;
    private String username;
    private String password;
    private String latitude;
    private String longitude;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword()
    { return password;}

    public void setPassword(String password)
    {this.password = password;}

    public String getLatitude()
    {return latitude;}

    public void setLatitude(String latitude)
    {this.latitude = latitude;}

    public String getLongitude()
    {return longitude;}

    public void setLongitude(String longitude)
    {this.longitude = longitude;}

    @Override
    public String toString() {
        return username;
    }
}
