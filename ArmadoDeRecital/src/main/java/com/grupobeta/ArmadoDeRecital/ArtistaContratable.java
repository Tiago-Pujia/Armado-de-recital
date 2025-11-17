package com.grupobeta.ArmadoDeRecital;

import java.util.Set;

public class ArtistaContratable extends Artista{
	
	public static double AUMENTO_POR_ENTRENAMIENTO = 1.5;
	public static double AUMENTO_ARREGLO = 1.5; //para bajarle el precio y luego subirselo tras contratar al artista
	public static double DESCUENTO_POR_COMPARTIR_BANDA = 0.5;
	
	private int maxCanciones;
	
	public ArtistaContratable(String name, Set<String> roles, Set<String> historial, int maxCanciones, double costoContratacion) {
		super(name, roles, historial, costoContratacion);
		this.cantContratos = 0;
		this.maxCanciones = maxCanciones;
	}
		
	public void reducirCostoContratacion(double descuento) {
		this.costoContratacion = this.costoContratacion * descuento;
	}
	
	public void aumentarCostoContratacion(double aumento) {
		this.costoContratacion = this.costoContratacion * aumento;
	}

	public void entrenar(String rol) {
		
		if(rol == null) {
			throw new IllegalArgumentException("Estas intentando ingresar un valor nulo");
		}
		
		if(this.esContratable() || this.tieneRol(rol)) {
			return;
		}
		
		this.roles.add(rol);
		this.aumentarCostoContratacion(AUMENTO_POR_ENTRENAMIENTO);
	}

	public int getMaxCanciones() {
		return maxCanciones;
	}
	
	@Override
	public boolean esContratable() {
		return cantContratos < maxCanciones;
	}
}
