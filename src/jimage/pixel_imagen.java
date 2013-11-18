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


import java.awt.Color;
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
   public double getBrillo(BufferedImage img){
        double brill=0;
        int histogramaAbs[] = img_get_histogramaAbs(img);
        for (int i=0; i<histogramaAbs.length; i++){
            brill += histogramaAbs[i]*i;
        
        }
        return brill/(width(img)*height(img));
    }
    public double getContraste(BufferedImage img){
    double contr=0;
    int histogramaAbs[] = img_get_histogramaAbs(img);
    for (int i=0; i<histogramaAbs.length; i++){
        contr += Math.pow(i-getBrillo(img), 2)* histogramaAbs[i];

    }
    return Math.sqrt(contr / (width(img)*height(img)));
}
    
    public void brillo (BufferedImage img){
        String StringBrillo = JOptionPane.showInputDialog("Introduzca el % de nivel de brillo");
        brilloContraste (img, img, Double.parseDouble(StringBrillo),0);
                
    }
    
    public void brilloContraste (BufferedImage img, BufferedImage dest_image, double br, double cr){
    	
        //String StringBrillo = JOptionPane.showInputDialog("Introduzca el % de nivel de brillo");
        //double brillo = Double.parseDouble(StringBrillo);
       // double brillo = br / 100;
        //double brillo = 2.56 * br;
        double original= 0.0;
        double A = cr / getContraste(img);
        double B = br - A * getBrillo(img);
        
        //double brillo = br / 100;
      int pBrillo = 0;
      double[] iArray = null;
        
        for (int x=0; x<img.getRaster().getWidth(); x++){
            for (int y=0; y<img.getRaster().getHeight(); y++){
            	iArray = img.getRaster().getPixel(x, y, iArray);
            	pBrillo = (int)(Math.round((A * iArray[0]) + B));
                //pBrillo = brillo * iArray[0];
                
                if (pBrillo > 255)
                    pBrillo = 255;
                if (pBrillo < 0)
                    pBrillo = 0;
                
               iArray[0] = pBrillo;
               iArray[1] = pBrillo;
               iArray[2] = pBrillo;
               dest_image.getRaster().setPixel(x, y, iArray);
            	
            }
        }
    	
    }
    
     public void contraste (BufferedImage img){
        String StringContraste = JOptionPane.showInputDialog("Introduzca el nivel de Contraste (+ aumentar) (- disminuir)");
        contraste (img, img, Double.parseDouble(StringContraste));
                
    }
     
    public void contraste (BufferedImage img, BufferedImage dest_img, double con){
        //String StringContraste = JOptionPane.showInputDialog("Introduzca el nivel de Contraste (+ aumentar) (- disminuir)");
        double contraste = con;
        //double contraste = 2.56 * con;
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
               dest_img.getRaster().setPixel(x, y, iArray);
            	
            }
        }    
        
 
    }
        
    public void gamma(BufferedImage original) {
     
        int alpha, red, green, blue;
        int newPixel;

        String StringGamma = JOptionPane.showInputDialog("Introduzca el % de nivel de brillo");
        double gamma = Double.parseDouble(StringGamma);  
        double gamma_new = 1 / gamma;

        BufferedImage gamma_cor = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());

        for(int i=0; i<original.getWidth(); i++) {
        for(int j=0; j<original.getHeight(); j++) {
 
            // Get pixels by R, G, B
            alpha = new Color(original.getRGB(i, j)).getAlpha();
            red = new Color(original.getRGB(i, j)).getRed();
            green = new Color(original.getRGB(i, j)).getGreen();
            blue = new Color(original.getRGB(i, j)).getBlue();
 
            red = (int) (255 * (Math.pow((double) red / (double) 255, gamma_new)));
            green = (int) (255 * (Math.pow((double) green / (double) 255, gamma_new)));
            blue = (int) (255 * (Math.pow((double) blue / (double) 255, gamma_new)));
 
            // Return back to original format
            newPixel = colorToRGB(alpha, red, green, blue);
 
            // Write pixels into image
            original.setRGB(i, j, newPixel);
        }
    }
    
    //original = gamma_cor;
    
 
}   
     private static int colorToRGB(int alpha, int red, int green, int blue) {
 
        int newPixel = 0;
        newPixel += alpha;
        newPixel = newPixel << 8;
        newPixel += red; newPixel = newPixel << 8;
        newPixel += green; newPixel = newPixel << 8;
        newPixel += blue;
 
        return newPixel;
 
    }
     
     public double entropia (BufferedImage original) {
   	 double prob = 0.0;
   	 double ent = 0.0;
         int[] histograma_abs =img_get_histogramaAbs(original);
   	 double size = original.getWidth() * original.getHeight();
   	 for (int i = 0; i < histograma_abs.length; ++i) {
   		 if (histograma_abs[i] != 0) {
   			 prob = histograma_abs[i] / size;
   			 ent += (-prob * (Math.log10(prob) / Math.log10(2)));
   		 }
   	 }
   	 return ent;
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
     

 public int width (BufferedImage img){
         int tam = img.getWidth(); // * getRefBufImg().getHeight();         
         return tam;
         
         
     }
     
public int height (BufferedImage img){
   int tam = img.getHeight(); // * getRefBufImg().getHeight();         
   return tam;


}
            
     public int ranMenor (BufferedImage img){
         double rang = 255;
         double[] iArray = null;
         for (int x=0; x<img.getRaster().getWidth(); x++){
            for (int y=0; y<img.getRaster().getHeight(); y++){
                if (rang > img.getRaster().getPixel(x, y, iArray)[0]){ 
                    rang = img.getRaster().getPixel(x, y, iArray)[0];
                }
            } 
     }
         return (int)rang;
     
        
}
     
     public int ranMayor (BufferedImage img){
         double rang = 0;
         double[] iArray = null;
         for (int x=0; x<img.getRaster().getWidth(); x++){
            for (int y=0; y<img.getRaster().getHeight(); y++){
                if (rang < img.getRaster().getPixel(x, y, iArray)[0]){ 
                    rang = img.getRaster().getPixel(x, y, iArray)[0];
                }
            } 
     }
         return (int) rang;
     
        
}
     
     
     public void equalize (BufferedImage img){
         int w = img.getWidth();
         int h = img.getHeight();
         int m = w*h;
         int k = 256;
         double[] iArray = null;
         
         // invocamos al histograma acumulativo.
         
         int [] H = img_get_histogramaAbs(img);
         for (int j = 1; j < H.length; j++){
             H[j] = H[j-1] + H[j];
         }
         
         //equalize
         for (int v = 0; v < h; v++){
             for (int u = 0; u < w; u++){
                 iArray =img.getRaster().getPixel(u, v, iArray);
                 int a = (int)iArray[0];
                 int b = H[a] * (k-1)/m;
                 if (b > 255)
                    b = 255;
                  if (b < 0)
                    b = 0;
                 iArray[0] =b;
                 iArray[1] =b;
                 iArray[2] =b;
                 img.getRaster().setPixel(u, v, iArray);
             }
         }
         
         
         
     }
        
}
    
    
    //GRIS = 0.299 R + 0.587 G + 0.114 B
    

