import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import services.IService;
import services.ServiceImplementation;

import java.rmi.NoSuchObjectException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class StartServer {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        long end = start + 5*1000;

        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:server-spring.xml");

        TimerTask task = new TimerTask() {
            public void run() {
                try {
                    UnicastRemoteObject.unexportObject(factory.getBean("appService", services.ServiceImplementation.class),true);
                    System.out.println("stopped");

                } catch (NoSuchObjectException e) {
                    e.printStackTrace();
                }
            }
        };

        Timer timer = new Timer("Timer");
        long delay = 3000L;
        timer.scheduleAtFixedRate(task, new Date(), delay);

        System.out.println("server deschis! ");
    }
}