package repo;

import model.Spectacol;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class RepoSpectacol {
    File fileSpec;
    ArrayList<Spectacol> spectacole;

    public RepoSpectacol() {
        spectacole = new ArrayList<>();
    }

    public void setFileSpec(File fileSpec) {
        this.fileSpec = fileSpec;
    }

    public synchronized Spectacol getOne(int id){
        return spectacole.stream().filter(sp -> sp.getID_spectacol()==id).findFirst().get();
    }

    public synchronized void addSpectacol(int id_Spect, ArrayList<Integer> locuri){
        Spectacol sp = getOne(id_Spect);
        locuri.forEach(sp::addLoc_Vandut);

        updateSpectacole();
    }

    public void citesteDinFiser(){
        try {
            Scanner scanner = new Scanner(fileSpec);
            while (scanner.hasNext()){
                String line1 = scanner.nextLine();
                if (line1.trim().length()==0)
                    continue;
                String line2 = scanner.nextLine();
                String line3 = scanner.nextLine();
                String[] parts = line1.split(",");
                int idSpec = Integer.parseInt(parts[0]);
                LocalDate localDate = LocalDate.parse(parts[1]);
                String titlu = parts[2];
                double pretBilet = Double.parseDouble(parts[3]);
                parts = line2.split(",");
                ArrayList<Integer> bilete_vandute = new ArrayList<>();
                for (String p :parts) {
                    try{
                        bilete_vandute.add(Integer.parseInt(p));
                    }catch (Exception ex){
                    }
                }
                Double suma = Double.parseDouble(line3);
                Spectacol spectacol = new Spectacol(idSpec, localDate,titlu,pretBilet,bilete_vandute, suma);
                spectacole.add(spectacol);
            }
            emptySpectacolFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void emptySpectacolFile(){
        spectacole.forEach(spectacol -> {
            spectacol.setLista_locuri_cumparate(new ArrayList<>());
            spectacol.setSold(0.0);
            updateSpectacole();
        });

    }

    public void updateSpectacole(){
        try{
        FileWriter fileWriter = new FileWriter(fileSpec); //Set true for append mode
        PrintWriter printWriter = new PrintWriter(fileWriter);
        spectacole.forEach(spe -> {
            printWriter.println(spe);
        });
        printWriter.close();
    } catch (FileNotFoundException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
    } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
