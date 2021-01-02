package services;

import model.Spectacol;
import model.SpectacolDto;
import model.Vanzare;
import repo.RepoSpectacol;
import repo.RepoVanzari;
import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.*;

public class ServiceImplementation extends UnicastRemoteObject implements IService {
    File fileVanzari, fileSpectacole;
    RepoVanzari repoVanzari;
    RepoSpectacol repoSpectacol;
    public List<IClient> clients;
    ExecutorService executor = Executors.newFixedThreadPool(20);

    public class WorkerVanzari implements Callable<Integer> {
        Vanzare v;

        public WorkerVanzari(Vanzare v) {
            this.v = v;
        }

        @Override
        public Integer call() {
            synchronized (repoVanzari) {
                try {
                    double suma = v.getNr_bilete_vandute() * repoSpectacol.getOne(v.getID_spectacol()).getPret_bilet();
                    v.setSuma(suma);

                    synchronized (fileVanzari) {
                        synchronized (fileSpectacole) {
                            int added = repoVanzari.addVanzare(v);
                            if (added==0) {
                                repoSpectacol.addSpectacol(v.getID_spectacol(), v.getLista_locuri_vandute());
                                return 0;
                            }
                        }
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    return -6;
                }
            }
            return -1;
        }
    }

    public class WorkerVerificari {
        ArrayList<Spectacol> listaSpactacole;
        ArrayList<Vanzare> listaVanzari;
        ArrayList<SpectacolDto> spectacolDtos = new ArrayList<>();

        public ArrayList<Vanzare> citesteVanzariDinFisier() {
            ArrayList<Vanzare> lista = new ArrayList<>();
            try {
                Scanner scanner = new Scanner(fileVanzari);
                while (scanner.hasNext()) {
                    String line1 = scanner.nextLine();
                    if (line1.trim().length() == 0)
                        continue;
                    String line2 = scanner.nextLine();
                    String line3 = scanner.nextLine();
                    String[] parts = line1.split(",");

                    int idSpec = Integer.parseInt(parts[0]);
                    LocalDate localDate = LocalDate.parse(parts[1]);
                    int nrLocuri = Integer.parseInt(parts[2]);

                    parts = line2.split(",");
                    ArrayList<Integer> bilete_vandute = new ArrayList<>();
                    for (String p : parts) {
                        try {
                            bilete_vandute.add(Integer.parseInt(p));
                        } catch (Exception ex) {
                        }
                    }
                    Double suma = Double.parseDouble(line3);
                    Vanzare vanzare = new Vanzare(idSpec, localDate, nrLocuri, bilete_vandute, suma);
                    lista.add(vanzare);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return lista;
        }

        public ArrayList<Spectacol> citesteSpectacoleDinFisier() {
            ArrayList<Spectacol> list = new ArrayList<>();
            try {
                Scanner scanner = new Scanner(fileSpectacole);
                while (scanner.hasNext()) {
                    String line1 = scanner.nextLine();
                    if (line1.trim().length() == 0)
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
                    for (String p : parts) {
                        try {
                            bilete_vandute.add(Integer.parseInt(p));
                        } catch (Exception ex) {
                        }
                    }
                    Double suma = Double.parseDouble(line3);
                    Spectacol spectacol = new Spectacol(idSpec, localDate, titlu, pretBilet, bilete_vandute, suma);
                    list.add(spectacol);
                }
                return list;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return list;
        }

        public void scrieVerificare() {
            File fileV = new File("verificare.txt");
            try {
                FileWriter fileWriter = new FileWriter(fileV, true); //Set true for append mode
                PrintWriter printWriter = new PrintWriter(fileWriter);
                spectacolDtos.forEach(spe -> {
                    String correct = spe.correct ? "corect" : "incorect";
                    printWriter.println("idSpectacol: " + spe.id + " verificationDate: " + LocalTime.now().toString() +
                            " nr vanzari: " + listaVanzari.size() + " -> " + correct + "\n" + spe.lista1 + "\n" + spe.lista2);
                });
                if (spectacolDtos.size() == 0)
                    printWriter.println("nicio vanzare");
                System.out.println("");
                printWriter.close();
            } catch (Exception ex) {

            }
        }

        public void run() {
            try {
                synchronized (fileVanzari) {
                    synchronized (fileSpectacole) {
                        listaVanzari = citesteVanzariDinFisier();
                        listaSpactacole = citesteSpectacoleDinFisier();
                    }
                }

                ArrayList<Spectacol> spectacole_din_vanzari = new ArrayList<>();
                listaVanzari.forEach(vanzare -> {
                    Optional<Spectacol> sp = spectacole_din_vanzari
                            .stream()
                            .filter(x -> x.getID_spectacol() == vanzare.getID_spectacol())
                            .findFirst();
                    if (sp.isPresent()) {
                        Spectacol spectacol = sp.get();
                        spectacol.addSold(vanzare.getSuma());
                        for (int i = 0; i < vanzare.getLista_locuri_vandute().size(); i++)
                            spectacol.addLoc_Vandut(vanzare.getLista_locuri_vandute().get(i));
                    } else {
                        Spectacol spectacol = new Spectacol(vanzare.getID_spectacol(), vanzare.getLista_locuri_vandute());
                        spectacol.addSold(vanzare.getSuma());
                        spectacole_din_vanzari.add(spectacol);
                    }
                });

                for (int i = 0; i < spectacole_din_vanzari.size(); i++) {
                    final int j = i;
                    Spectacol s1 = listaSpactacole
                            .stream()
                            .filter(x -> x.getID_spectacol() == spectacole_din_vanzari.get(j).getID_spectacol())
                            .findFirst()
                            .get();
                    Spectacol s2 = spectacole_din_vanzari.get(i);
                    if (!s1.equals(s2))
                        spectacolDtos.add(new SpectacolDto(s1.getID_spectacol(), LocalDate.now(), false, s1.getLista_locuri_cumparate(), s2.getLista_locuri_cumparate()));
                    else
                        spectacolDtos.add(new SpectacolDto(s1.getID_spectacol(), LocalDate.now(), true, s1.getLista_locuri_cumparate(), s2.getLista_locuri_cumparate()));
                }
                scrieVerificare();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public ServiceImplementation(RepoVanzari repoVanzari, RepoSpectacol repoSpectacol) throws RemoteException {
        super();
        this.repoVanzari = repoVanzari;
        this.repoSpectacol = repoSpectacol;
        this.fileSpectacole = new File("spectacole.txt");
        this.fileVanzari = new File("vanzari.txt");
        clients = new ArrayList<>();
        repoSpectacol.setFileSpec(fileSpectacole);
        repoVanzari.setFileV(fileVanzari);
        repoSpectacol.citesteDinFiser();

        //remove old verifications
        try {
            FileWriter fileWriter = new FileWriter(new File("verificare.txt"), false);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //schedule verifications at spesicific times
        try {
            TimerTask task = new TimerTask() {
                public void run() {
                    WorkerVerificari workerVerificari = new WorkerVerificari();
                    workerVerificari.run();
                }
            };
            Timer timer = new Timer("Timer");
            long delay = 5000L;
            timer.scheduleAtFixedRate(task, new Date(), delay);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void stopSterver(){

        this.clients.forEach(client -> {
            try {
                client.serverHasStopped();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public Integer cumparaBilet(int idSpectacol, LocalDate date, int nrBilete, ArrayList<Integer> locuri) {
        Vanzare v = new Vanzare(idSpectacol, date, nrBilete, locuri, 0);

        Callable<Integer> worker = new WorkerVanzari(v);
        Future<Integer> future = executor.submit(worker);
        try {
            return future.get();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
            return -3;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return -4;
        }
    }

    @Override
    public void addClient(IClient client) {
        clients.add(client);
    }
}
