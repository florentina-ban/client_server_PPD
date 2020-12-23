package model;

import java.util.ArrayList;

public class Sala {
    private int nr_locuri;
    private ArrayList<Integer> id_spectacole;

    public Sala(int nr_locuri, ArrayList<Integer> lista) {
        this.nr_locuri = nr_locuri;
        id_spectacole = lista;
    }

    public ArrayList<Integer> getId_spectacole() {
        return id_spectacole;
    }

    public int getNr_locuri() {
        return nr_locuri;
    }

    public void setNr_locuri(int nr_locuri) {
        this.nr_locuri = nr_locuri;
    }
}
