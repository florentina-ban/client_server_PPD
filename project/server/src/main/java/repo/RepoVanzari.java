package repo;

import model.Vanzare;
import java.io.*;
import java.util.ArrayList;

public class RepoVanzari {
    File fileV;
    ArrayList<Vanzare> listaVanzari;

    public RepoVanzari() {
        this.listaVanzari =new ArrayList<>();
    }

    public void setFileV(File fileV) {
        this.fileV = fileV;
        try {
            FileWriter fileWriter = new FileWriter(fileV, false);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean valideazaVanzare(Vanzare vanzare) {
        int sum = 0;
        for (Integer loc_nou : vanzare.getLista_locuri_vandute()) {
            for (Vanzare vanzare1 : this.listaVanzari) {
                if (vanzare1.getID_spectacol() == vanzare.getID_spectacol()) {
                    if (vanzare1.getLista_locuri_vandute().contains(loc_nou))
                        sum += 1;
                }
            }
        }
        return sum==0;
    }

    public synchronized int addVanzare(Vanzare vanzare){
        if (valideazaVanzare(vanzare)) {
            listaVanzari.add(vanzare);
            actualizareFisier(vanzare);
            return 0;
        }
        else return -1;
    }
    public void actualizareFisier(Vanzare vanzare){
        FileWriter fileWriter;
        PrintWriter printWriter;
        try {
            fileWriter = new FileWriter(fileV, true); //Set true for append mode
            printWriter = new PrintWriter(fileWriter);
            printWriter.print(vanzare);
            printWriter.close();
            fileWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Vanzare> getAll(){
        return listaVanzari;
    }


}
