package com.grupobeta.ArmadoDeRecital;

import java.util.ArrayList;
import java.util.HashMap;

public class Recital {
	
	ArrayList<Cancion> canciones = null; 
	ArrayList<Contratacion> contrataciones = null;
	ArrayList<Artista> repertorio = null;
	ArrayList<String> artistasBase = null;
	Contratador contratador = null;
	
	public Recital(ArrayList<Cancion> c, ArrayList<Artista> r, ArrayList<String> ab) throws Exception {
		this.canciones = c;
		this.repertorio = r;
		this.artistasBase = ab;
		this.contrataciones = new ArrayList<Contratacion>();
		this.contratador = new Contratador();
		this.agregarArtistasBase();
	}
	
	///agregar artistas base
	/// recorro la lista de canciones y obtengo su lista de roles
		///recorro la lista de roles faltantes y busco en el repertorio un artista base (me fijo que el nombre del artista esté en la lista de artistas base)
			///si lo encuentro, me fijo si en la lista de contrataciones ya está contratado para esa misma cancion
				///si lo está, paso al siguiente artista.
				//si no lo está, me fijo si tiene el rol
					//lo tiene? lo contrato
						//creo un contrato para ese artista y ese rol, le resto 1 a la cantidad de ese rol en el hashmap de roles de la cancion y agrego el contrato a la lista de contratos
							//si el hashmap tiene 0 para ese rol, elimino esa entrada del map
						//sigo con otro rol 
					//no lo tiene? sigo con otro artista
	
	private void agregarArtistasBase() throws Exception { //contratar all es basicamente esto pero contratando a mansalva a todos sin chequear que sea base o no, simplemente contrata
		
		//se me ocurre remover temporalmente al artista ya contratado del repertorio para que no pregunte 
		//por él varias veces para una misma canción. Después lo agregamos otra vez al finalizar
		ArrayList<Artista> artistasContratadosParaCancionTemp = new ArrayList<Artista>(); 
		
		for(Cancion cancion : canciones) { 
			
			for(int i = 0; i < repertorio.size() ; i++) {
				
				Artista artista = repertorio.get(i);
				if(artistasBase.contains(artista.getNombre())  && contratador.artistaEsContratableParaCancion(this.contrataciones, artista, cancion)) {
					this.contrataciones.add(this.contratador.contratarArtistaParaUnRolEnCancion(artista, cancion));
					artistasContratadosParaCancionTemp.add(artista);
					this.repertorio.remove(artista);
				}
							
			}
			
		}
		this.repertorio.addAll(artistasContratadosParaCancionTemp); 
	}
	
	///punto 1
	public HashMap<String, Integer> consultarRolesFaltantesParaCancion(String nombre) {
				
		Cancion cancion = this.buscarCancion(nombre);
		
		if(cancion == null){
			return null;
		}
		
		return cancion.getRolesRequeridos();
	}
	
	///punto 2
	public ArrayList<Cancion> getCanciones() {
		return this.canciones;		
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
	public ArrayList<Contratacion> listarArtistasContratados() {
		this.contrataciones.sort(null);
		return this.contrataciones;
	}
	
	public double obtenerCostoContratacionesCancion(String nombreCancion) {
		
		double costoCancion = 0;;
		
		for(Contratacion con : contrataciones) {
			if(con.getCancion().getTitulo().toLowerCase().equals(nombreCancion.toLowerCase())) {
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
