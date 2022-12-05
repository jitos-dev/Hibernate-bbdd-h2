package com.garciajuanjo.hibernatebbddh2;

import com.garciajuanjo.hibernatebbddh2.entity.User;
import com.garciajuanjo.hibernatebbddh2.impl.UserRepositoryImpl;
import com.garciajuanjo.hibernatebbddh2.repository.UserRepository;

import java.util.List;

/**
 * @author Juan José García Navarrete
 */
public class App {

    //Instancia de UserRepository para operaciones con la bbdd
    private static final UserRepository userRepository = new UserRepositoryImpl();

    public static void deleteUserById(int id){
        if (userRepository.deleteUserById(id))
            System.out.println("El usuario con id " + id + " se ha borrado con éxito");
        else
            System.out.println("El usuario no se ha encontrado y no se puede borrar");
    }

    public static void deleteUserByName(String name){
        if (userRepository.deleteUserByName(name))
            System.out.println("El usuario con nombre " + name + " se ha borrado con éxito");
        else
            System.out.println("El usuario no se ha encontrado y no se puede borrar");
    }

    public static void getAllUsers(){
        List<User> users = userRepository.getAllUsers();
        if (users.isEmpty())
            System.out.println("No tiene usuarios registrados actualmente");
        else
            users.forEach(System.out::println);
    }

    public static void getUserById(int id) {
        User user = userRepository.getUserById(id);
        if (user != null)
            System.out.println("Usuario encontrado: " + user);
        else
            System.out.println("No se ha encontrado ningún usuario con el id introducido");
    }


    public static void updateUser(User user){
        User userUpdate = userRepository.updateUser(user);
        if (userUpdate != null)
            System.out.println("El usuario se ha modificado con éxito. " + user);
        else
            System.out.println("El usuario no se ha podido modificar");
    }


    public static void saveUser(User user) {
        User userSave = userRepository.saveUser(user);
        if (userSave != null)
            System.out.println("Usuario guardado con éxito: " + userSave);
        else
            System.out.println("El usuario no se pudo guardar en la bbdd: " + user);
    }

    public static void mockUsers(int numUsers) {
        for (int i = 0; i < numUsers; i++) {
            saveUser(new User("usuario " + (i + 1)));
        }
    }



    public static void main(String[] args) {
        User user1 = new User(8, "Juanjose García");
        User user2 = new User("Maria Isabel");

        //Insertamos unos usuarios para las pruebas
        mockUsers(10);

        //Vamos utilizando todos los métodos del programa
        getAllUsers();
        saveUser(user2);
        deleteUserById(6);
        deleteUserByName("usuario 2");
        getUserById(8);
        updateUser(user1);
        getAllUsers();

    }
}
