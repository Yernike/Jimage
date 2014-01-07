package jimage;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class especificacionH {

	public especificacionH () {
		
	}
	
	public BufferedImage especificacionH (BufferedImage imagen, int [] histograma1, int [] histograma2) {
		BufferedImage imagenResult = new BufferedImage(imagen.getWidth(), imagen.getHeight(), imagen.getType());
		HashMap<Integer, Integer> tabla = tablaTransformacion(histograma1, histograma2);
		int temp = 0;
		for (int i = 0; i < imagenResult.getWidth(); ++i){
			for (int j = 0; j < imagenResult.getHeight(); ++j){
				temp = tabla.get(new Color (imagen.getRGB(i, j)).getRed());
				imagenResult.setRGB(i, j, new Color(temp, temp, temp).getRGB());
			}
		}
		return imagenResult;
	}
	
	public HashMap<Integer, Integer> tablaTransformacion (int [] histograma1, int[] histograma2) {
		HashMap<Integer, Integer> tabla = new HashMap<Integer, Integer>();
		double [] histograma1N = normalizar(histograma1);
		double [] histograma2N = normalizar(histograma2);
		
		for (int i = 0; i < histograma1N.length; ++i) {
			tabla.put(i, buscarMasCercano(histograma1N[i], histograma2N));
		}
		return tabla;
	}
	
	public int buscarMasCercano (double frecuenciaBuscada, double [] h2) {
		double maximo = 99999.0;
		double minimo = 99999.0;
		int mejorSolucionSuperior = 0;
		int mejorSolucionInferior = 0;
		
		for (int i = 0; i < h2.length; ++i) {
			double diferenciaSuperior = h2[i] - frecuenciaBuscada;
			if (diferenciaSuperior >= 0){
				if (maximo > diferenciaSuperior){
					maximo = diferenciaSuperior;
					mejorSolucionSuperior = i;
				}
			}
			
			double diferenciaInferior = frecuenciaBuscada - h2[i];
			if (diferenciaInferior > 0){
				if (minimo > diferenciaInferior) {
					minimo = diferenciaInferior;
					mejorSolucionInferior = i;
				}
			}
			
		}
		if (minimo < maximo)
			return mejorSolucionInferior;
		else
			return mejorSolucionSuperior;
	}
	public double [] normalizar (int [] valores) {
		double pixelesTotales = (double)valores[valores.length -1];
		double [] nuevo = new double [valores.length];
		for (int i = 0; i < valores.length; ++i) {
			nuevo[i]= ((double)valores[i]) /pixelesTotales;
		}
		return nuevo;
	}

	public void mostrarValoresNormalizados (double [] normalizados) {
		for (int i = 0; i < normalizados.length; ++i){
			System.out.println(normalizados[i]);
		}
	}
}
