package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Spectacol {
    private int ID_spectacol;
    private LocalDate data_spectacol;
    private String titlu;
    private double pret_bilet;
    private ArrayList<Integer> lista_locuri_vandute;
    private double sold;

    public Spectacol(int ID_spectacol, LocalDate data_spectacol, String titlu, double pret_bilet) {
        this.ID_spectacol = ID_spectacol;
        this.data_spectacol = data_spectacol;
        this.titlu = titlu;
        this.pret_bilet = pret_bilet;
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

    public ArrayList<Integer> getLista_locuri_vandute() {
        return lista_locuri_vandute;
    }

    public void setLista_locuri_vandute(ArrayList<Integer> lista_locuri_vandute) {
        this.lista_locuri_vandute = lista_locuri_vandute;
    }

    public void addLoc_Vandut(int nrLoc){
        this.lista_locuri_vandute.add(nrLoc);
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
