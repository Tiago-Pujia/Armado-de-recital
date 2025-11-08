package com.grupobeta.ArmadoDeRecital;

import java.util.ArrayList;
import java.util.HashMap;

public class Recital {
	
	ArrayList<Cancion> canciones = null; 
	ArrayList<Contratacion> contrataciones = null;
	ArrayList<Artista> repertorio = null;
	ArrayList<String> artistasBase = null;
	
	public Recital(ArrayList<Cancion> c, ArrayList<Artista> r, ArrayList<String> ab) {
		this.canciones = c;
		this.repertorio = r;
		this.artistasBase = ab;
	}
	
	///punto 1
	public HashMap<String, Integer> consultarRolesFaltantesParaCancion(String nombre) {
	
		Cancion cancion = this.buscarCancion(nombre);
		
		if(cancion == null){
			return null;
		}
		
		return cancion.getRolesRequeridos();
				
		
		
		///rescatado de la version anterior de Contratacion (donde tenia un hashmap de artista-rol)
//		public ArrayList<String> getRolesFaltantes() {
//			
//			ArrayList<String> rolesCubiertos = new ArrayList<String>();
//			
//			for(String rol : this.contratos.values()) {
//				rolesCubiertos.add(rol);
//			}
//			
//			ArrayList<String> diferencia = new ArrayList<String>(cancion.getRolesRequeridos());
//			diferencia.removeAll(rolesCubiertos);
//			return diferencia;
//		}
	}
	
	///punto 2
	public void obtenerRolesFaltantesAll() {
		
	}
	
	///punto 3
	public void contratarArtistaParaCancion() {
		
		
		///se elimina de la lista de roles requeridos del array de canciones el rol especifico con el que se contrató a un artista
	}
	
	///punto 4
	public void contratarArtistasAll() {
		
	}
	
	///punto 5
	public void entrenarArtista() {
		
	}
	
	///punto 6
	public void listarArtistasContratados() {
		
	}
	
	///punto 7
	public ArrayList<Cancion> getCanciones() {
		return this.canciones;		
	}
	
	public double obtenerCostoContratacionesCancion(String nombreCancion) {
		
		double costoCancion = 0;;
		
		for(Contratacion con : contrataciones) {
			if(con.cancion.getTitulo().toLowerCase().equals(nombreCancion.toLowerCase())) {
				costoCancion += con.getCosto();
			}
		}
		
		return costoCancion;
	}
	
	public double obtenerCostoTotal() {
		
		double costoTotal = 0;
		
		for(Contratacion con : contrataciones) {
			costoTotal += con.getCosto();
		}
		
		return costoTotal;
	}

	public Cancion buscarCancion(String ncanc) {
		for(Cancion c : this.canciones) {
			if(c.getTitulo().toLowerCase().equals(ncanc.toLowerCase())) {
				return c;
			}
		}
		return null;
	}
}
