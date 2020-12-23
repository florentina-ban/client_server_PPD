import model.DtoMic;
import services.IService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.DatabaseMetaData;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class StartClient {
    static IService server;

    public static class MyRunnable implements Runnable{

        @Override
        public void run() {
            DtoMic dtoMic = getDto();
            Integer rez = server.cumparaBilet(dtoMic.idSpect, LocalDate.now(), dtoMic.nrLoc, dtoMic.lista_loc);
            System.out.println(rez);
        }
    }

    private static DtoMic getDto(){
        int id =(int)(Math.random() *(3 - 1)) + 1;
        int nrBil = (int)(Math.random() *(10 - 1)) + 1;
        ArrayList<Integer> l =new ArrayList<>();
        for (int i=0; i<nrBil; i++){
            int loc =  (int)(Math.random() *(100 - 1)) + 1;
            if (l.contains(loc)){
                i--;
                continue;
            }
            l.add(loc);
        }
        DtoMic dtoMic = new DtoMic(id,nrBil,l);
        return dtoMic;
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        ApplicationContext factory = new ClassPathXmlApplicationContext("spring-client.xml");
        server = (IService) factory.getBean("serviceApplication");
        System.out.println("Obtained a reference to remote server");

        for(int i=0; i<20; i++){
            executor.submit(new MyRunnable());
        }

        executor.shutdown();
       }
}
