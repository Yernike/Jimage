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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.*;

import java.awt.geom.Rectangle2D;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.JOptionPane;



/**
 *
 * @author yeray
 */
public class JInternalFrame1 extends javax.swing.JInternalFrame {

    JLabel JLabel_imagen;
    String title = "";
    BufferedImage imagen;
    BufferedImage Imagmemoria;
    BufferedImage imgrecortada;

    Graphics2D g2D;
    int x=0;
    int y=0;
    int ancho=0;
    int alto=0;
    
    /**
     * Creates new form JInternalFrame1
     */
    
  public Point puntero_mouse(){
       
    Point p = MouseInfo.getPointerInfo().getLocation();
    return p;
                         
    
 }
   public void addEventos(final JLabel label){
  addMouseListener (new MouseListener() {
   
   @Override
   public void mouseReleased(MouseEvent arg0) {
       
        ancho = (JLabel_imagen.getMousePosition().x)-x;
        alto = (JLabel_imagen.getMousePosition().y)-y;
        if(ancho<0) ancho=0;
        if(alto<0) alto=0;
        if(x > imagen.getWidth()) x = imagen.getWidth() - ancho ;
        if(y > imagen.getHeight()) y = imagen.getHeight() - alto ;        
        recortar();
   }
   
   @Override
   public void mousePressed(MouseEvent arg0) {
        x= JLabel_imagen.getMousePosition().x;
        y= JLabel_imagen.getMousePosition().y;
 
   }
   
   @Override
   public void mouseExited(MouseEvent arg0) {
    
   }
   
   @Override
   public void mouseEntered(MouseEvent arg0) {
    
   }
   
   @Override
   public void mouseClicked(MouseEvent arg0) {
    
   }
});

  
        addMouseMotionListener(new MouseMotionAdapter(){
            
            @Override
            public void mouseMoved(MouseEvent evento){
                try { 
                double pArray[] = null;
                pArray = imagen.getRaster().getPixel(JLabel_imagen.getMousePosition().x,
                        JLabel_imagen.getMousePosition().y, pArray);
                
              label.setText(String.format("XY [%d, %d] RGB [%d, %d, %d]",
                     JLabel_imagen.getMousePosition().x,
                     JLabel_imagen.getMousePosition().y, 
                     (int)pArray[0],
                     (int)pArray[1],
                     (int)pArray[2]
                    
                       ));           
      
        }catch (NullPointerException a){
  } 
                 }
            
           
             
        });
                
       
 }
  
public JInternalFrame1() {
        initComponents();
         
                
    }

public JInternalFrame1(BufferedImage img) {
        initComponents();
        imagen= img;
        this.imagen = img;
        //this.setSize(img.getWidth(),img.getHeight());
        //this.setVisible(true);
        //addMouseMotionListener();
        //addMouseListener();
}

private void recortar (){
    imagen = imagen.getSubimage(x, y, ancho, alto);
    JLabel_set_imagen();
    this.setSize(imagen.getHeight(), imagen.getWidth());
}
public void Ji_abrir_imagen (BufferedImage img){
    title = "ImagenSinTitulo";
    JLabel_imagen= new JLabel();
    this.add(JLabel_imagen);
    
    
    
    if (img == null){
        open_save_image a = new open_save_image();
        imagen= a.abrir_imagen();
        title = a.path.substring(a.path.lastIndexOf("/")+1);
    }
    else imagen = img;
    
    if (imagen != null) 
    {
        setJInternalFrame(title, imagen.getWidth(),imagen.getHeight());
        //Cargamos el JLabel con la imagen como ImageIcon
        
        JLabel_set_imagen();
    }

    

}
    public void setJInternalFrame(String title, int width, int height){

        this.setMaximizable(true);
        this.setClosable(true);
        this.setIconifiable(true);

        this.setTitle(title);
        this.setVisible(true);
        this.setSize(width, height);  
             
}
        
    public void JLabel_set_imagen(){
        
        ImageIcon icono =new ImageIcon(imagen);
        JLabel_imagen.setIcon(icono);
        this.JLabel_imagen.setBounds(0, 0, imagen.getWidth(), imagen.getHeight());
    }
    
    public void Ji_to_bw(){
        pixel_imagen tmp = new pixel_imagen();
        tmp.img_to_BW(imagen);
        JLabel_set_imagen();   
    }
    
    public void Ji_to_eq(){
        pixel_imagen tmp = new pixel_imagen();
        tmp.equalize(imagen);
        JLabel_set_imagen();   
    }

    
    public void Ji_brillo(){
        pixel_imagen tmp = new pixel_imagen();
        tmp.brillo(imagen);
        JLabel_set_imagen();   
    }
    
    public void Ji_contraste(){
        pixel_imagen tmp = new pixel_imagen();
        tmp.contraste(imagen);
        JLabel_set_imagen();   
    }
    
    public void Ji_gamma(){
        pixel_imagen tmp = new pixel_imagen();
        tmp.gamma(imagen);
        JLabel_set_imagen();   
    }
    

    public void Ji_histograma_abs(){
        pixel_imagen tmp = new pixel_imagen();
        int[] hist = tmp.img_get_histogramaAbs(imagen);
        Ji_draw_histograma(hist, "Histograma Absoluto");
        
    }
    
      public void Ji_histograma_acu(){
        pixel_imagen tmp = new pixel_imagen();
        int[] hist = tmp.img_get_histogramaAcu(imagen);
        Ji_draw_histograma(hist, "Histograma Acumulativo");
        
    }
    public void Ji_draw_histograma(int[] hist, String titulo){
    
        ChartPanel panel;
        JFreeChart chart;
        XYSplineRenderer renderer = new XYSplineRenderer();;
        XYSeriesCollection dataset = new XYSeriesCollection();
        
        ValueAxis x = new NumberAxis();
        ValueAxis y = new NumberAxis();
        
        XYSeries serie = new XYSeries("Datos");
        XYPlot plot;
        
        
        
        for(int fila=0;fila<255;fila++){
            serie.add(fila, hist[fila]);   
        }
        
        dataset.addSeries(serie);
        //x.setLabel("Color");
        //y.setLabel("Veces");
        chart = ChartFactory.createHistogram(titulo, title, title, dataset, PlotOrientation.VERTICAL, isClosed, closable, isClosed);
             
        plot = new XYPlot(dataset,x,y,renderer);
        plot.isDomainCrosshairVisible();
        plot.isRangeCrosshairVisible();
        plot.isRangeGridlinesVisible();
        plot.isDomainZoomable();
        plot.isRangeZoomable();
        
        //chart = new JFreeChart(plot);
        //chart.setTitle("Histograma Absoluto");
        
        panel = new ChartPanel(chart);
        panel.setBounds(5,10,400,400);
        
        setJInternalFrame("Histograma", 450,450);
        this.add(panel);
        this.repaint();
        
    }
    
    
/*
    @Override
    protected void paintComponent(Graphics g) {
      Graphics2D g2 = (Graphics2D)g;
      if(imagen!=null){
        Imagmemoria = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        g2D = Imagmemoria.createGraphics();
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.drawImage(imagen,0, 0, imagen.getWidth(this), imagen.getHeight(this), this);
        g2D.setStroke(new BasicStroke(2f));
        g2D.setColor(Color.WHITE);
        Rectangle2D r2 = new Rectangle2D.Float( x, y, ancho, alto );
        g2D.draw(r2);
        g2.drawImage(Imagmemoria, 0, 0, this);
      }
    }
    public void guardar_imagen(String nombrearchivo){
     imgrecortada = ((BufferedImage) imagen).getSubimage((int)x,(int) y,(int) ancho,(int) alto) ;
        try {          
            ImageIO.write(imgrecortada, "jpg", new File(nombrearchivo));
            JOptionPane.showMessageDialog(null, "Se ha guardado Correctamente la imagen recortada");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error, Trate nuevamente");
        }
   }
    public void mouseDragged(MouseEvent e) {
        ancho = e.getX()-x;
        alto = e.getY()-y;
        if(ancho<0) ancho=0;
        if(alto<0) alto=0;
        if(x > imagen.getWidth()) x = this.getWidth() - ancho ;
        if(y > imagen.getHeight()) y = this.getHeight() - alto ;        
        this.repaint();
    }
    
    public void mousePressed(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }*/
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 394, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 274, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}