package proyectofinal_mundial;
import controller.ClientesController;
import model.ClienteModel;
import java.util.List;


public class ProyectoFinal_Mundial {

    public static void main(String[] args) {
        // Punto de entrada del programa
        // TODO: abrir LoginView cuando esté listo
        java.awt.EventQueue.invokeLater(() -> {
            // new view.LoginView().setVisible(true);
            System.out.println("Sistema Mundial 2026 - iniciar LoginView aquí");
            
            ClientesController controller = new ClientesController();
            // ── REGISTRAR ──────────────────────────────────────────
        System.out.println("=== REGISTRAR ===");
        controller.registrarCliente("Giovanni", "Charuc", "1111-2222", "Ronald@email.com", "yepo0capa");
        
        System.out.println("Clientes registrados");

        // ── LISTAR TODOS ───────────────────────────────────────
        System.out.println("\n=== LISTAR TODOS ===");
        List<ClienteModel> lista = controller.obtenerClientes();
        for (ClienteModel cm : lista) {
            System.out.println(cm.getId() + " | " + cm.getNombre() + " " + cm.getApellido() + " | " + cm.getEmail());
        }

        // ── BUSCAR POR ID ──────────────────────────────────────
        System.out.println("\n=== BUSCAR POR ID (id=1) ===");
        ClienteModel encontrado = controller.buscarPorId(1);
        if (encontrado != null) {
            System.out.println("Encontrado: " + encontrado.getNombre() + " " + encontrado.getApellido());
        } else {
            System.out.println("No encontrado");
        }

        // ── BUSCAR POR NOMBRE ──────────────────────────────────
        System.out.println("\n=== BUSCAR POR NOMBRE ('car') ===");
        List<ClienteModel> resultados = controller.buscarPorNombre("car");
        for (ClienteModel cm : resultados) {
            System.out.println("Encontrado: " + cm.getNombre() + " " + cm.getApellido());
        }

        // ── ACTUALIZAR ─────────────────────────────────────────
        System.out.println("\n=== ACTUALIZAR (id=1) ===");
        controller.actualizarCliente(3, "Giovanni", "Charuc", "1111-2222", "Ronald@email.com", "Nueva Direccion 789");
        System.out.println("Cliente actualizado");

        // ── CAMBIAR ESTADO ─────────────────────────────────────
        System.out.println("\n=== CAMBIAR ESTADO (id=2) ===");
        controller.cambiarEstado(2);
        System.out.println("Estado cambiado");

        // ── LISTAR DE NUEVO para verificar cambios ─────────────
        System.out.println("\n=== LISTAR FINAL ===");
        lista = controller.obtenerClientes();
        for (ClienteModel cm : lista) {
            System.out.println(cm.getId() + " | " + cm.getNombre() + " " + cm.getApellido() + " | " + cm.getEmail());
        }
    });
}
}



    
    

