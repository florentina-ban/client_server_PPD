package services;

import java.time.LocalDate;

public class ServiceImplementation implements IService {
    @Override
    public int cumparaBilet(int idSpectacol, LocalDate date, int nrBilete) {
        return 80;
    }
}
