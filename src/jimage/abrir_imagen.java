/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jimage;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author yeray
 */
public class abrir_imagen {
    
    //private JLabel etiqueta_Imagen_Original;//Etiqueta donde se mostrara la imagen
    //public BufferedImage imagen;
    public String path;
        
     private String SelImagen (){
     
        //nos creamos el JFileChooser
        final JFileChooser fc = new JFileChooser();
        int seleccionfile = fc.showOpenDialog(main.getFrames()[0]);

        //si la opción es la correcta
        if (seleccionfile == JFileChooser.APPROVE_OPTION)
        {
        File fichero = fc.getSelectedFile();
        //obtenemos la ruta
               return fichero.getAbsolutePath();
        } else{
            return null;}
    
    }
    
    public BufferedImage LeerImagen (){
        path = SelImagen();
        BufferedImage imagen = null;      
       
        try{
           imagen = ImageIO.read(new File(path));

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "No se puede abrir la imagen");
        }
    return imagen;
    }
}
