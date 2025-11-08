package com.grupobeta.ArmadoDeRecital;

import java.util.ArrayList;
import java.util.HashSet;

public class Contratador {
	
	//extraemos el primer rol que tengan en comun artista.roles y cancion.roles
	public Contratacion contratarArtistaParaUnRolEnCancion(Artista artista, Cancion cancion) throws Exception {
		
		return this.crearContrato(artista, cancion, this.rolesQuePuedeTomarArtistaParaCancion(artista, cancion).iterator().next()); 
	}
	
	/// crea el contrato y lo devuelve, no sin antes quitarle a la cancion 1 unidad de ese rol de sus roles faltantes por cubrir
	public Contratacion crearContrato(Artista artista, Cancion cancion, String rol) throws Exception {
		
		//aplicarDescuentos(artista,contrataciones,cancion); //chequea que haya algún artista base contratado y le 
		
		Contratacion contrato = Contratacion.contratarArtistaRol(cancion, artista, rol);
		artista.contratar();
		cancion.removerUnRol(rol);		
		return contrato;
	}
	
	
	public boolean artistaEsContratableParaCancion(ArrayList<Contratacion> contrataciones, Artista artista, Cancion cancion) {
			
			///chequeo que el artista no esté ya contratado para esa cancion
			if(yaHayContratoParaEseArtistaYCancion(contrataciones, artista, cancion)) {
				return false;
			}
			
			///chequeo que el artista tenga algún rol para la cancion
			if(!artistaTieneAlgunRolCancion(artista, cancion)) {
				return false;
			}
			
			///chequeo que el artista tenga contratos libres
			return artista.esContratable();
	}
	
	public boolean yaHayContratoParaEseArtistaYCancion(ArrayList<Contratacion> contrataciones, Artista artista, Cancion cancion) {
		
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


	
}
