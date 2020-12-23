package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Spectacol {
    private int ID_spectacol;
    private LocalDate data_spectacol;
    private String titlu;
    private double pret_bilet;
    private ArrayList<Integer> lista_locuri_cumparate;
    private double sold;

    public Spectacol(int ID_spectacol, LocalDate data_spectacol, String titlu, double pret_bilet) {
        this.ID_spectacol = ID_spectacol;
        this.data_spectacol = data_spectacol;
        this.titlu = titlu;
        this.pret_bilet = pret_bilet;
    }

    public Spectacol(int ID_spectacol, LocalDate data_spectacol, String titlu, double pret_bilet, ArrayList<Integer> lista_locuri_cumparate, double sold) {
        this.ID_spectacol = ID_spectacol;
        this.data_spectacol = data_spectacol;
        this.titlu = titlu;
        this.pret_bilet = pret_bilet;
        this.lista_locuri_cumparate = lista_locuri_cumparate;
        this.sold = sold;
    }

    public String listaString(){
        String s="";
        for (int i = 0; i< lista_locuri_cumparate.size(); i++)
            s+= lista_locuri_cumparate.get(i).toString()+",";
        return s;
    }
    @Override
    public String toString() {
        return ID_spectacol + ","+ data_spectacol + "," + titlu + "," +pret_bilet +"\n"+
                listaString() + "\n"+
                sold +"\n";
    }

    public int getID_spectacol() {
        return ID_spectacol;
    }

    public void setID_spectacol(int ID_spectacol) {
        this.ID_spectacol = ID_spectacol;
    }

    public LocalDate getData_spectacol() {
        return data_spectacol;
    }

    public void setData_spectacol(LocalDate data_specatol) {
        this.data_spectacol = data_specatol;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public double getPret_bilet() {
        return pret_bilet;
    }

    public void setPret_bilet(double pret_bilet) {
        this.pret_bilet = pret_bilet;
    }

    public ArrayList<Integer> getLista_locuri_cumparate() {
        return lista_locuri_cumparate;
    }

    public void setLista_locuri_cumparate(ArrayList<Integer> lista_locuri_cumparate) {
        this.lista_locuri_cumparate = lista_locuri_cumparate;
    }

    public void addLoc_Vandut(int nrLoc){
        this.lista_locuri_cumparate.add(nrLoc);
    }

    public double getSold() {
        return sold;
    }

    public void setSold(double sold) {
        this.sold = sold;
    }
    public void addSold(double sold){
        this.sold += sold ;
    }
}
