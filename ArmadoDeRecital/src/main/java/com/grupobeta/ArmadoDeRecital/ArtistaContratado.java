package com.grupobeta.ArmadoDeRecital;

import java.util.Set;

public class ArtistaContratado extends Artista{
	
	public static double AUMENTO_POR_ENTRENAMIENTO = 1.5;
	
	private int maxCanciones;
	
	public ArtistaContratado(String name, Set<String> roles, Set<String> historial, int maxCanciones, double costoContratacion) {
		super(name, roles, historial, costoContratacion);
		this.cantContratos = 0;
		this.maxCanciones = maxCanciones;
	}
		
	public void reducirCostoContratacion() {
		this.costoContratacion = this.costoContratacion * DESCUENTO_POR_COMPARTIR_BANDA;
	}
	
	public void aumentarCostoContratacion() {
		this.costoContratacion = this.costoContratacion * AUMENTO_POR_ENTRENAMIENTO;
	}

	public void entrenar(String rol) {
		this.roles.add(rol);
		this.aumentarCostoContratacion();
	}

	public int getMaxCanciones() {
		return maxCanciones;
	}
	
	@Override
	public boolean esContratable() {
		return cantContratos > maxCanciones;
	}
}
