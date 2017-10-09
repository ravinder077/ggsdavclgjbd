package com.tuespotsolutions.ravinder077.ggsdav;

/**
 * Created by ravinder077 on 25-09-2017.
 */

public class ActivityData

{
private String id;
    private String name;
    private String venue;
    private String Date;
    private String stdate;
    private String enddate;
    private String orgnaizer;
    private String contact;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    private String datetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getStdate() {
        return stdate;
    }

    public void setStdate(String stdate) {
        this.stdate = stdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getOrgnaizer() {
        return orgnaizer;
    }

    public void setOrgnaizer(String orgnaizer) {
        this.orgnaizer = orgnaizer;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
