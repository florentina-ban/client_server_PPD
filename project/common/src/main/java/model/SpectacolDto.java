package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class SpectacolDto {
    public int id;
    public LocalDate date;
    public boolean correct;
    public ArrayList<Integer> lista1, lista2;


    public SpectacolDto(int id, LocalDate date, boolean oku, ArrayList<Integer> lista1, ArrayList<Integer> lista2) {
        this.id = id;
        this.date = date;
        this.correct = oku;
        this.lista1 = lista1;
        this.lista2 = lista2;
    }
}
