package controller;

import dao.PartidoDao;
import model.PartidoModel;

import java.time.LocalDateTime;
import java.util.List;

public class PartidoController {

    private final PartidoDao dao = new PartidoDao();

    // insertar
    public boolean insertarPartido(String equipoLocal, String equipoVisitante,
            LocalDateTime fecha, String estadio,
            String ciudad, int capacidad, String estado) {
        PartidoModel p = new PartidoModel(
                equipoLocal, equipoVisitante,
                fecha, estadio, ciudad, capacidad, estado
        );
        return dao.insertar(p) > 0;
    }

    // el de actualizar
    public boolean actualizarPartido(int id, String equipoLocal, String equipoVisitante,
            LocalDateTime fecha, String estadio,
            String ciudad, int capacidad, String estado) {
        PartidoModel p = new PartidoModel(
                id, equipoLocal, equipoVisitante,
                fecha, estadio, ciudad, capacidad, estado, null
        );
        return dao.actualizar(p);
    }

    // eliminarR pone cancelado vía SP
    public boolean eliminarPartido(int id) {
        return dao.eliminar(id);
    }

    // listar a todos
    public List<PartidoModel> listarPartidos() {
        return dao.listarTodos();
    }

    // buscar por id
    public PartidoModel buscarPorId(int id) {
        return dao.buscarPorId(id);
    }

    // buscar por equipo
    public List<PartidoModel> buscarPorEquipo(String texto) {
        return dao.buscarPorEquipo(texto);
    }

    // listar los disponibles (sirve mas que nada para el modulo de ventas y tickets)
    public List<PartidoModel> listarDisponibles() {
        return dao.listarTodos()
                .stream()
                .filter(p -> PartidoDao.ESTADO_DISPONIBLE.equals(p.getEstado()))
                .toList();
    }
}
