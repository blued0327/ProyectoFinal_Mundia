import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
 
public class VentasDao {
 
    private Connection connection;
    private static final double IVA_PORCENTAJE = 0.12;
    private static final double DESCUENTO_5 = 0.05;
    private static final double DESCUENTO_7 = 0.07;
    private static final int MINIMO_DESCUENTO_5 = 5;
    private static final int MINIMO_DESCUENTO_7 = 10;
 
    public VentasDao(Connection connection) {
        this.connection = connection;
    }
 
  
    
      //Retorna todos los partidos disponibles para la venta.
     
    public ArrayList<PartidoModel> obtenerPartidosDisponibles() throws SQLException {
        ArrayList<PartidoModel> partidos = new ArrayList<>();
        String sql = "SELECT * FROM partidos WHERE fecha >= CURRENT_DATE ORDER BY fecha ASC";
 
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
 
            while (rs.next()) {
                PartidoModel partido = new PartidoModel(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDate("fecha").toLocalDate(),
                        rs.getString("estadio"),
                        rs.getString("equipo_local"),
                        rs.getString("equipo_visitante")
                );
                partidos.add(partido);
            }
        }
        return partidos;
    }
 
    
     //Retorna un partido por su ID.
     
    public PartidoModel obtenerPartidoPorId(int idPartido) throws SQLException {
        String sql = "SELECT * FROM partidos WHERE id = ?";
 
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idPartido);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new PartidoModel(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getDate("fecha").toLocalDate(),
                            rs.getString("estadio"),
                            rs.getString("equipo_local"),
                            rs.getString("equipo_visitante")
                    );
                }
            }
        }
        return null;
    }
 
    
    
     // Retorna la lista de boletos disponibles (no vendidos) para un partido específico.
     
    public ArrayList<Boletos> obtenerBoletosDisponibles(int idPartido) throws SQLException {
        ArrayList<Boletos> boletos = new ArrayList<>();
        String sql = "SELECT * FROM boletos WHERE id_partido = ? AND estado = 'DISPONIBLE' ORDER BY fila, asiento";
 
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idPartido);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Boletos boleto = new Boletos(
                            rs.getInt("id"),
                            rs.getString("partidoAsociado"),
                            rs.getString("asiento"),
                            rs.getString("seccion"),
                            rs.getDouble("precio"),
                            rs.getString("estado"),
                            idPartido
                    );
                    boletos.add(boleto);
                }
            }
        }
        return boletos;
    }
 
    /**
     * Verifica si un boleto específico sigue disponible antes de confirmar la compra.
     */
    public boolean verificarDisponibilidadBoleto(int idBoleto) throws SQLException {
        String sql = "SELECT estado FROM boletos WHERE id = ?";
 
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idBoleto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return "DISPONIBLE".equalsIgnoreCase(rs.getString("estado"));
                }
            }
        }
        return false;
    }
 
   
     // Busca un cliente existente por su DPI o identificación.
     
    public ClienteModel buscarClientePorDpi(String dpi) throws SQLException {
        String sql = "SELECT * FROM clientes WHERE nombre = ?";
 
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, dpi);
            try (ResultSet rs = ps.executeQuery()) {
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
            }
        }
        return null;
    }
 
    /**
     * Registra un nuevo cliente en el sistema.
     * Retorna el cliente con el ID generado por la base de datos.
     */
    public ClienteModel registrarCliente(ClienteModel cliente) throws SQLException {
        String sql = "INSERT INTO clientes (nombre, apellido, telefono, email, direccion) VALUES (?, ?, ?, ?, ?)";
 
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getTelefono());
            ps.setString(4, cliente.getEmail());
            ps.setString(5, cliente.getDireccion());
            ps.executeUpdate();
 
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    cliente.setId(keys.getInt(1));
                }
            }
        }
        return cliente;
    }
 
    
     //Calcula el subtotal sumando los precios de los boletos seleccionados.
     
    public double calcularSubtotal(ArrayList<Boletos> boletos) {
        double subtotal = 0;
        for (Boletos boleto : boletos) {
            subtotal += boleto.getPrecio();
        }
        return subtotal;
    }
 
    
     // Determina el porcentaje de descuento según la cantidad de boletos:
    
    public double calcularPorcentajeDescuento(int cantidadBoletos) {
        if (cantidadBoletos >= MINIMO_DESCUENTO_7) {
            return DESCUENTO_7;
        } else if (cantidadBoletos > MINIMO_DESCUENTO_5) {
            return DESCUENTO_5;
        }
        return 0.0;
    }
 
   
    public VentasModel calcularVenta(ClienteModel cliente,
                                     UsuarioModel usuario,
                                     PartidoModel partido,
                                     ArrayList<Boletos> boletosSeleccionados) {
 
        double subtotal = calcularSubtotal(boletosSeleccionados);
        double porcentajeDescuento = calcularPorcentajeDescuento(boletosSeleccionados.size());
        double descuento = subtotal * porcentajeDescuento;
        double baseImponible = subtotal - descuento;
        double iva = baseImponible * IVA_PORCENTAJE;
        double total = baseImponible + iva;
 
        return new VentasModel(
                0,                          // ID lo asignará la BD
                LocalDate.now(),
                cliente,
                usuario,
                partido,
                boletosSeleccionados,
                subtotal,
                iva,
                descuento,
                total
        );
    }
 
   
    public VentasModel confirmarCompra(VentasModel venta) throws SQLException {
        connection.setAutoCommit(false);
 
        try {
            // Verificar que todos los boletos siguen disponibles
            for (Boletos boleto : venta.getTicketsComprados()) {
                if (!verificarDisponibilidadBoleto(boleto.getId())) {
                    throw new SQLException("El boleto " + boleto.getFila()
                            + boleto.getAsiento() + " ya no está disponible.");
                }
            }
 
            int idVenta = insertarVenta(venta);
            venta.setId(idVenta);
 
            insertarDetalleVenta(idVenta, venta.getTicketsComprados());
 
            actualizarEstadoBoletos(venta.getTicketsComprados(), "VENDIDO");
 
            connection.commit();
            return venta;
 
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }
 
    
    private int insertarVenta(VentasModel venta) throws SQLException {
        String sql = "INSERT INTO ventas (fecha_compra, id_cliente, id_usuario, id_partido, "
                   + "subtotal, iva, descuento, total) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
 
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDate(1, Date.valueOf(venta.getFechaCompra()));
            ps.setInt(2, venta.getCliente().getId());
            ps.setInt(3, venta.getUsuarioVenta().getId());
            ps.setInt(4, venta.getPartido().getId());
            ps.setDouble(5, venta.getSubtotal());
            ps.setDouble(6, venta.getIva());
            ps.setDouble(7, venta.getDescuento());
            ps.setDouble(8, venta.getTotal());
            ps.executeUpdate();
 
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }
        throw new SQLException("No se pudo obtener el ID generado para la venta.");
    }
 
    private void insertarDetalleVenta(int idVenta, ArrayList<Boletos> boletos) throws SQLException {
        String sql = "INSERT INTO detalle_ventas (id_venta, id_boleto, precio_unitario) VALUES (?, ?, ?)";
 
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (Boletos boleto : boletos) {
                ps.setInt(1, idVenta);
                ps.setInt(2, boleto.getId());
                ps.setDouble(3, boleto.getPrecio());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
 
    private void actualizarEstadoBoletos(ArrayList<Boletos> boletos, String nuevoEstado) throws SQLException {
        String sql = "UPDATE boletos SET estado = ? WHERE id = ?";
 
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (Boletos boleto : boletos) {
                ps.setString(1, nuevoEstado);
                ps.setInt(2, boleto.getId());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }