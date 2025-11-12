package com.grupobeta.ArmadoDeRecital;

//import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;

class CargadorDeArchivosTests {

	public static final String ARCHIVO_RECITAL = "recital.json";
	public static final String ARCHIVO_ARTISTAS = "artistas.json";
	public static final String ARCHIVO_ARTISTAS_BASE = "artistas-base.json";
	
	public static final String CLAVE_ARTISTA_NOMBRE = "nombre";
	public static final String CLAVE_ARTISTA_ROLES = "roles";
	public static final String CLAVE_ARTISTA_COSTO = "costo";
	public static final String CLAVE_ARTISTA_MAX = "maxCanciones";
	public static final String CLAVE_ARTISTA_BANDAS = "bandas";
	
//	@Test
//	void pasarArchivosAJSONArray() throws IOException {
//		CargadorDeArchivos cargador = new CargadorDeArchivos();
//		JSONArray arrayCanciones = cargador.parsearJSONArray(ARCHIVO_RECITAL);
//		JSONArray arrayArtistas = cargador.parsearJSONArray(ARCHIVO_ARTISTAS);
//		JSONArray arrayArtistasBase = cargador.parsearJSONArray(ARCHIVO_ARTISTAS_BASE);
//		System.out.println(arrayCanciones.toString());
//		System.out.println(arrayArtistas.toString());
//		System.out.println(arrayArtistasBase.toString());
//	}
//	
//	@Test
//	void cargadoDeArchivoArtistas() {
//			
//		CargadorDeArchivos cargador = new CargadorDeArchivos();
//		ArrayList<ArtistaContratado> repertorio = cargador.cargarArchivoArtistas();
//		
//		for(Artista a : repertorio) {
//			System.out.println(a.toString());
//		}
//	}
//	
//	@Test
//	void cargadoDeArchivoRecital() {
//		
//		CargadorDeArchivos cargador = new CargadorDeArchivos();
//		ArrayList<Cancion> canciones = cargador.cargarArchivoRecital();
//		
//		for(Cancion c : canciones) {
//			System.out.println(c.toString());
//		}
//	}
	
	@Test()
	void cargadoDeArtistasBase() {
		
		CargadorDeArchivos cargador = new CargadorDeArchivos();
		ArrayList<ArtistaContratado> repertorio = cargador.cargarArchivoArtistas();
		ArrayList<ArtistaBase> artBase = cargador.cargarArchivoArtistasBase(repertorio);
		
		for(ArtistaBase ab : artBase) {
			System.out.println(ab);
		}
	}
}
