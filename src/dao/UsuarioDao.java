package dao;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

import connection.CreateConnection;
import model.UsuarioModel;

public class UsuarioDao {

    //insertar , lo cambie a int para que devuelva el id que se incerto
    public int insertar(UsuarioModel user) {

        //query -- se cambia por los procedures sp--tengan cuidado con esto!!!!!
        String query = "select sp_usuario_insertar(?,?,?,?)";
        //try
        try (Connection conn = CreateConnection.getInstancia().getConnection(); PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs  = ps.executeQuery() ) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRol());
            ps.setBoolean(4, user.isEstado());

            ps.executeUpdate();
            //parte donde se incerta el id-- se hizo en el procedure
            if(rs.next()){
                return rs.getInt(1);
            }
            //usaremos -1 para errores ya que tiene que retorna positivo
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
      

    }
      //actualizar
    public boolean actualizar(UsuarioModel user) {

        //query
        String query = "UPDATE usuario set username = ?, password = ?, rol = ?, estado = ? where id = ?";

        //try
        try (Connection conn = CreateConnection.getInstancia().getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRol());
            ps.setBoolean(4, user.isEstado());
            ps.setInt(5, user.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //consultar
    public List<UsuarioModel> listar() {
        List<UsuarioModel> lista = new ArrayList<>();

        //query
        String query = "SELECT * FROM usuario ORDER BY id";

        try (Connection conn = CreateConnection.getInstancia().getConnection(); PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                UsuarioModel user = new UsuarioModel(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("rol"),
                        rs.getBoolean("estado"),
                        rs.getTimestamp("creado_en").toLocalDateTime());
                lista.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;

    }

    //cambiar estado
    public boolean cambiarEstado(int id, boolean estado) {
        //query
        String query = "UPDATE usuario set estado = ? where id = ?";

        //try
        try (Connection conn = CreateConnection.getInstancia().getConnection(); PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setBoolean(1, estado);
            ps.setInt(2, id);
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
}
