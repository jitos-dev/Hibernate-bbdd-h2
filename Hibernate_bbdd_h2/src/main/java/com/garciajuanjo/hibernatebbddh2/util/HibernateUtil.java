package com.garciajuanjo.hibernatebbddh2.util;

import lombok.NoArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Esta clase sigue el patrón Singleton para que solo pueda haber una instancia de SessionFactory en la app
 * @author Juan José García Navarrete
 */
@NoArgsConstructor
public class HibernateUtil {

    private static SessionFactory sesionFactory;
    private static HibernateUtil hibernateUtil;

    //Método synchronized para que no se pueda acceder a el hasta que no termine de ejecutarse
    private synchronized static void createInstance(){
        if (hibernateUtil == null)
            hibernateUtil = new HibernateUtil();
    }

    /*Devuelve una instancia de la clase. Comprueba si es null (porque no está inicializada) y si
    * no lo es la inicializa y la devuelve*/
    public static HibernateUtil getInstance() {
        if (hibernateUtil == null) {
            createInstance();
        }
        return hibernateUtil;
    }

    /*Devuelve el SessionFactory. Si no está inicializado lo inicializa primero*/
    public SessionFactory getSessionFactory(){
        if (sesionFactory == null) {
            sesionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sesionFactory;
    }

}
