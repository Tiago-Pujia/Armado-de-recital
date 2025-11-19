package com.grupobeta.ArmadoDeRecital;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class CargarEstadoPrevio implements Comando {
	
	private Recital recital;
	private CargadorDeArchivos cargador;
	
	public CargarEstadoPrevio(Recital recital, CargadorDeArchivos cargador) {
		if (recital == null || cargador == null) {
			throw new IllegalArgumentException("Recital y CargadorDeArchivos no pueden ser null");
		}
		this.recital = recital;
		this.cargador = cargador;
	}
	
	@Override
	public void ejecutar() {
		System.out.print(Menu.ANSI_CYAN + "=== Cargar Estado Previo ===" + Menu.ANSI_RESET + "\n");
		
		try {
			List<Contratacion> contratacionesCargadas = cargarContratacionesDesdeArchivo("recital-out.json");
			
			if(contratacionesCargadas.isEmpty()) {
				System.out.println(Menu.ANSI_YELLOW + "El archivo está vacío o no contiene contrataciones válidas." + Menu.ANSI_RESET);
				return;
			}
			
			int contratacionesAplicadas = 0;
			int contratacionesOmitidas = 0;
			StringBuilder advertencias = new StringBuilder();
			Contratador contratador = recital.getContratador();
			
			for(Contratacion contrato : contratacionesCargadas) {
				try {
					Cancion cancion = recital.buscarCancion(contrato.getCancion().getTitulo());
					Artista artista = recital.buscarArtistaAll(contrato.getArtista().getNombre());
					
					List<Contratacion> contratosExistentes = recital.getContratosDeCancion(cancion);
					boolean yaExiste = false;
					for(Contratacion existente : contratosExistentes) {
						if(existente.getArtista().equals(artista) && 
						   existente.getRol().equals(contrato.getRol()) &&
						   existente.getCancion().equals(cancion)) {
							yaExiste = true;
							break;
						}
					}
					
					if(!yaExiste) {
						recital.getContrataciones().add(contratador.contratarArtistaParaUnRolEnCancion(artista, cancion, contrato.getRol(), contrato.getDescuento()));
						contratacionesAplicadas++;
						recital.aumentarCostoTotal(contrato.getCosto());
					} else {
						contratacionesOmitidas++;
					}
				} catch (Exception e) {
					contratacionesOmitidas++;
					advertencias.append(Menu.ANSI_RED + "Advertencia: No se pudo cargar la contratación: " + 
						contrato.getArtista().getNombre() + " - " + contrato.getRol() + " en " + 
						contrato.getCancion().getTitulo() + Menu.ANSI_RESET + "\n");
				}
			}
			
			StringBuilder resultado = new StringBuilder();
			resultado.append(Menu.ANSI_GREEN + "\nEstado cargado exitosamente:" + Menu.ANSI_RESET + "\n");
			resultado.append("  - Contrataciones aplicadas: " + Menu.ANSI_GREEN + contratacionesAplicadas + Menu.ANSI_RESET + "\n");
			if(contratacionesOmitidas > 0) {
				resultado.append("  - Contrataciones omitidas: " + Menu.ANSI_YELLOW + contratacionesOmitidas + Menu.ANSI_RESET + "\n");
			}
			resultado.append("  - Costo total actual: " + Menu.ANSI_GREEN + "$" + recital.getCostoTotal() + Menu.ANSI_RESET + "\n");
			if(advertencias.length() > 0) {
				resultado.append("\n" + advertencias.toString());
			}
			
			System.out.println(resultado.toString());
			return;
			
		} catch (IOException e) {
			System.out.println(Menu.ANSI_RED + "Error al leer el archivo: " + e.getMessage() + Menu.ANSI_RESET);
			return;
		} catch (Exception e) {
			System.out.println(Menu.ANSI_RED + "Error al cargar el estado: " + e.getMessage() + Menu.ANSI_RESET);
			return;
		}
	}
	
	private List<Contratacion> cargarContratacionesDesdeArchivo(String nombreArchivo) throws IOException {
		ArrayList<Contratacion> contrataciones = new ArrayList<Contratacion>();
		
		JSONArray contratosArray = cargador.parsearJSONArray(nombreArchivo);
		
		for(int i = 0; i < contratosArray.length(); i++) {
			JSONObject contratoJSON = contratosArray.getJSONObject(i);
			
			String nombreCancion = contratoJSON.getString(CargadorDeArchivos.CLAVE_CONTRATO_CANCION);
			String nombreArtista = contratoJSON.getString(CargadorDeArchivos.CLAVE_CONTRATO_ARTISTA);
			String rol = contratoJSON.getString(CargadorDeArchivos.CLAVE_CONTRATO_ROL);
			double costo = contratoJSON.getDouble(CargadorDeArchivos.CLAVE_CONTRATO_COSTO);
			
			Cancion cancion = recital.buscarCancion(nombreCancion);
			Artista artista = recital.buscarArtistaAll(nombreArtista);
			
			boolean hayDescuento = (costo < artista.getCosto());
			
			Contratacion contrato = Contratacion.contratarArtistaRol(cancion, artista, rol, hayDescuento);
			contrataciones.add(contrato);
		}
		
		return contrataciones;
	}
}