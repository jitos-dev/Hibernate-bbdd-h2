package com.garciajuanjo.hibernatebbddh2.impl;

import com.garciajuanjo.hibernatebbddh2.entity.User;
import com.garciajuanjo.hibernatebbddh2.repository.UserRepository;
import com.garciajuanjo.hibernatebbddh2.util.HibernateUtil;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Juan José García Navarrete
 */
public class UserRepositoryImpl implements UserRepository {

    /**
     * Método para guardar un User en la bbdd. Primero guardamos el User con el método persist(User)
     * porque el método save(User) esta deprecated. Luego obtenemos el id del usuario que acabamos de
     * insertar con el método de Session getIdentifier(User) y utilizamos el método de la clase
     * getUserById(int id) para obtener el User y devolverlo
     *
     * @param user User que queremos guardar en la bbdd
     * @return User que hemos guardado en la bbdd
     */
    @Override
    public User saveUser(User user) {
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();

        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();

            //Ahora para guardar se utiliza persist() porque save() esta deprecated
            session.persist(user);

            /*Ahora recuperamos el usuario que acabamos de insertar para devolverlo con todos sus datos*/
            int idUsuario = (int) session.getIdentifier(user);

            //Hacemos commit para confirmar los cambios y cerramos sesión
            transaction.commit();

            //Utilizo otro método de esta clase para ahorrar código
            return getUserById(idUsuario);

        } catch (Exception e) {
            e.printStackTrace();
            rollbackTransaction(transaction);
            return null;
        }
    }


    /**
     * Método para eliminar un User de la bbdd. Creamos una consulta sql donde insertamos el id
     * que se pasa por parámetro. El método executeUpdate() devuelve el número de filas que se ven
     * afectadas por la consulta. Si no borra ningún registro devuelve 0 y por el contrario devuelve
     * el número de filas que ha borrado.
     *
     * @param idUser id del User que queremos eliminar
     * @return true o false dependiendo de si se ha borrado o no. Si es distinto de 0 es true.
     */
    @Override
    public boolean deleteUserById(int idUser) {
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();

        try (Session session = sessionFactory.openSession()) {

            String sql = "delete from User as u where u.id = :id";

            transaction = session.beginTransaction();

            /*El método createQuery lo marca como deprecated pero las alternativas que propone no funcionan y
            * si a ese método le pasamos aparte del sql la clase (User.class) ya no lo marca como deprecated pero
            * solo funciona con consultas Select y como esta es para delete pues da error. Ahora se utiliza el
            * metodo createMutationQuery como se muestra en el ejemplo
            session.createQuery(sql).setParameter("name", "Levi").executeUpdate();*/
            int result = session.createMutationQuery(sql)
                    .setParameter("id", idUser)
                    .executeUpdate();

            transaction.commit();

            return result != 0;

        } catch (Exception e) {
            e.printStackTrace();
            rollbackTransaction(transaction);
            return false;
        }
    }

    /**
     * Método para eliminar un User de la bbdd. Creamos una consulta sql donde insertamos el name
     * que se pasa por parámetro. El método executeUpdate() devuelve el número de filas que se ven
     * afectadas por la consulta. Si no borra ningún registro devuelve 0 y por el contrario devuelve
     * el número de filas que ha borrado.
     *
     * @param name name del User que queremos eliminar
     * @return true o false dependiendo de si se ha borrado o no. Si es distinto de 0 es true.
     */
    @Override
    public boolean deleteUserByName(String name) {
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();

        try (Session session = sessionFactory.openSession()) {

            String sql = "delete from User as u where u.name = :name";

            transaction = session.beginTransaction();

            int result = session.createMutationQuery(sql)
                    .setParameter("name", name)
                    .executeUpdate();

            transaction.commit();

            return result != 0;

        } catch (Exception e) {
            e.printStackTrace();
            rollbackTransaction(transaction);
            return false;
        }
    }

    /**
     * Método que nos devuelve una lista con todos los User que hay registrados en la bbdd.
     *
     * @return List<User> de los User registrados
     */
    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();

        try (Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();

            //Solo hay que poner from y la entidad. Con list() nos los devuelve todos
            List<User> users = session
                    .createQuery("from User", User.class)
                    .list();

            transaction.commit();

            return users;

        } catch (Exception e) {
            e.printStackTrace();
            rollbackTransaction(transaction);

            return new ArrayList<>();
        }
    }

    /**
     * Método que nos devuelve un User por el id que le pasamos por parámetro. Creamos una
     * consulta sql donde insetamos el id que se introduce por parámetro. Si lo encuentra devolvemos
     * el User y si hay cualquier error devolvemos null. Si no encuentra resultados lanza la excepción
     * NoResultException y en catch aparte de ejecutar el rolback() para no guardar cambios también
     * cerramos la sesión.
     *
     * @param id id del User que queremos buscar
     * @return User que obtenemos por el id
     */
    @Override
    public User getUserById(int id) {
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();

        try (Session session = sessionFactory.openSession()){

            transaction = session.beginTransaction();

            User user = session.createQuery("from User as u where u.id = :id", User.class)
                    .setParameter("id", id)
                    .getSingleResult();

            transaction.commit();

            return user;

        } catch (NoResultException nre) {
            //Entra aquí cuando no encuentra ningún User
            rollbackTransaction(transaction);
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            rollbackTransaction(transaction);
            return null;
        }
    }

    /**
     * Método para modificar un User de la bbdd. Creamos una consulta sql donde pasamos los
     * datos del usuario que queremos modificar. Si el User se modifica lo devolvemos con sus nuevos
     * datos y si no pues devolvemos null.
     *
     * @param user User que queremos modificar
     * @return User modificado o null si no lo modifica
     */
    @Override
    public User updateUser(User user) {
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();

        try (Session session = sessionFactory.openSession()) {

            String sql = "update User as u set u.name = :name where u.id = :id";

            transaction = session.beginTransaction();

            //executeUpdate() devuelve el número de filas afectadas por la consulta
            int num = session.createMutationQuery(sql)
                    .setParameter("name", user.getName())
                    .setParameter("id", user.getId())
                    .executeUpdate();

            transaction.commit();

            //Si no es 0 es porque ha modificado el usuario
            return num != 0 ? user : null;

        } catch (Exception e) {
            e.printStackTrace();
            rollbackTransaction(transaction);
            return null;
        }
    }

    /**
     * Método para simplificar el código en caso de que ocurra alguna excepción
     *
     * @param t transacction a la que hacer el rollback
     */
    private void rollbackTransaction(Transaction t) {
        if (t != null)
            t.rollback();
    }
}
