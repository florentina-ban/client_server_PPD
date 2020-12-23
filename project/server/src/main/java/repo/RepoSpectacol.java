package repo;

import model.Spectacol;
import model.Vanzare;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class RepoSpectacol {
    String fileName;
    ArrayList<Spectacol> spectacole;

    public RepoSpectacol() {
        spectacole = new ArrayList<>();
        fileName = "spectacole.txt";
        citesteDinFiser();
    }

    public Spectacol getOne(int id){
        Spectacol spectacol = spectacole.stream().filter(sp -> {return sp.getID_spectacol()==id;}).findFirst().get();
        return spectacol;
    }

    public void addSpectacol(int id_Spect, ArrayList<Integer> locuri){
        Spectacol sp = getOne(id_Spect);
        locuri.forEach(x->{sp.addLoc_Vandut(x);});

        updateSpectacole();
    }

    public void citesteDinFiser(){
        File file = new File(fileName);
        try {
            Scanner scanner = new Scanner(file);
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public synchronized void updateSpectacole(){
        try{
        FileWriter fileWriter = new FileWriter(fileName); //Set true for append mode
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
