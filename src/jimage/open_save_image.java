package jimage;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author leo
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author yeray
 */
public class open_save_image {
    
    //private JLabel etiqueta_Imagen_Original;//Etiqueta donde se mostrara la imagen
    //public BufferedImage imagen;
    public String path;
        
     private String Selection_Dialog (){
     
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
    
    public BufferedImage abrir_imagen (){
        
        path = Selection_Dialog();
        BufferedImage imagen = null;      
       
        try{
           imagen = ImageIO.read(new File(path));

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "No se puede abrir la imagen");
        }    
    return imagen;
    }
    
    public void guardar_imagen (BufferedImage image){
         
        
                 
        final JFileChooser fc = new JFileChooser();
        int name = fc.showSaveDialog(main.getFrames()[0]);
        

        //si la opción es la correcta
        if (name == JFileChooser.APPROVE_OPTION)
        {
        File rutadestino = fc.getSelectedFile().getAbsoluteFile();
            try {

                BufferedWriter out = new BufferedWriter(new FileWriter(rutadestino));
                ImageIO.write(image, "JPG", rutadestino);

             } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            }
    }
    
    }
}
