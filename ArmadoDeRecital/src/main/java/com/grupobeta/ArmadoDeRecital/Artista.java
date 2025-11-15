package com.grupobeta.ArmadoDeRecital;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class Artista implements Comparable<Artista> {
	
	public static double DESCUENTO_POR_COMPARTIR_BANDA = 0.5;
	
	protected String nombre;	
	protected HashSet<String> roles = null;
	protected HashSet<String> bandasHistoricas = null;
	protected int cantContratos = 0;
	protected double costoContratacion;
	
	public Artista(String name, Set<String>roles, Set<String>historial, double costo) {
		this.nombre = name;
		this.roles = new HashSet<String>(roles);
		this.bandasHistoricas = new HashSet<String>(historial);		
	}
				
	public boolean compartioBandaCon(Artista otro) {
		HashSet<String> interseccion = new HashSet<String>(bandasHistoricas);
		interseccion.retainAll(otro.bandasHistoricas);
		return !interseccion.isEmpty();
	}
	
	public void contratar() {
		this.cantContratos++;
	}
	
	public boolean tieneRol(String rol) {
		return this.roles.contains(rol);
	}
	
	public abstract boolean esContratable();
	
	public String getNombre() {
		return nombre;
	}
	
	public double getCosto() {
		return this.costoContratacion;
	}
	
	public HashSet<String> getRoles() {
		return roles;
	}
	
	public int getCantContratos() {
		return this.cantContratos;
	}
	
	public HashSet<String> getHistorial() {
		return this.bandasHistoricas;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(bandasHistoricas, nombre, roles);
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
		return Objects.equals(bandasHistoricas, other.bandasHistoricas)
				&& Objects.equals(nombre, other.nombre)
				&& Objects.equals(roles, other.roles);
	}
	
	@Override
	public String toString() {
		return "Artista [nombre=" + nombre + ", roles=" + roles + " bandasHistoricas=" + bandasHistoricas + "]";
	}

	@Override
	public int compareTo(Artista o) {
		return this.nombre.compareTo(o.getNombre());
	}
}
