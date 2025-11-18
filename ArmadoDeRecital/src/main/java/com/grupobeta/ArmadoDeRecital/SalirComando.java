package com.grupobeta.ArmadoDeRecital;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class SalirComando implements Comando{
		
	private Menu menu;
	private Recital recital;
	
	public SalirComando(Menu menu, Recital recital) {
		this.menu = menu;
		this.recital = recital;
	}
	
	@Override
	public void ejecutar() {
		menu.setEstaCorriendo(false);
		System.out.println("\nGuardando estado antes de salir...");
		JSONArray contenidoArchSalida = new JSONArray();
		ArrayList<Contratacion> contrataciones = recital.getContrataciones();
		
		for(Contratacion contrato : contrataciones) {
			JSONObject contratoJSON = new JSONObject();
			contratoJSON.put(CargadorDeArchivos.CLAVE_CONTRATO_CANCION, contrato.getCancion().getTitulo());
			contratoJSON.put(CargadorDeArchivos.CLAVE_CONTRATO_ARTISTA, contrato.getArtista().getNombre());
			contratoJSON.put(CargadorDeArchivos.CLAVE_CONTRATO_ROL, contrato.getRol());
			contratoJSON.put(CargadorDeArchivos.CLAVE_CONTRATO_COSTO, contrato.getCosto());
			contenidoArchSalida.put(contratoJSON);
		}
		
		File archivoSalida = new File(CargadorDeArchivos.ARCHIVO_SALIDA);
		try (FileWriter file = new FileWriter(archivoSalida)) {
	        file.write(contenidoArchSalida.toString());
	        file.flush();

	    } catch (IOException e) {
	        e.fillInStackTrace();
	    }
	}
}
