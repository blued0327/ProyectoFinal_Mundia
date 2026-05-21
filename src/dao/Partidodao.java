package dao;

import model.PartidoModel;
import util.connection.       // ← ajusta si tu paquete de conexión tiene otro nombre

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la tabla 'partido'.
 * Todos los métodos abren y cierran su propia conexión.
 * Si tu equipo usa una conexión compartida, ajusta según su Conexion.java.
 */
public class PartidoDAO {

    // ─────────────────────────────────────────────
    //  INSERTAR
    // ─────────────────────────────────────────────
    public boolean insertar(PartidoModel p) {
        String sql = "INSERT INTO partido "
                   + "(equipo_local, equipo_visitante, fecha, estadio, ciudad, capacidad, estado) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getEquipoLocal());
            ps.setString(2, p.getEquipoVisitante());
            ps.setTimestamp(3, Timestamp.valueOf(p.getFecha()));
            ps.setString(4, p.getEstadio());
            ps.setString(5, p.getCiudad());
            ps.setInt(6, p.getCapacidad());
            ps.setString(7, p.getEstado());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar partido: " + e.getMessage());
            return false;
        }
    }

    // ─────────────────────────────────────────────
    //  MODIFICAR
    // ─────────────────────────────────────────────
    public boolean modificar(PartidoModel p) {
        String sql = "UPDATE partido SET "
                   + "equipo_local = ?, equipo_visitante = ?, fecha = ?, "
                   + "estadio = ?, ciudad = ?, capacidad = ?, estado = ? "
                   + "WHERE id = ?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getEquipoLocal());
            ps.setString(2, p.getEquipoVisitante());
            ps.setTimestamp(3, Timestamp.valueOf(p.getFecha()));
            ps.setString(4, p.getEstadio());
            ps.setString(5, p.getCiudad());
            ps.setInt(6, p.getCapacidad());
            ps.setString(7, p.getEstado());
            ps.setInt(8, p.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al modificar partido: " + e.getMessage());
            return false;
        }
    }

    // ─────────────────────────────────────────────
    //  ELIMINAR
    // ─────────────────────────────────────────────
    public boolean eliminar(int idPartido) {
        String sql = "DELETE FROM partido WHERE id = ?";
        try (Connection con = Connection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPartido);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar partido: " + e.getMessage());
            return false;
        }
    }

    // ─────────────────────────────────────────────
    //  LISTAR TODOS
    // ─────────────────────────────────────────────
    public List<PartidoModel> listarTodos() {
        List<PartidoModel> lista = new ArrayList<>();
        String sql = "SELECT id, equipo_local, equipo_visitante, fecha, estadio, "
                   + "ciudad, capacidad, estado, created_en FROM partido ORDER BY fecha";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar partidos: " + e.getMessage());
        }
        return lista;
    }

    // ─────────────────────────────────────────────
    //  BUSCAR POR ID
    // ─────────────────────────────────────────────
    public PartidoModel buscarPorId(int id) {
        String sql = "SELECT id, equipo_local, equipo_visitante, fecha, estadio, "
                   + "ciudad, capacidad, estado, created_en FROM partido WHERE id = ?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar partido por id: " + e.getMessage());
        }
        return null;
    }

    // ─────────────────────────────────────────────
    //  BUSCAR POR ESTADO  (útil para el módulo de Tickets)
    // ─────────────────────────────────────────────
    public List<PartidoModel> buscarPorEstado(String estado) {
        List<PartidoModel> lista = new ArrayList<>();
        String sql = "SELECT id, equipo_local, equipo_visitante, fecha, estadio, "
                   + "ciudad, capacidad, estado, created_en FROM partido WHERE estado = ? ORDER BY fecha";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, estado.toUpperCase());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar partidos por estado: " + e.getMessage());
        }
        return lista;
    }

    // ─────────────────────────────────────────────
    //  BUSCAR POR EQUIPO  (búsqueda parcial, útil para el JTextField de búsqueda)
    // ─────────────────────────────────────────────
    public List<PartidoModel> buscarPorEquipo(String texto) {
        List<PartidoModel> lista = new ArrayList<>();
        String sql = "SELECT id, equipo_local, equipo_visitante, fecha, estadio, "
                   + "ciudad, capacidad, estado, created_en FROM partido "
                   + "WHERE LOWER(equipo_local) LIKE ? OR LOWER(equipo_visitante) LIKE ? "
                   + "ORDER BY fecha";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String patron = "%" + texto.toLowerCase() + "%";
            ps.setString(1, patron);
            ps.setString(2, patron);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar partidos por equipo: " + e.getMessage());
        }
        return lista;
    }

    // ─────────────────────────────────────────────
    //  MAPEAR ResultSet → PartidoModel  (privado, evita repetir código)
    // ─────────────────────────────────────────────
    private PartidoModel mapear(ResultSet rs) throws SQLException {
        PartidoModel p = new PartidoModel();
        p.setId(rs.getInt("id"));
        p.setEquipoLocal(rs.getString("equipo_local"));
        p.setEquipoVisitante(rs.getString("equipo_visitante"));

        Timestamp ts = rs.getTimestamp("fecha");
        if (ts != null) p.setFecha(ts.toLocalDateTime());

        p.setEstadio(rs.getString("estadio"));
        p.setCiudad(rs.getString("ciudad"));
        p.setCapacidad(rs.getInt("capacidad"));
        p.setEstado(rs.getString("estado"));

        Timestamp creadoTs = rs.getTimestamp("created_en");
        if (creadoTs != null) p.setCreadoEn(creadoTs.toLocalDateTime());

        return p;
    }
}