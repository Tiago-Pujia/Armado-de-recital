package com.grupobeta.ArmadoDeRecital;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Contratacion {
	
	Cancion cancion;
	HashMap<Artista, String> contratos; /// clave-valor: artista-rol
	
	public Contratacion(Cancion c) {
		this.cancion = c;
		contratos = new HashMap<Artista, String>();
	}
	
	//completar ¿?: que se fije que el artista tenga el rol ¿?
	public void contratarArtistaRol(Artista art, String rol) {
		this.contratos.put(art, rol);
	}
	
	public double getCostoTotal() {
		
		double total = 0;
		
		for(Artista art : contratos.keySet()) {
			total += art.getCosto();
		}
		
		return total;
	}
	
	public ArrayList<String> getRolesFaltantes() {
		
		ArrayList<String> rolesCubiertos = new ArrayList<String>();
		
		for(String rol : this.contratos.values()) {
			rolesCubiertos.add(rol);
		}
		
		ArrayList<String> diferencia = new ArrayList<String>(cancion.getRolesRequeridos());
		diferencia.removeAll(rolesCubiertos);
		return diferencia;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(cancion, contratos);
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
		return Objects.equals(cancion, other.cancion) && Objects.equals(contratos, other.contratos);
	}
}
