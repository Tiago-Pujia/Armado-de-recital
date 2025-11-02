package com.grupobeta.ArmadoDeRecital;

import java.util.ArrayList;
import java.util.Objects;

public class Cancion {
	
	private String titulo;
	private ArrayList<String> rolesRequeridos = null;
	
	public Cancion(String nombre, ArrayList<String>rr) {
		this.titulo = nombre;
		this.rolesRequeridos = rr;
	}
	
	public ArrayList<String> getRolesRequeridos() {
		return this.rolesRequeridos;
	}
	public String getTitulo() {
		return this.titulo;
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
}
