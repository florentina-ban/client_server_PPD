package repo;

import model.Vanzare;

import java.io.*;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;


public class RepoVanzari {
    String fileName;
    ArrayList<Vanzare> listaVanzari;

    public RepoVanzari() {
        this.listaVanzari =new ArrayList<>();
        fileName = "vanzari.txt";
    }

    public void addVanzare(Vanzare vanzare){
        listaVanzari.add(vanzare);
        actualizareFisier(vanzare);
    }
    public synchronized void actualizareFisier(Vanzare vanzare){
        try {
            FileWriter fileWriter = new FileWriter(fileName, true); //Set true for append mode
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
