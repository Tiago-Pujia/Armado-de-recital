package com.grupobeta.ArmadoDeRecital;

import java.util.Objects;

public class Contratacion {
	
	Cancion cancion;
	Artista artista; /// 1 contrato contempla 1 artista con 1 cancion en 1 rol
	String rol;
	private double costo = 0;
	
	private Contratacion(Cancion c, Artista a, String r) {
		this.cancion = c;
		this.artista = a;
		this.rol = r;
		this.costo = a.getCosto();
	}
	
	public static Contratacion contratarArtistaRol(Cancion cancion, Artista artista, String rol) throws Exception {
		
		if(!artista.getRoles().contains(rol)) {
			throw new Exception("El artista no cuenta con el rol que estás intentando asignarle");
		}
		return new Contratacion(cancion, artista, rol);
	}
	
	public double getCosto() {
		return this.costo;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(artista, cancion, rol);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contratacion other = (Contratacion) obj;
		return Objects.equals(artista, other.artista) && Objects.equals(cancion, other.cancion)
				&& Objects.equals(rol, other.rol);
	}
}
