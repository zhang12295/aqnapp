package com.njaqn.itravel.aqnapp.service.bean;

public class JSpotBean
{

    private int id;
    private String intro;
    private String name;

    private String longitude;
    private String latitude;
    
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

    public String getIntro()
    {
	return intro;
    }

    public void setIntro(String intro)
    {
	this.intro = intro;
    }

    public String getLongitude()
    {
        return longitude;
    }

    public void setLongitude(String longitude)
    {
        this.longitude = longitude;
    }

    public String getLatitude()
    {
        return latitude;
    }

    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }

}
