/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import connection.CreateConnection;
import model.ClienteModel;
/**
 *
 * @author rchar
 */
public class ClienteDao {
    
    
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
        
     public boolean ModificarCliente(ClienteModel cm) {
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
     //esto no se hace
     /*
     public boolean EliminarCliente(int id) {
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
*/
     
     public ClienteModel BuscarConID(int id){
       String sql = "SELECT * FROM cliente WHERE id=?";
       
       try(Connection conn = CreateConnection.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
           ps.setInt(1, id);
           try(ResultSet rs = ps.executeQuery()){
               while(rs.next()){
                   return new ClienteModel(
                   rs.getInt("id"),
                   rs.getString("nombre"),
                   rs.getString("apellido"),
                   rs.getString("telefono"),
                   rs.getString("email"),
                   rs.getString("direccion"));
               }
           }
       }catch (SQLException e) {
            e.printStackTrace();
        }
         
         return null;
     }
    
    

}
