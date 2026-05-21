/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyectofinal_mundial;

import connection.CreateConnection;
import java.sql.*;
import model.UsuarioModel;
import dao.UsuarioDao;
import java.util.List;

/**
 *
 * @author jdmm0
 */
public class ProyectoFinal_Mundial {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Connection conn = CreateConnection.getInstancia().getConnection();
        UsuarioDao dao = new UsuarioDao();

        // 1. INSERTAR
        System.out.println("=== INSERTAR ===");
        UsuarioModel nuevo = new UsuarioModel();
        nuevo.setUsername("jose_test");
        nuevo.setPassword("1234");
        nuevo.setRol("ADMIN");
        System.out.println(dao.insertar(nuevo) ? "Insertado OK" : "Error al insertar");

        // 2. LISTAR
        System.out.println("\n=== LISTAR ===");
        List<UsuarioModel> usuarios = dao.listar();
        for (UsuarioModel u : usuarios) {
            System.out.println(u.getId() + " | " + u.getUsername() + " | " + u.getRol() + " | activo: " + u.isEstado());
        }

        // 3. CAMBIAR ESTADO (desactivar el último insertado)
        if (!usuarios.isEmpty()) {
            int ultimoId = usuarios.get(usuarios.size() - 1).getId();
            System.out.println("\n=== CAMBIAR ESTADO (desactivar id " + ultimoId + ") ===");
            System.out.println(dao.cambiarEstado(ultimoId, false) ? "Cambiado OK" : "Error");
        }

        // 4. LISTAR DE NUEVO para verificar el cambio
        System.out.println("\n=== LISTAR DESPUÉS ===");
        for (UsuarioModel u : dao.listar()) {
            System.out.println(u.getId() + " | " + u.getUsername() + " | activo: " + u.isEstado());
        }

    }

}
