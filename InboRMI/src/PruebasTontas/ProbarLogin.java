/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PruebasTontas;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import mx.inbo.controllers.UserJpaController;
import mx.inbo.entities.User;
import mx.inbo.exception.CustomException;

/**
 *
 * @author BODEGA
 */
public class ProbarLogin {
    public static void main(String args[]){
        System.out.println("Buscar un usuario");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("InboRMIPU");
        UserJpaController ujc = new UserJpaController(emf);
        
        try {
            ujc.validarLogin("Rainbow", "1345");
            System.out.println("Usuario encontrado");
        } catch (SQLException | CustomException | NoResultException ex) {
            System.out.println(ex.getMessage());
        }
        User usuario = ujc.findUser(1);
        try {
            ujc.correoSignup(usuario);
        } catch (MessagingException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
