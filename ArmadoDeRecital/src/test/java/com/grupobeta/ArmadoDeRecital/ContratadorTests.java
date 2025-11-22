package com.grupobeta.ArmadoDeRecital;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

class ContratadorTests {
	
	Contratador contratador = new Contratador();
	ArtistaContratable artista1 = new ArtistaContratable("Sid", new HashSet<String>(Arrays.asList("sintetizador")), new HashSet<String>(Arrays.asList("Banda1","Banda2")), 1, 100.0 );
	ArtistaContratable artista2 = new ArtistaContratable("Jeff Buckley", new HashSet<String>(Arrays.asList("voz principal","guitarra acústica")), new HashSet<String>(Arrays.asList("Banda1","Banda2")), 1, 100.0 );
	ArtistaContratable artista3 = new ArtistaContratable("Simon Gallup", new HashSet<String>(Arrays.asList("bajo")), new HashSet<String>(Arrays.asList("The Cure")), 1, 100.0 );
	
	@Test
	void queSeAumentaLaCantDeContratosDeArtistaAlContratarloYSeModificaElMapDeRolesDeLaCancion() {
		
		HashMap<String, Integer> roles = new HashMap<String, Integer>();		
		roles.put("sintetizador", 1);
		Cancion cancion = new Cancion("505", roles);
		int cantContratosAntes = artista1.getCantContratos();
		HashMap<String, Integer> rolesAntes = new HashMap<String, Integer>(roles);
		
		Contratador.crearContratoDesdeDatosPrevios(artista1,cancion, "sintetizador", artista1.getCosto());
		assertFalse(cancion.getRolesRequeridos().equals(rolesAntes));
		assertNotEquals(artista1.getCantContratos(), cantContratosAntes);
	}
	
	@Test
	void queNoSePuedeContratarUnArtistaParaUnRolQueNoTieneSiNoEsDuranteLaCargaDeUnEstadoPrevio() {
		
		HashMap<String, Integer> roles = new HashMap<String, Integer>();		
		roles.put("guitarra eléctrica", 1);
		Cancion cancion = new Cancion("Lonely Day", roles);
		
		assertThrows(IllegalArgumentException.class, ()->{
			contratador.contratarArtistaParaUnRolEnCancion(artista1,cancion,"guitarra eléctrica", false);}
		);
		assertNotNull(Contratador.crearContratoDesdeDatosPrevios(artista1,cancion,"triángulo", artista1.getCosto()));
	}
	
	@Test
	void queArtistasNoSonContratablesParaUnaCancion() {
		
		ArrayList<Contratacion> contrataciones = new ArrayList<Contratacion>();
		HashMap<String, Integer> roles = new HashMap<String, Integer>();		
		roles.put("bajo", 1);
		roles.put("guitarra eléctrica", 2);
		Cancion cancion = new Cancion("Bite My Hip", roles);
		
		contrataciones.add(Contratador.crearContratoDesdeDatosPrevios(artista2,cancion,"bajo", artista1.getCosto())); 
		
		assertFalse(contratador.artistaEsContratableParaCancion(contrataciones,artista2,cancion)); //el artista 2 tiene un contrato ya para esa cancion
		assertFalse(contratador.artistaEsContratableParaCancion(contrataciones,artista2,cancion)); //el artista 1 no tiene ningún rol necesario
	}
	
	@Test
	void queUnArtistaTieneDescuentoPorCompartirBandaConBaseContratadoParaCancionYOtroNo() {
		
		ArrayList<Contratacion> contrataciones = new ArrayList<Contratacion>();
		HashMap<String, Integer> roles = new HashMap<String, Integer>();		
		ArrayList<ArtistaBase> bases = new ArrayList<ArtistaBase>();
		ArtistaBase base = new ArtistaBase("Robert Smith", new HashSet<String>(Arrays.asList("voz principal")), new HashSet<String>(Arrays.asList("The Cure")));
		
		roles.put("voz principal", 1);
		roles.put("guitarra eléctrica", 2);
		roles.put("bajo", 1);
		Cancion cancion = new Cancion("Mother", roles);
		bases.add(base);
		
		contrataciones.add(Contratador.crearContratoDesdeDatosPrevios(base,cancion, "bajo", base.getCosto())); 
		
		assertTrue(contratador.evaluarDescuento(artista3, cancion, bases, contrataciones)); //el artista 3 compartio banda con un base contratado para esa cancion
		assertFalse(contratador.evaluarDescuento(artista1, cancion, bases, contrataciones)); //el artista 1, no
	}
}
