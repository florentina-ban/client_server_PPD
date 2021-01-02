import model.DtoMic;
import services.IClient;
import services.IService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class ClientGenerator {
    public IService server;
    public Random rand;

    protected ClientGenerator() throws RemoteException {
        rand = new Random();
        rand.setSeed(System.nanoTime());
    }

    public void setServer(IService server) {
        this.server = server;
    }

    public IClient getClient() throws RemoteException {
        return new MyClient();
    }

    public class MyClient extends UnicastRemoteObject implements Runnable, IClient{
        boolean serverStopped = false;

        protected MyClient() throws RemoteException {
        }

        @Override
        public void run() {
            while (!serverStopped) {
                DtoMic dtoMic = getDto();
                System.out.println(dtoMic.lista_loc);
                Integer rez = server.cumparaBilet(dtoMic.idSpect, LocalDate.now(), dtoMic.nrLoc, dtoMic.lista_loc);
                System.out.println(rez.toString()+' ');
                if (rez == -100)
                    break;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        }

        public int getRandomInteger(int max){
            return rand.nextInt(max);
        }

        public DtoMic getDto() {
            int id = getRandomInteger(4);
            id = id==0? 1: id;
            int nrBil = getRandomInteger(6);
            nrBil = nrBil==0? 1: nrBil;
            ArrayList<Integer> l = new ArrayList<>();
            for (int i = 0; i < nrBil; i++) {
                int loc = getRandomInteger(101);
                loc = loc==0 ? 1 : loc;
                if (l.contains(loc)) {
                    i--;
                    continue;
                }
                l.add(loc);
            }
            return new DtoMic(id, nrBil, l);
        }

        @Override
        public int serverHasStopped() {
            System.out.println("server stopped");
            this.serverStopped = true;
            return 0;
        }
    }
}
