package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Vanzare {
    private int ID_spectacol;
    private LocalDate data_vanzare;
    private int nr_bilete_vandute;
    private ArrayList<Integer> lista_locuri_vandute;
    private double suma;

    public Vanzare(int ID_spectacol, LocalDate data_vanzare, int nr_bilete_vandute, ArrayList<Integer> locuri, double suma) {
        this.ID_spectacol = ID_spectacol;
        this.data_vanzare = data_vanzare;
        this.nr_bilete_vandute = nr_bilete_vandute;
        this.suma = suma;
        this.lista_locuri_vandute = locuri;
    }

    public int getID_spectacol() {
        return ID_spectacol;
    }

    public void setID_spectacol(int ID_spectacol) {
        this.ID_spectacol = ID_spectacol;
    }

    public LocalDate getData_vanzare() {
        return data_vanzare;
    }

    public void setData_vanzare(LocalDate data_vanzare) {
        this.data_vanzare = data_vanzare;
    }

    public int getNr_bilete_vandute() {
        return nr_bilete_vandute;
    }

    public void setNr_bilete_vandute(int nr_bilete_vandute) {
        this.nr_bilete_vandute = nr_bilete_vandute;
    }

    public ArrayList<Integer> getLista_locuri_vandute() {
        return lista_locuri_vandute;
    }

    public void setLista_locuri_vandute(ArrayList<Integer> lista_locuri_vandute) {
        this.lista_locuri_vandute = lista_locuri_vandute;
    }

    public void addLoc_Vandut(int nrLoc){
        this.lista_locuri_vandute.add(nrLoc);
    }

    public double getSuma() {
        return suma;
    }

    public void setSuma(double suma) {
        this.suma = suma;
    }

    public String listaString(){
        String s="";
        for (int i=0; i<lista_locuri_vandute.size(); i++)
            s+=lista_locuri_vandute.get(i).toString()+",";
        return s;
    }
    @Override
    public String toString() {
        return ID_spectacol+"," + data_vanzare+","+ nr_bilete_vandute +"\n"
                + listaString() + "\n"+ suma +"\n";
    }
}
