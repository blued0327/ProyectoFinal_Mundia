/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daoCliente;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import connection.CreateConnection;
import modelCliente.ClienteModel;
/**
 *
 * @author rchar
 */
public class ClienteDao {
    
    private final Connection conn = CreateConnection.getInstancia().getConnection();
    
    public boolean RegistrarCLiente(ClienteModel cm){
        
        String sql = "INSERT INTO cliente (nombre, apellido, telefono, email, direccion) Values (?, ?, ?, ?, ?)";
        try (Connection conn = CreateConnection.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, cm.getNombre());
            ps.setString(2, cm.getApellido());
            ps.setString(3, cm.getTelefono());
            ps.setString(4, cm.getEmail());
            ps.setString(5, cm.getDireccion());
            ps.executeUpdate();
            
            conn.close();
            ps.close();
            return true;
                }catch (SQLException e) {
                    e.printStackTrace();
            return false;
        
    }
    }
        
     public boolean modificarCliente(ClienteModel cm) {
        String sql = "UPDATE cliente SET nombre = ?, apellido = ?, telefono = ?, email = ?, direccion = ? WHERE id = ?";
 
        try (Connection conn = CreateConnection.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            ps.setString(1, cm.getNombre());
            ps.setString(2, cm.getApellido());
            ps.setString(3, cm.getTelefono());
            ps.setString(4, cm.getEmail());
            ps.setString(5, cm.getDireccion());
            ps.setInt(6, cm.getId());
            ps.executeUpdate();
            return true;
 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
     
     public boolean eliminarCliente(int id) {
        String sql = "DELETE FROM cliente WHERE id = ?";
 
        try (Connection conn = CreateConnection.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
 
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    

}
