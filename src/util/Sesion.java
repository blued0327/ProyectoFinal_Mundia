package util;

import model.UsuarioModel;

public class Sesion {

    private static UsuarioModel usuarioActual;
    
    
    //si es exitoso el login lo guardamos en esta variable
    public static void iniciar(UsuarioModel usuario) {
        usuarioActual = usuario;
    }
    //sino el usuario se queda null
    public static void cerrar() {
        usuarioActual = null;
    }
    
    //
    public static UsuarioModel getUsuario() {
        return usuarioActual;
    }
    // verfica que este activa la sesion y sea admin
    public static boolean esAdmin() {
        return usuarioActual != null && "ADMIN".equals(usuarioActual.getRol());
    }
// verfica que este activa la sesion y sea vendedor
    public static boolean esVendedor() {
        return usuarioActual != null && "VENDEDOR".equals(usuarioActual.getRol());
    }
}