import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import services.IService;
import services.ServiceImplementation;

import java.rmi.NoSuchObjectException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;


public class StartServer {

    public static void main(String[] args) {
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:server-spring.xml");
        IService server = (IService) factory.getBean("appService");

        //schedule server to stop after specific time;
        TimerTask task = new TimerTask() {
            public void run() {
                try {
                    ServiceImplementation serv = (ServiceImplementation) server;
                    serv.stopSterver();
                    UnicastRemoteObject.unexportObject(serv, true);
                    System.out.println("stopped");
                    System.exit(0);
                } catch (NoSuchObjectException e) {
                    e.printStackTrace();
                }
            }
        };

        Timer timer = new Timer("Timer");
        long delay = 60000L;
        timer.schedule(task, delay);

        System.out.println("server deschis! ");
    }
}