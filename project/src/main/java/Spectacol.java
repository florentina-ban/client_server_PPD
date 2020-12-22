import java.time.LocalDate;
import java.util.ArrayList;

public class Spectacol {
    private int ID_spectacol;
    private LocalDate data_specatol;
    private String titlu;
    private double pret_bilet;
    private ArrayList<Integer> lista_locuri_vandute;
    private double sold;

    public Spectacol(int ID_spectacol, LocalDate data_specatol, String titlu, double pret_bilet) {
        this.ID_spectacol = ID_spectacol;
        this.data_specatol = data_specatol;
        this.titlu = titlu;
        this.pret_bilet = pret_bilet;
    }

    public int getID_spectacol() {
        return ID_spectacol;
    }

    public void setID_spectacol(int ID_spectacol) {
        this.ID_spectacol = ID_spectacol;
    }

    public LocalDate getData_specatol() {
        return data_specatol;
    }

    public void setData_specatol(LocalDate data_specatol) {
        this.data_specatol = data_specatol;
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
    public void substractSold(){
        this.sold-- ;
    }
}
