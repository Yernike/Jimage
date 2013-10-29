/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jimage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.image.BufferedImage;
import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;



/**
 *
 * @author yeray
 */
public class JInternalFrame1 extends javax.swing.JInternalFrame {

    JLabel JLabel_imagen;
    BufferedImage imagen;
    /**
     * Creates new form JInternalFrame1
     */
public JInternalFrame1() {
        initComponents();
                   
    }

public JInternalFrame1(BufferedImage img) {
        initComponents();
        imagen= img;   
}

public void Ji_abrir_imagen (BufferedImage img){
    String title = "ImagenSinTitulo";
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
