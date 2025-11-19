package com.grupobeta.ArmadoDeRecital;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Recital {
	
	private List<Cancion> canciones = null; 
	private List<Contratacion> contrataciones = null;
	private List<ArtistaContratable> artistasContratables = null;
	private List<ArtistaBase> artistasBase = null;
	private Contratador contratador = null;
	private double costoTotal = 0;
	
	public Recital(CargadorDeArchivos cargador) {
		this.canciones = cargador.cargarArchivoRecital();
		this.artistasContratables = cargador.cargarArchivoArtistas();
		this.artistasBase = cargador.cargarArchivoArtistasBase(artistasContratables);
		this.contrataciones = new ArrayList<Contratacion>();
		this.contratador = new Contratador();
		//this.agregarArtistasBase(); //lo quito temporal o indefinidamente, creo que es mejor NO agregarlos al principio para no tener que des-contratarlos para cargar un estado previo (agarramos el recital vacío, so to speak)
	}

	public Contratador getContratador() {
		return this.contratador;
	}
	
	///punto 1
	public Map<String, Integer> consultarRolesFaltantesParaCancion(Cancion cancion) {		
		return cancion.getRolesRequeridos();
	}
	
	//punto 3
	public List<Contratacion> contratarArtistasParaCancion(Cancion cancion) {
		
		boolean rolVacio = true, primerContratable = true;
		HashMap<String, Integer> copia = new HashMap<String, Integer>(cancion.getRolesRequeridos());
		ArrayList<Contratacion> contratosRealizados = new ArrayList<Contratacion>();
		
		ArrayList<Artista> repertorio = new ArrayList<Artista>(this.getArtistasBase());
		repertorio.addAll(artistasContratables);
		
		if(copia.isEmpty()) {
			return contratosRealizados;
		}
		
		for(Map.Entry<String, Integer> rol : copia.entrySet()) {
			
			while(rol.getValue() != 0) {
				primerContratable = true;
				rolVacio = true;
				Artista artistaMin = null;
				for(Artista artista : repertorio) {
					
					if(contratador.artistaEsContratableParaCancionRol(contrataciones, artista, cancion, rol.getKey())) {
						
						if(primerContratable) {
							artistaMin = artista;
							primerContratable = false;
							rolVacio = false;
						}
						
						if(artista.getCosto() < artistaMin.getCosto()) {
							artistaMin = artista;
							rolVacio = false;
						}
					}
				}
				if(!rolVacio) {
					Contratacion contratacion = contratador.contratarArtistaParaUnRolEnCancion(artistaMin, cancion, rol.getKey(), contratador.evaluarDescuento(artistaMin, cancion, artistasBase, contrataciones));
					this.contrataciones.add(contratacion);
					this.aumentarCostoTotal(contratacion.getCosto());
					contratosRealizados.add(contratacion);
					rol.setValue(rol.getValue()-1);
				}
				else {
					break;
				}
			}
		}
		
		return contratosRealizados;
	}
	
	///punto 6
	public List<Contratacion> getContrataciones() {
		this.contrataciones.sort(null);
		return this.contrataciones;
	}
	
	public double obtenerCostoContratacionesYContratosCancion(Cancion cancion, List<Contratacion> contrato) {
		
		double costoCancion = 0;
		
		for(Contratacion con : contrataciones) {
			if(con.getCancion().getTitulo().toLowerCase().equals(cancion.getTitulo().toLowerCase())) {
				costoCancion += con.getCosto();
				contrato.add(con);
			}
		}
		
		return costoCancion;
	}
	
	public void aumentarCostoTotal(double monto){
		this.costoTotal += monto;
	}

	public Cancion buscarCancion(String ncanc) {
		
		for(Cancion c : this.canciones) {
			if(c.getTitulo().toLowerCase().equals(ncanc.toLowerCase())) {
				return c;
			}
		}
		throw new CancionNoEncontradaException("Cancion no encontrada");
	}

	public ArtistaContratable buscarArtistaContratable(String nombre) {
		
		for(ArtistaContratable a : this.artistasContratables) {
			if(a.getNombre().toLowerCase().equals(nombre.toLowerCase())) {
				return a;
			}
		}
		throw new ArtistaNoEncontradoException("El nombre ingresado no coincide con el de ningún artista. Intente nuevamente");
	}
	
	public List<Contratacion> getContratosDeArtista(Artista artista) {
		
		ArrayList<Contratacion>contratosArtista = new ArrayList<Contratacion>();
		
		for(Contratacion c : this.contrataciones) {
			if(c.getArtista().equals(artista)) {
				contratosArtista.add(c);
			}
		}
		return contratosArtista;
	}
	
public List<Contratacion> getContratosDeCancion(Cancion cancion) {
	
		ArrayList<Contratacion>contratosCancion = new ArrayList<Contratacion>();
		
		for(Contratacion c : this.contrataciones) {

			if(c.getCancion().equals(cancion)) {
				contratosCancion.add(c);
			}
		}
		return contratosCancion;
	}
	
	public void removerContratacion(Contratacion contrato){
		this.contrataciones.remove(contrato);
		contrato.getCancion().agregarRol(contrato.getRol());
		this.costoTotal -= contrato.getCosto();
	}
	
	public double getCostoTotal() { return costoTotal; }
	
	public List<ArtistaContratable> getArtistasContratables() { return artistasContratables; }

	public List<ArtistaBase> getArtistasBase() { return artistasBase; }	
	
	public List<Cancion> getCanciones() { return this.canciones; }
	
	public Artista buscarArtistaAll(String nombre) {
		
		ArrayList<Artista> todos = new ArrayList<Artista>(this.getArtistasBase());
		todos.addAll(artistasContratables);
		
		for(Artista a : todos) {
			if(a.getNombre().toLowerCase().equals(nombre.toLowerCase())) {
				return a;
			}
		}
		throw new ArtistaNoEncontradoException("El nombre ingresado no coincide con el de ningún artista. Intente nuevamente");
	}
}
