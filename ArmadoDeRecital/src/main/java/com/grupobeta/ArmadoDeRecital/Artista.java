package com.grupobeta.ArmadoDeRecital;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class Artista {
	
	private String nombre;
	private HashSet<String> roles = null;
	private double costoContratacion;
	private int maxCanciones;
	private int cantidadContratos;
	private HashSet<String> bandasHistoricas = null;
	
	public Artista(String name, List<String>roles, double costCont, int maxCanc, List<String>historial) {
		this.nombre = name;
		this.roles = new HashSet<String>(roles);
		this.costoContratacion = costCont;
		this.maxCanciones = maxCanc;
		this.cantidadContratos = 0;
		this.bandasHistoricas = new HashSet<String>(historial);		
	}
	
	public void entrenar(String rol) {
		this.roles.add(rol);
		this.costoContratacion *= 1.5;
	}
	
	public double getCosto() {
		return this.costoContratacion;
	}
	
	public boolean compartioBandaCon(Artista otro) {
		HashSet<String> interseccion = new HashSet<String>(bandasHistoricas);
		interseccion.retainAll(otro.bandasHistoricas);
		return !interseccion.isEmpty();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(bandasHistoricas, cantidadContratos, costoContratacion, maxCanciones, nombre, roles);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Artista other = (Artista) obj;
		return Objects.equals(bandasHistoricas, other.bandasHistoricas) && cantidadContratos == other.cantidadContratos
				&& Double.doubleToLongBits(costoContratacion) == Double.doubleToLongBits(other.costoContratacion)
				&& maxCanciones == other.maxCanciones && Objects.equals(nombre, other.nombre)
				&& Objects.equals(roles, other.roles);
	}
	
	
}
