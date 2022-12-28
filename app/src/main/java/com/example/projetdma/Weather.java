package com.example.projetdma;

class Weather {

    private Long date;
    private Double temp;
    private  String icon;

    public Double getTemp() {
        return temp;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }



    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Weather(Double temp, String icon) {
        this.temp = temp;
        this.icon = icon;
    }

    public Weather(Long date,Double temp, String icon) {
        this.date = date;
        this.temp = temp;
        this.icon = icon;
    }


}
