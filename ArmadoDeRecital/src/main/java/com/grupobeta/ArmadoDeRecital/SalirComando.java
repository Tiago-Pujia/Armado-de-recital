package com.grupobeta.ArmadoDeRecital;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;

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
		JSONArray contrataciones = new JSONArray(recital.getContrataciones());
	
		contenidoArchSalida.putAll(contrataciones);
		
		File archivoSalida = new File(CargadorDeArchivos.NOM_ARCH_SALIDA);
		try (FileWriter file = new FileWriter(archivoSalida)) {
	        file.write(contenidoArchSalida.toString());
	        file.flush();

	    } catch (IOException e) {
	        e.fillInStackTrace();
	    }
	}
}
