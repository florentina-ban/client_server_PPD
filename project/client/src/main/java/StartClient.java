import model.DtoMic;
import services.IService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.*;

public class StartClient {
    static IService server;

    public static class MyCallable implements Callable<Integer> {

        @Override
        public Integer call() {
            DtoMic dtoMic = getDto();
            return server.cumparaBilet(dtoMic.idSpect, LocalDate.now(), dtoMic.nrLoc, dtoMic.lista_loc);
        }
    }

    private static DtoMic getDto(){
        Random rand = new Random(System.currentTimeMillis());
        int id =(rand.nextInt() *(4 - 1)) + 1;
        int nrBil = (rand.nextInt() *(5 - 1)) + 1;
        ArrayList<Integer> l =new ArrayList<>();
        for (int i=0; i<nrBil; i++){
            int loc =  (rand.nextInt() *(100 - 1)) + 1;
            if (l.contains(loc)){
                i--;
                continue;
            }
            l.add(loc);
        }
        return new DtoMic(id,nrBil,l);
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        ApplicationContext factory = new ClassPathXmlApplicationContext("spring-client.xml");
        server = (IService) factory.getBean("serviceApplication");
        System.out.println("Obtained a reference to remote server");

        for(int i=0; i<200; i++){
            Future<Integer> rez = executor.submit(new MyCallable());
            try {
                if (rez.get()==-100)
                    break;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            } catch (InterruptedException | ExecutionException exception) {
                exception.printStackTrace();
            }
        }
        executor.shutdown();
       }
}
