package dao;

import connection.CreateConnection;
import model.ClienteModel;
import model.DetalleVentaModel;
import model.TicketModel;
import model.VentasModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentasDao {

    //constantes de negocio - iva 12%, descuento 5% si mas de 5 boletas, 7% si 10 o mas
    private static final BigDecimal IVA_PORCENTAJE = new BigDecimal("0.12");
    private static final BigDecimal DESCUENTO_5    = new BigDecimal("0.05");
    private static final BigDecimal DESCUENTO_7    = new BigDecimal("0.07");
    private static final int LIMITE_DESC_5         = 5;
    private static final int LIMITE_DESC_7         = 10;

    //calcular totales - subtotal, descuento, iva y total en base a los tickets seleccionados
    public VentasModel calcularTotales(List<TicketModel> ticketsSeleccionados) {

        //sumar precios
        BigDecimal subtotal = BigDecimal.ZERO;
        for (TicketModel t : ticketsSeleccionados) {
            subtotal = subtotal.add(t.getPrecio());
        }

        //descuento segun cantidad
        BigDecimal porcentajeDescuento = BigDecimal.ZERO;
        int cantidad = ticketsSeleccionados.size();
        if (cantidad >= LIMITE_DESC_7) {
            porcentajeDescuento = DESCUENTO_7;
        } else if (cantidad > LIMITE_DESC_5) {
            porcentajeDescuento = DESCUENTO_5;
        }

        //calcular
        BigDecimal descuento = subtotal.multiply(porcentajeDescuento).setScale(2, RoundingMode.HALF_UP);
        BigDecimal baseIva   = subtotal.subtract(descuento);
        BigDecimal totalIva  = baseIva.multiply(IVA_PORCENTAJE).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total     = baseIva.add(totalIva).setScale(2, RoundingMode.HALF_UP);

        //armar el modelo con los totales
        VentasModel venta = new VentasModel();
        venta.setSubtotal(subtotal);
        venta.setDescuento(descuento);
        venta.setTotalIva(totalIva);
        venta.setTotal(total);
        return venta;
    }

    //registrar venta - inserta venta, detalles y actualiza tickets en una transaccion
    public int registrarVenta(VentasModel venta, List<TicketModel> tickets) {

        //queries
        String sqlVenta   = "INSERT INTO venta(cliente_id, usuario_id, subtotal, descuento, total_iva, total) VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
        String sqlDetalle = "INSERT INTO detalle_venta(venta_id, ticket_id, precio, iva) VALUES (?, ?, ?, ?)";
        String sqlTicket  = "UPDATE ticket SET estado = 'VENDIDO' WHERE id = ?";

        Connection conn = null;
        try {
            conn = CreateConnection.getInstancia().getConnection();
            conn.setAutoCommit(false);

            //insertar cabecera de venta
            int ventaId = -1;
            try (PreparedStatement ps = conn.prepareStatement(sqlVenta)) {
                ps.setInt(1, venta.getClienteId());
                ps.setInt(2, venta.getUsuarioId());
                ps.setBigDecimal(3, venta.getSubtotal());
                ps.setBigDecimal(4, venta.getDescuento());
                ps.setBigDecimal(5, venta.getTotalIva());
                ps.setBigDecimal(6, venta.getTotal());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    ventaId = rs.getInt(1);
                }
            }

            if (ventaId == -1) throw new SQLException("no se pudo obtener el id de la venta");

            //insertar detalles y marcar tickets como vendidos
            try (PreparedStatement psDetalle = conn.prepareStatement(sqlDetalle);
                 PreparedStatement psTicket  = conn.prepareStatement(sqlTicket)) {

                for (TicketModel ticket : tickets) {
                    //iva por ticket
                    BigDecimal precioConDescuento = ticket.getPrecio()
                            .multiply(BigDecimal.ONE.subtract(
                                venta.getDescuento().divide(venta.getSubtotal(), 10, RoundingMode.HALF_UP)
                            )).setScale(2, RoundingMode.HALF_UP);
                    BigDecimal ivaTicket = precioConDescuento.multiply(IVA_PORCENTAJE).setScale(2, RoundingMode.HALF_UP);

                    psDetalle.setInt(1, ventaId);
                    psDetalle.setInt(2, ticket.getId());
                    psDetalle.setBigDecimal(3, ticket.getPrecio());
                    psDetalle.setBigDecimal(4, ivaTicket);
                    psDetalle.executeUpdate();

                    //actualizar estado del ticket
                    psTicket.setInt(1, ticket.getId());
                    psTicket.executeUpdate();
                }
            }

            //todo bien, confirmar
            conn.commit();
            System.out.println("venta registrada con id: " + ventaId);
            return ventaId;

        } catch (SQLException e) {
            //algo fallo, rollback
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
            return -1;
        } finally {
            try { if (conn != null) { conn.setAutoCommit(true); conn.close(); } } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    //listar ventas con cliente y usuario
    public List<VentasModel> listar() {
        List<VentasModel> lista = new ArrayList<>();

        //query
        String query = "SELECT v.id, v.fecha, v.cliente_id, v.usuario_id, v.subtotal, "
                     + "v.descuento, v.total_iva, v.total, v.anulada, "
                     + "c.nombre || ' ' || c.apellido AS cliente_nombre, u.username "
                     + "FROM venta v "
                     + "LEFT JOIN cliente c ON c.id = v.cliente_id "
                     + "LEFT JOIN usuario u ON u.id = v.usuario_id "
                     + "ORDER BY v.fecha DESC";

        //try
        try (Connection conn = CreateConnection.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                VentasModel v = new VentasModel(
                        rs.getInt("id"),
                        rs.getTimestamp("fecha").toLocalDateTime(),
                        rs.getInt("cliente_id"),
                        rs.getInt("usuario_id"),
                        rs.getBigDecimal("subtotal"),
                        rs.getBigDecimal("descuento"),
                        rs.getBigDecimal("total_iva"),
                        rs.getBigDecimal("total"),
                        rs.getBoolean("anulada")
                );
                v.setClienteNombre(rs.getString("cliente_nombre"));
                v.setUsuarioUsername(rs.getString("username"));
                lista.add(v);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    //listar tickets disponibles por partido
    public List<TicketModel> listarTicketsDisponibles(int partidoId) {
        List<TicketModel> lista = new ArrayList<>();

        //query
        String query = "SELECT t.id, t.partido_id, t.numero_asiento, t.seccion, t.precio, t.estado, "
                     + "p.equipo_local, p.equipo_visitante "
                     + "FROM ticket t "
                     + "JOIN partido p ON p.id = t.partido_id "
                     + "WHERE t.partido_id = ? AND t.estado = 'DISPONIBLE' "
                     + "ORDER BY t.seccion, t.numero_asiento";

        //try
        try (Connection conn = CreateConnection.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, partidoId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TicketModel t = new TicketModel(
                        rs.getInt("id"),
                        rs.getInt("partido_id"),
                        rs.getString("numero_asiento"),
                        rs.getString("seccion"),
                        rs.getBigDecimal("precio"),
                        rs.getString("estado")
                );
                t.setEquipoLocal(rs.getString("equipo_local"));
                t.setEquipoVisitante(rs.getString("equipo_visitante"));
                lista.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    //buscar cliente por email
    public ClienteModel buscarClientePorEmail(String email) {

        //query
        String query = "SELECT id, nombre, apellido, telefono, email, direccion FROM cliente WHERE email = ?";

        //try
        try (Connection conn = CreateConnection.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new ClienteModel(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        rs.getString("direccion")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //registrar cliente nuevo
    public int registrarCliente(ClienteModel cliente) {

        //query
        String query = "INSERT INTO cliente(nombre, apellido, telefono, email, direccion) VALUES (?, ?, ?, ?, ?) RETURNING id";

        //try
        try (Connection conn = CreateConnection.getInstancia().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getTelefono());
            ps.setString(4, cliente.getEmail());
            ps.setString(5, cliente.getDireccion());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
