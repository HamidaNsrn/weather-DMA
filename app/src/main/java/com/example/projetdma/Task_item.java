package com.example.projetdma;

public class Task_item {

    private String titledoes;
    private String datedoes;
    private String descdoes;
    private String keydoes; // ndiroha bch apres n9adro nrecupiriw les numta3 les attribut man dbb


    public Task_item() {
    }

    public Task_item(String titledoes, String datedoes, String descdoes, String keydoes) {
        this.titledoes = titledoes;
        this.datedoes = datedoes;
        this.descdoes = descdoes;
        this.keydoes = keydoes;

    }




    public String getDescdoes() {
        return descdoes;
    }
    public String getDatedoes() {
        return datedoes;
    }
    public String getKeydoes() {
        return keydoes;
    }
    public String getTitledoes() {
        return titledoes;
    }

    public void setTitledoes(String titledoes) {
        this.titledoes = titledoes;
    }
    public void setKeydoes(String keydoes) {
        this.keydoes = keydoes;
    }
    public void setDatedoes(String datedoes) {
        this.datedoes = datedoes;
    }
    public void setDescdoes(String descdoes) {
        this.descdoes = descdoes;
    }
}