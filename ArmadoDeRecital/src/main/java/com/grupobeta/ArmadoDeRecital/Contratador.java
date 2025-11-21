package com.grupobeta.ArmadoDeRecital;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;

public class Contratador {
	
	public Contratacion contratarArtistaParaUnRolCualquieraEnCancion(Artista artista, Cancion cancion, boolean hayDescuento) {
		
		return this.crearContrato(artista, cancion, this.rolesQuePuedeTomarArtistaParaCancion(artista, cancion).iterator().next(), hayDescuento); 
	}
	
	public Contratacion contratarArtistaParaUnRolEnCancion(Artista artista, Cancion cancion, String rol, boolean hayDescuento) {
		
		return this.crearContrato(artista, cancion, rol, hayDescuento); 
	}
	
	public static Contratacion crearContratoDesdeDatosArchivo(Artista artista, Cancion cancion, String rol, double costo) {
		artista.contratar();
		cancion.removerUnRol(rol);	
		return Contratacion.contratarArtistaRolDirecto(cancion, artista, rol, costo); 
	}
	
	private Contratacion crearContrato(Artista artista, Cancion cancion, String rol, boolean hayDescuento) {
		Contratacion contrato = Contratacion.contratarArtistaRol(cancion, artista, rol, hayDescuento);
		artista.contratar();
		cancion.removerUnRol(rol);		
		return contrato;
	}
	
	public boolean artistaEsContratableParaCancion(ArrayList<Contratacion> contrataciones, Artista artista, Cancion cancion) {
			
		if(yaHayContratoParaEseArtistaYCancion(contrataciones, artista, cancion)) {
			return false;
		}
		
		if(!artistaTieneAlgunRolCancion(artista, cancion)) {
			return false;
		}
			
		return artista.esContratable();
	}
	
	public boolean artistaEsContratableParaCancionRol(List<Contratacion> contrataciones, Artista artista, Cancion cancion, String rol) {
		
		if(yaHayContratoParaEseArtistaYCancion(contrataciones, artista, cancion)) {
			return false;
		}
		
		if(!artista.tieneRol(rol)) {
			return false;
		}
		
		return artista.esContratable();
	}
	
	public boolean yaHayContratoParaEseArtistaYCancion(List<Contratacion> contrataciones, Artista artista, Cancion cancion) {
		
		for(Contratacion contratacion : contrataciones) {
			if(contratacion.getArtista().equals(artista) && contratacion.getCancion().equals(cancion)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean artistaTieneAlgunRolCancion(Artista artista, Cancion cancion) {
		
		HashSet<String> interseccion = new HashSet<String>(artista.getRoles());
		interseccion.retainAll(cancion.getRolesRequeridos().keySet());
		return !interseccion.isEmpty();
	}
	
	public HashSet<String> rolesQuePuedeTomarArtistaParaCancion(Artista artista, Cancion cancion) {
			
		HashSet<String> interseccion = new HashSet<String>(artista.getRoles());
		interseccion.retainAll(cancion.getRolesRequeridos().keySet());
		return interseccion;
	}

	public ArrayList<Artista> cancionTieneContratadoArtistasBase(Cancion cancion, List<ArtistaBase> artistasBase, List<Contratacion> contrataciones) {
		
		ArrayList<Artista> artistasBaseEnEsaCancion = new ArrayList<Artista>();
		
		for(Contratacion contrato : contrataciones) {
			if(contrato.getCancion().equals(cancion) && artistasBase.contains(contrato.getArtista())) {
				artistasBaseEnEsaCancion.add(contrato.getArtista());
			}
		}		
		return artistasBaseEnEsaCancion;
	}	
	
	public boolean evaluarDescuento(Artista artista, Cancion cancion, List<ArtistaBase> artistasBase, List<Contratacion> contrataciones ) {
		
		ArrayList<Artista> artistasBaseEnEsaCancion = cancionTieneContratadoArtistasBase(cancion, artistasBase, contrataciones);
		
		if(artistasBaseEnEsaCancion.isEmpty()) {
			return false;
		}
		
		for(Artista baseEnCancion : artistasBaseEnEsaCancion) {
			if(artista.compartioBandaCon(baseEnCancion)) {
				return true;
			}
		}
		return false;
	}
	
}