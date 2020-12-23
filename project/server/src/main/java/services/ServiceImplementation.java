package services;

import model.Spectacol;
import model.Vanzare;
import repo.RepoSpectacol;
import repo.RepoVanzari;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.*;

public class ServiceImplementation implements IService {
    RepoVanzari repoVanzari;
    RepoSpectacol repoSpectacol;
    ExecutorService executor = Executors.newFixedThreadPool(20);

    public class MyCallable implements Callable<Integer> {
        Vanzare v;

        public MyCallable(Vanzare v) {
            this.v = v;
        }

        @Override
        public Integer call() throws Exception {
            if (valideazaVanzare(v)) {
                try {
                    repoVanzari.addVanzare(v);
                    repoSpectacol.addSpectacol(v.getID_spectacol(), v.getLista_locuri_vandute());
                }catch (Exception ex){
                    return -6;
                }
                return 0;
            }
            return -1;
        }

    }

    public ServiceImplementation(RepoVanzari repoVanzari, RepoSpectacol repoSpectacol) {
        this.repoVanzari = repoVanzari;
        this.repoSpectacol = repoSpectacol;

    }

    private boolean valideazaVanzare(Vanzare vanzare) {
        Spectacol spectacol = repoSpectacol.getOne(vanzare.getID_spectacol());
        ArrayList<Integer> locuriV = spectacol.getLista_locuri_cumparate();
        for (Integer loc : vanzare.getLista_locuri_vandute()) {
            if (locuriV.contains(loc)) {
                return false;
            }
        }
        return true;
    }



    @Override
    public Integer cumparaBilet(int idSpectacol, LocalDate date, int nrBilete, ArrayList<Integer> locuri) {
        Vanzare v = new Vanzare(idSpectacol, date, nrBilete,locuri, 100);

        Callable<Integer> worker = new MyCallable(v);
        Future<Integer> furure = executor.submit(worker);
        try {
            return furure.get();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
            return -3;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return -4;
        }
        //return -2;
    }
}
