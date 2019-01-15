package com.rmit.tejas.foodtruck.model;

public abstract class AbstractTrackable {

    private String id;
    private String name;
    private String type;
    private String cusine;
    private String url;

    public AbstractTrackable(String id, String name, String type, String cusine, String url) {
        this.setId(id);
        this.setName(name);
        this.setType(type);
        this.setCusine(cusine);
        this.setUrl(url);
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCusine() {
        return cusine;
    }

    public void setCusine(String cusine) {
        this.cusine = cusine;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
