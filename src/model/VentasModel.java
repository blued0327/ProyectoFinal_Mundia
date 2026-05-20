package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class VentasModel {

    private int id;
    private LocalDateTime fecha;
    private int clienteId;
    private int usuarioId;
    private BigDecimal subtotal;
    private BigDecimal descuento;
    private BigDecimal totalIva;
    private BigDecimal total;
    private boolean anulada;

    //para mostrar en pantalla con joins
    private String clienteNombre;
    private String usuarioUsername;
    private List<DetalleVentaModel> detalles = new ArrayList<>();

    //vacio
    public VentasModel() {}

    //todos
    public VentasModel(int id, LocalDateTime fecha, int clienteId, int usuarioId,
                       BigDecimal subtotal, BigDecimal descuento, BigDecimal totalIva,
                       BigDecimal total, boolean anulada) {
        this.id        = id;
        this.fecha     = fecha;
        this.clienteId = clienteId;
        this.usuarioId = usuarioId;
        this.subtotal  = subtotal;
        this.descuento = descuento;
        this.totalIva  = totalIva;
        this.total     = total;
        this.anulada   = anulada;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getTotalIva() {
        return totalIva;
    }

    public void setTotalIva(BigDecimal totalIva) {
        this.totalIva = totalIva;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public boolean isAnulada() {
        return anulada;
    }

    public void setAnulada(boolean anulada) {
        this.anulada = anulada;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public String getUsuarioUsername() {
        return usuarioUsername;
    }

    public void setUsuarioUsername(String usuarioUsername) {
        this.usuarioUsername = usuarioUsername;
    }

    public List<DetalleVentaModel> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVentaModel> detalles) {
        this.detalles = detalles;
    }
    
    
    
}
