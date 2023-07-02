package com.garciajuanjo.hibernatebbddh2;

import com.garciajuanjo.hibernatebbddh2.entity.User;
import com.garciajuanjo.hibernatebbddh2.impl.UserRepositoryImpl;
import com.garciajuanjo.hibernatebbddh2.repository.UserRepository;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Juan José García Navarrete
 */
public class UserRepositoryImplTest {

    private static UserRepository userRepository;

    @BeforeClass
    public static void beforeClass() {
        //Instanciamos el objeto antes de las pruebas
        userRepository = new UserRepositoryImpl();

        //Guardamos unos usuarios en la bbdd para la prueba. El valor de name es usuario + iteración
        for (int i = 0; i < 20; i++) {
            userRepository.saveUser(new User("usuario " + (i + 1)));
        }
    }

    @AfterClass
    public static void afterClass() {
        userRepository.getAllUsers().forEach(System.out::println);
    }

    @Test
    public void testSaveUser(){
        User user = new User("Juanjo");

        User userSave = userRepository.saveUser(user);

        assertNotNull("El usuario no se ha guardado en la bbdd", userSave);
    }

    @Test
    public void testDeleteUserById(){
        boolean delete = userRepository.deleteUserById(1);

       assertTrue(delete);
    }

    @Test
    public void testDeleteUserByName(){
        boolean delete = userRepository.deleteUserByName("usuario 5");

        assertTrue(delete);
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = userRepository.getAllUsers();

        /*Lo comprobamos al revés. Si users está vacío el método no hace lo que debe.
        * Si fuera assertTrue(users.isEmpty()) pasaría la prueba si está vacía y por eso
        * la comprobamos con assertFalse()*/
        assertFalse(users.isEmpty());
    }

    @Test
    public void testGetUserById() {
        User user = userRepository.getUserById(10);

        assertNotNull("No se ha encontrado el usuario con el id pasado", user);
    }

    @Test
    public void testUpdateUser() {
        int id = 2;
        User user = new User(id, "Prueba");
        User userUpdate = userRepository.updateUser(user);

        assertNotNull(userUpdate);
    }
}
