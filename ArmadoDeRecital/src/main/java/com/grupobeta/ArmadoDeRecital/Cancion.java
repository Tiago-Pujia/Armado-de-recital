package com.grupobeta.ArmadoDeRecital;

import java.util.HashMap;
import java.util.Objects;

public class Cancion {
	
	private String titulo;
	private HashMap<String, Integer> rolesRequeridos = null;
	
	public Cancion(String nombre, HashMap<String, Integer>rr) {
		this.titulo = nombre;
		this.rolesRequeridos = rr;
	}
	
	public HashMap<String, Integer> getRolesRequeridos() {
		return this.rolesRequeridos;
	}
	
	public void removerUnRol(String rol) {
		if(this.rolesRequeridos.containsKey(rol)) {
			this.rolesRequeridos.put(rol, this.rolesRequeridos.getOrDefault(rol, 0) -1);
			if(this.rolesRequeridos.get(rol)==0) {
				this.rolesRequeridos.remove(rol);
			}
		}
	}
	
	public String getTitulo() {
		return this.titulo;
	}
	
	public void setRolesRequeridos(HashMap<String, Integer> roles) {
		this.rolesRequeridos = roles;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(rolesRequeridos, titulo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cancion other = (Cancion) obj;
		return Objects.equals(rolesRequeridos, other.rolesRequeridos) && Objects.equals(titulo, other.titulo);
	}

	@Override
	public String toString() {
		return "Cancion [titulo=" + titulo + ", rolesRequeridos=" + rolesRequeridos + "]";
	}
}
