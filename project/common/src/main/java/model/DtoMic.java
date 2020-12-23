package model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DtoMic {
    public Integer idSpect;
    public Integer nrLoc;
    public ArrayList<Integer> lista_loc;

    public DtoMic(Integer idSpect, Integer nrLoc, ArrayList<Integer> lista_loc) {
        this.idSpect = idSpect;
        this.nrLoc = nrLoc;
        this.lista_loc = lista_loc;
    }
}
