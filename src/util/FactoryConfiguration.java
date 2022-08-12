package util;

import enitity.Message;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;
    private SessionFactory sessionFactory;

    private FactoryConfiguration() {
        Properties properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemClassLoader().getResourceAsStream("hibernate.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Configuration configuration = new Configuration().mergeProperties(properties).addAnnotatedClass(Message.class);
        sessionFactory = configuration.buildSessionFactory();
    }

    public static FactoryConfiguration getFactoryConfiguration() {
        return factoryConfiguration == null ? factoryConfiguration = new FactoryConfiguration() : factoryConfiguration;
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }
}
