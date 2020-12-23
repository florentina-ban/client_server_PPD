package services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.Future;

public interface IService {
    Integer cumparaBilet(int idSpectacol, LocalDate date, int nrBilete, ArrayList<Integer> listaBilete);

}
