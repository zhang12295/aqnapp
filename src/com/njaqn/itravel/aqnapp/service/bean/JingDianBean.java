package com.njaqn.itravel.aqnapp.service.bean;

public class JingDianBean
{

    private int id;
    private String name;
    private int spotId;

    private String intro;
    private double longitude;
    private double latitude;
    
    public String getIntro()
    {
        return intro;
    }

    public void setIntro(String intro)
    {
        this.intro = intro;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public int getId()
    {
	return id;
    }

    public void setId(int id)
    {
	this.id = id;
    }

    public String getName()
    {
	return name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    public int getSpotId()
    {
	return spotId;
    }

    public void setSpotId(int spotId)
    {
	this.spotId = spotId;
    }

}
