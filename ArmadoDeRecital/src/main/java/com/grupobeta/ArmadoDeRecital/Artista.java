package com.grupobeta.ArmadoDeRecital;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class Artista implements Comparable<Artista> {
	
	public static double AUMENTO_POR_ENTRENAMIENTO = 1.5;
	public static double DESCUENTO_POR_COMPARTIR_BANDA = 0.5;
	
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
		this.aumentarCostoContratacion();
	}
	
	public boolean esContratable() {
		return cantidadContratos < maxCanciones;
	}
	
	public void contratar() {
		this.cantidadContratos++;
	}
	
	public boolean compartioBandaCon(Artista otro) {
		HashSet<String> interseccion = new HashSet<String>(bandasHistoricas);
		interseccion.retainAll(otro.bandasHistoricas);
		return !interseccion.isEmpty();
	}
	
	public void reducirCostoContratacion() {
		this.costoContratacion = this.costoContratacion * DESCUENTO_POR_COMPARTIR_BANDA;
	}
	public void aumentarCostoContratacion() {
		this.costoContratacion = this.costoContratacion * AUMENTO_POR_ENTRENAMIENTO;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public double getCosto() {
		return this.costoContratacion;
	}
	
	public HashSet<String> getRoles() {
		return roles;
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
	
	@Override
	public String toString() {
		return "Artista [nombre=" + nombre + ", roles=" + roles + ", costoContratacion=" + costoContratacion
				+ ", maxCanciones=" + maxCanciones + ", cantidadContratos=" + cantidadContratos + ", bandasHistoricas="
				+ bandasHistoricas + "]";
	}

	@Override
	public int compareTo(Artista o) {
		return this.nombre.compareTo(o.getNombre());
	}
}
