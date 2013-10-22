/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jimage;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.image.BufferedImage;
import javax.swing.Icon;

/**
 *
 * @author yeray
 */
public class guardar_imagen {
    
    private JLabel etiqueta_Imagen_Original;//Etiqueta donde se mostrara la imagen
    public BufferedImage image;
    
       
     public void guardar (Icon ico){
         
         image = (BufferedImage) ico;
         
     
        //nos creamos el JFileChooser
         //image.getComponent(0);
         //JLabel pic = image.getComponentAt(5, 5);
         
        final JFileChooser fc = new JFileChooser();
        int name = fc.showSaveDialog(main.getFrames()[0]);
        

        //si la opción es la correcta
        if (name == JFileChooser.APPROVE_OPTION)
        {
        File rutadestino = fc.getSelectedFile().getAbsoluteFile();
            try {
                //if (rutadestino.getName().endsWith(".jpg")){
                BufferedWriter out = new BufferedWriter(new FileWriter(rutadestino));
                ImageIO.write(image, "JPG", rutadestino);
                //}
                //else{
                //}
                //}
           //out.write();
           //out.close(); 
             } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            }
    
    }
    
    
    }
}
