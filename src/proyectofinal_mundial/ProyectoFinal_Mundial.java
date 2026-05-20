/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyectofinal_mundial;
import connection.CreateConnection;
import java.sql.*;
import dao.ClienteDao;
import model.ClienteModel;

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
        
        ClienteDao dao = new ClienteDao();

        // ===== PROBAR INSERT =====
        ClienteModel cliente = new ClienteModel(
                0,
                "Ronald",
                "Charuc",
                "12345678",
                "ronald@gmail.com",
                "Guatemala"
        );

        boolean registrado = dao.RegistrarCLiente(cliente);

        if (registrado) {
            System.out.println("Cliente registrado correctamente");
        } else {
            System.out.println("Error al registrar cliente");
        }


        // ===== PROBAR BUSCAR =====
        ClienteModel encontrado = dao.BuscarConID(1);

        if (encontrado != null) {
            System.out.println("Cliente encontrado:");
            System.out.println("Nombre: " + encontrado.getNombre());
            System.out.println("Apellido: " + encontrado.getApellido());
            System.out.println("Telefono: " + encontrado.getTelefono());
        } else {
            System.out.println("Cliente no encontrado");
        }


        // ===== PROBAR MODIFICAR =====
        ClienteModel modificar = new ClienteModel(
                1,
                "Ronald Modificado",
                "Charuc",
                "87654321",
                "nuevo@gmail.com",
                "Zona 1"
        );

        boolean modificado = dao.ModificarCliente(modificar);

        if (modificado) {
            System.out.println("Cliente modificado correctamente");
        } else {
            System.out.println("Error al modificar");
        }


        // ===== PROBAR ELIMINAR =====
        boolean eliminado = dao.EliminarCliente(1);

        if (eliminado) {
            System.out.println("Cliente eliminado correctamente");
        } else {
            System.out.println("Error al eliminar");
        }
    }
}
    
    
    

