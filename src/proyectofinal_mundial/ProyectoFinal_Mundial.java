/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyectofinal_mundial;
import connection.CreateConnection;
import java.sql.*;
import dao.BoletosDao;
import java.util.List;
import model.BoletosModel;

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
         BoletosDao dao = new BoletosDao();

        try {

            // ================================
            // 1. GENERAR TICKET
            // ================================
            BoletosModel boleto = new BoletosModel(
                    0,              // id
                    "1",            // partido asociado
                    15,             // asiento
                    "VIP",          // sección
                    500.00,         // precio
                    "DISPONIBLE"    // estado
            );

            boolean creado = dao.generarTicket(boleto);

            if (creado) {
                System.out.println("✅ Ticket generado correctamente");
                System.out.println("ID generado: " + boleto.getId());
            } else {
                System.out.println("❌ No se pudo generar el ticket");
            }

            // ================================
            // 2. CONSULTAR DISPONIBLES
            // ================================
            System.out.println("\n📌 Tickets disponibles:");

            List<BoletosModel> disponibles =
                    dao.consultarDisponibles(1);

            for (BoletosModel b : disponibles) {

                System.out.println(
                        "ID: " + b.getId()
                        + " | Asiento: " + b.getAsiento()
                        + " | Sección: " + b.getSeccion()
                        + " | Precio: " + b.getPrecio()
                        + " | Estado: " + b.getEstado()
                );
            }

            // ================================
            // 3. RESERVAR ASIENTO
            // ================================
            System.out.println("\n🎟 Reservando asiento 15...");

            BoletosModel reservado =
                    dao.asignarAsiento(1, "15");

            if (reservado != null) {

                System.out.println("✅ Asiento reservado");
                System.out.println("Estado actual: "
                        + reservado.getEstado());

                // ================================
                // 4. CONFIRMAR VENTA
                // ================================
                boolean vendido =
                        dao.confirmarVenta(reservado.getId());

                if (vendido) {
                    System.out.println("💰 Venta confirmada");
                } else {
                    System.out.println("❌ No se pudo confirmar");
                }

            } else {
                System.out.println("❌ El asiento no está disponible");
            }

            // ================================
            // 5. LIBERAR ASIENTO
            // ================================
            boolean liberado = dao.liberarAsiento(1);

            if (liberado) {
                System.out.println("🔓 Asiento liberado");
            } else {
                System.out.println("❌ No se pudo liberar");
            }

        } catch (SQLException e) {

            System.out.println("🚨 Error SQL:");
            e.printStackTrace();

        } catch (Exception e) {

            System.out.println("🚨 Error general:");
            e.printStackTrace();
        }
    }
}
    
    
    

