import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import services.IClient;
import services.IService;

import java.rmi.RemoteException;
import java.util.concurrent.*;

public class StartClient {

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(10);
        ApplicationContext factory = new ClassPathXmlApplicationContext("spring-client.xml");
        IService service = (IService) factory.getBean("serviceClient");
        System.out.println("Obtained a reference to remote server");
        try {
            ClientGenerator clientGenerator = new ClientGenerator();
            clientGenerator.setServer(service);
            for (int i = 0; i < 6; i++) {
                IClient client = clientGenerator.getClient();
                service.addClient(client);
                executor.submit((Runnable) client);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }


        executor.shutdown();
        //System.exit(0);
    }
}
