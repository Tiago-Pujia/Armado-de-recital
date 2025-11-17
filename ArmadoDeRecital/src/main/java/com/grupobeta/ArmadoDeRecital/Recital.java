package com.grupobeta.ArmadoDeRecital;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Recital {
	
	private ArrayList<Cancion> canciones = null; 
	private ArrayList<Contratacion> contrataciones = null;
	private ArrayList<ArtistaContratable> artistasContratables = null;
	private ArrayList<ArtistaBase> artistasBase = null;
	private Contratador contratador = null;
	private double costoTotal = 0;
	
	public Recital(CargadorDeArchivos cargador) {
		this.canciones = cargador.cargarArchivoRecital();
		this.artistasContratables = cargador.cargarArchivoArtistas();
		this.artistasBase = cargador.cargarArchivoArtistasBase(artistasContratables);
		this.contrataciones = new ArrayList<Contratacion>();
		this.contratador = new Contratador();
		this.agregarArtistasBase();
	}
	
	private void agregarArtistasBase() {
		
		for(Cancion cancion : canciones) { 
			
			for(ArtistaBase artista : artistasBase) {
				if(contratador.artistaEsContratableParaCancion(this.contrataciones, artista, cancion)) {
					Contratacion contratacion = this.contratador.contratarArtistaParaUnRolCualquieraEnCancion(artista, cancion);
					this.contrataciones.add(contratacion);
					this.aumentarCostoTotal(contratacion.getCosto());
				}
			}
		}
	}
	
	///punto 1
	public HashMap<String, Integer> consultarRolesFaltantesParaCancion(Cancion cancion) {		
		return cancion.getRolesRequeridos();
	}
	
	//punto 3
	public ArrayList<Contratacion> contratarArtistasParaCancion(Cancion cancion) {
		
		boolean rolVacio = true, primerContratable = true;
		HashMap<String, Integer> copia = new HashMap<String, Integer>(cancion.getRolesRequeridos());
		ArrayList<Contratacion> contratosRealizados = new ArrayList<Contratacion>();
		
		if(copia.isEmpty()) {
			return contratosRealizados;
		}
		
		for(Map.Entry<String, Integer> rol : copia.entrySet()) {
			
			while(rol.getValue() != 0) {
				rolVacio = true;
				ArtistaContratable artistaMin = artistasContratables.iterator().next();
				for(ArtistaContratable artista : artistasContratables) {
					
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
					boolean huboDescuento = contratador.aplicarDescuento(artistaMin, cancion, artistasBase, contrataciones);
					Contratacion contratacion = contratador.contratarArtistaParaUnRolEnCancion(artistaMin, cancion, rol.getKey());
					this.contrataciones.add(contratacion);
					this.aumentarCostoTotal(contratacion.getCosto());
					contratosRealizados.add(contratacion);
					rol.setValue(rol.getValue()-1);
					if(huboDescuento) {
						artistaMin.aumentarCostoContratacion(ArtistaContratable.AUMENTO_ARREGLO);
					}
				}
				else {
					break;
				}
			}
		}
		
		return contratosRealizados;
	}
	
	///punto 6
	public ArrayList<Contratacion> getContrataciones() {
		this.contrataciones.sort(null);
		return this.contrataciones;
	}
	
	public double obtenerCostoContratacionesYContratosCancion(Cancion cancion, ArrayList<Contratacion> contrato) {
		
		double costoCancion = 0;
		
		for(Contratacion con : contrataciones) {
			if(con.getCancion().getTitulo().toLowerCase().equals(cancion.getTitulo().toLowerCase())) {
				costoCancion += con.getCosto();
				contrato.add(con);
			}
		}
		
		return costoCancion;
	}
	
	private void aumentarCostoTotal(double monto){
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
	
	public ArrayList<Contratacion> getContratosDeArtista(Artista artista) {
		
		ArrayList<Contratacion>contratosArtista = new ArrayList<Contratacion>();
		
		for(Contratacion c : this.contrataciones) {
			if(c.getArtista().equals(artista)) {
				contratosArtista.add(c);
			}
		}
		return contratosArtista;
	}
	
	public void removerContratacion(Contratacion contrato){
		this.contrataciones.remove(contrato);
		contrato.getCancion().agregarRol(contrato.getRol());
	}
	
	public double getCostoTotal() { return costoTotal; }
	
	public ArrayList<ArtistaContratable> getArtistasContratables() { return artistasContratables; }

	public ArrayList<ArtistaBase> getArtistasBase() { return artistasBase; }	
	
	public ArrayList<Cancion> getCanciones() { return this.canciones; }
}
