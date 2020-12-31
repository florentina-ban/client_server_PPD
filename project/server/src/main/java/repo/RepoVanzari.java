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
    }

    public synchronized void addVanzare(Vanzare vanzare){
        listaVanzari.add(vanzare);
        actualizareFisier(vanzare);
    }
    public void actualizareFisier(Vanzare vanzare){
        try {
            FileWriter fileWriter = new FileWriter(fileV, true); //Set true for append mode
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print(vanzare);
            printWriter.close();
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
