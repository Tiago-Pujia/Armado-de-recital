package com.grupobeta.ArmadoDeRecital;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

public class CargadorDeArchivos {
	
	public static final String ARCHIVO_RECITAL = "recital.json";
	public static final String ARCHIVO_ARTISTAS = "artistas.json";
	public static final String ARCHIVO_ARTISTAS_BASE = "artistas-base.json";
	
	public static final String CLAVE_CANCION_TITULO = "titulo";
	public static final String CLAVE_CANCION_ROLES = "rolesRequeridos";
	
	public static final String CLAVE_ARTISTA_NOMBRE = "nombre";
	public static final String CLAVE_ARTISTA_ROLES = "roles";
	public static final String CLAVE_ARTISTA_COSTO = "costo";
	public static final String CLAVE_ARTISTA_MAX = "maxCanciones";
	public static final String CLAVE_ARTISTA_BANDAS = "bandas";
	
	public JSONArray parsearJSONArray(String nomArch) throws IOException {
		String contenido = new String(Files.readAllBytes(Paths.get(nomArch)));
		return new JSONArray(contenido);		
	}
	
	public ArrayList<Cancion> cargarArchivoRecital() {
		
		ArrayList<Cancion> canciones = new ArrayList<Cancion>();
		
		try {
			JSONArray cancionesArray = this.parsearJSONArray(ARCHIVO_RECITAL);
	
			for(int i = 0; i < cancionesArray.length(); i++) {
				
				JSONObject cancionJSON = (JSONObject) cancionesArray.get(i);
				String titulo = (String)cancionJSON.get(CLAVE_CANCION_TITULO);
				
				JSONArray rolesJSON = (JSONArray) cancionJSON.get(CLAVE_CANCION_ROLES);
				
				HashMap<String, Integer> roles = new HashMap<String, Integer>();
				for(int j = 0 ; j < rolesJSON.length() ; j++) {
					String rol = rolesJSON.getString(j);
					roles.put(rol, roles.getOrDefault(rol, 0) + 1); // roles.add(rolesJSON.getString(j));
				}
				
				canciones.add(new Cancion(titulo,roles));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return canciones;
	}
	
	public ArrayList<Artista> cargarArchivoArtistas() {
		
			ArrayList<Artista> repertorio = new ArrayList<Artista>();
			
			try {
				JSONArray artistasArray = this.parsearJSONArray(ARCHIVO_ARTISTAS);
		
				for(int i = 0; i < artistasArray.length(); i++) {
					
					JSONObject artistaJSON = (JSONObject) artistasArray.get(i);
					String nombre = (String)artistaJSON.get(CLAVE_ARTISTA_NOMBRE);
					double costo = (int)artistaJSON.get(CLAVE_ARTISTA_COSTO);
					int maxCanc = artistaJSON.getInt(CLAVE_ARTISTA_MAX);
										
					JSONArray rolesJSON = (JSONArray) artistaJSON.get(CLAVE_ARTISTA_ROLES);
					JSONArray historicoJSON = (JSONArray) artistaJSON.get(CLAVE_ARTISTA_BANDAS);
					
					ArrayList<String> roles = new ArrayList<String>();
					for(int j = 0 ; j < rolesJSON.length() ; j++) {
						roles.add(rolesJSON.getString(j));
					}
					
					ArrayList<String> historicoBandas = new ArrayList<String>();
					for(int j = 0 ; j < historicoJSON.length() ; j++) {
						historicoBandas.add(historicoJSON.getString(j));
					}
					
					repertorio.add(new Artista(nombre, roles, costo, maxCanc, historicoBandas));
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			return repertorio;
		}
	
	public ArrayList<String> cargarArchivoArtistasBase(){
		
		ArrayList<String> nombresArtistasBase = new ArrayList<String>();
		//ArrayList<Artista> artistasBase = new ArrayList<Artista>();
		
		try {
			
			JSONArray artistasArray = this.parsearJSONArray(ARCHIVO_ARTISTAS_BASE);
			
			for(int i = 0; i < artistasArray.length(); i++) {
				nombresArtistasBase.add((String)artistasArray.get(i));
			}
			
			//ArrayList<Artista> repertorio = this.cargarArchivoArtistas();
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		return nombresArtistasBase;		
	}
}
