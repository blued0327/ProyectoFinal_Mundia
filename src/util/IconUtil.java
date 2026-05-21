package util;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class IconUtil {

    private static final String ruta_imagen = "/resources/logo.png";
    

    public static void setIcono(JFrame frame) {
        try {
            ImageIcon icono = new ImageIcon(
                IconUtil.class.getResource(ruta_imagen)
            );
            frame.setIconImage(icono.getImage());
        } catch (Exception e) {
            System.err.println("No se pudo cargar el icono: " + e.getMessage());
        }
    }
}