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

import javax.swing.JOptionPane;

/**
 *
 * @authors yeray, leti, leo.
 */
public class pixel_imagen {

    
    public void img_to_BW(BufferedImage img){
        double[] iArray = null;
        for (int x=0; x<img.getRaster().getWidth(); x++){
            for (int y=0; y<img.getRaster().getHeight(); y++){
               iArray = img.getRaster().getPixel(x, y, iArray);
               double gris = iArray[0] * 0.299 + iArray[1] * 0.587 + iArray[2] * 0.114 ;
               iArray[0] = gris;
               iArray[1] = gris;
               iArray[2] = gris;
               img.getRaster().setPixel(x, y, iArray);
                  
            }
        }
    }
    
    public int[] img_get_histogramaAbs(BufferedImage img){
        int[] sumArray = new int [256]; 
        int pos = 0;
        for(int i=0;i<sumArray.length;i++)
         sumArray[i] = 0;

        double[] iArray = null;
        for (int x=0; x<img.getRaster().getWidth(); x++){
            for (int y=0; y<img.getRaster().getHeight(); y++){
               iArray = img.getRaster().getPixel(x, y, iArray);
               pos = (int) iArray[0];
               sumArray[pos] +=1;                 
            }
        }
       return sumArray;
    }
    
    public int[] img_get_histogramaAcu(BufferedImage img){
         
        int [] acu_array = img_get_histogramaAbs(img);
        
        for (int i=1; i<acu_array.length; i++){
            acu_array[i] += acu_array[i-1];        
        }
        
    return acu_array;
    
    }
    
    public void brillo (BufferedImage img){
    	
        String StringBrillo = JOptionPane.showInputDialog("Introduzca el % de nivel de brillo");
        double brillo = Double.parseDouble(StringBrillo);
        brillo = brillo / 100;
        double pBrillo = 0.0;
        double[] iArray = null;
        
        for (int x=0; x<img.getRaster().getWidth(); x++){
            for (int y=0; y<img.getRaster().getHeight(); y++){
            	iArray = img.getRaster().getPixel(x, y, iArray);
            	pBrillo = brillo * iArray[0];
                
                if (pBrillo > 255)
                    pBrillo = 255;
                if (pBrillo < 0)
                    pBrillo = 0;
                
               iArray[0] = pBrillo;
               iArray[1] = pBrillo;
               iArray[2] = pBrillo;
               img.getRaster().setPixel(x, y, iArray);
            	
            }
        }
    	
    }
    
    public void contraste (BufferedImage img){
        String StringContraste = JOptionPane.showInputDialog("Introduzca el nivel de Contraste (+ aumentar) (- disminuir)");
        double contraste = Double.parseDouble(StringContraste);
        double pContraste = 0.0;
        double[] iArray = null;
        
        for (int x=0; x<img.getRaster().getWidth(); x++){
            for (int y=0; y<img.getRaster().getHeight(); y++){
            	iArray = img.getRaster().getPixel(x, y, iArray);
                pContraste = contraste + iArray[0];
                
                if (pContraste > 255)
                    pContraste = 255;
                if (pContraste < 0)
                    pContraste = 0;
                
               iArray[0] = pContraste;
               iArray[1] = pContraste;
               iArray[2] = pContraste;
               img.getRaster().setPixel(x, y, iArray);
            	
            }
        }
    	
        
        
        
        
        
 
    }
        
        
        
     public void tamanyo (BufferedImage img){
         int tam = 0;
         
         for (int x=0; x<img.getRaster().getWidth(); x++){
            for (int y=0; y<img.getRaster().getHeight(); y++){
                tam++;
                System.out.println ( " tamanio = "+ tam);
                
                
                
            }
         }
         
         
         
     }
        
}
    
    
    //GRIS = 0.299 R + 0.587 G + 0.114 B
    

