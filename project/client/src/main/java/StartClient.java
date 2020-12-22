import services.IService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;

public class StartClient {

    public static void main(String[] args) {
        ApplicationContext factory = new ClassPathXmlApplicationContext("spring-client.xml");
        IService server = (IService) factory.getBean("serviceApplication");
        System.out.println("Obtained a reference to remote server");

        int rez = server.cumparaBilet(0,null, 2);
        System.out.println(rez);
    }
}
