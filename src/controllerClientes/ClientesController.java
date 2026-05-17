/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllerClientes;

/**
 *
 * @author rchar
 */
import modelCliente.ClienteModel;
import daoCliente.ClienteDao;
import java.util.List;

public class ClientesController {
    
    
     private final ClienteDao dao = new ClienteDao();

    
 
    // Registrar cliente nuevo
    public void registrarCliente(String nombre, String apellido, String telefono, String email, String direccion) {
        ClienteModel cm = new ClienteModel();
        cm.setNombre(nombre);
        cm.setApellido(apellido);
        cm.setTelefono(telefono);
        cm.setEmail(email);
        cm.setDireccion(direccion);
        dao.RegistrarCLiente(cm);
    }
 
    // Modificar cliente existente
    public void modificarCliente(int id, String nombre, String apellido, String telefono, String email, String direccion) {
        ClienteModel cm = new ClienteModel();
        cm.setId(id);
        cm.setNombre(nombre);
        cm.setApellido(apellido);
        cm.setTelefono(telefono);
        cm.setEmail(email);
        cm.setDireccion(direccion);
        dao.ModificarCliente(cm);
    }
 
    // Eliminar cliente con id
    public void eliminarCliente(int id) {
        dao.EliminarCliente(id);
    }
    //Buscar cliente con id
    public ClienteModel buscarClientePorId(int id) {
        return dao.BuscarConID(id);
}
    
}
