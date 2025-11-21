package com.grupobeta.ArmadoDeRecital;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

class ArtistaTests {
	
	ArtistaContratable artista1 = new ArtistaContratable("John Doe", new HashSet<String>(Arrays.asList("sintetizador","voz principal")), new HashSet<String>(Arrays.asList("Banda1","Banda2")), 1, 100.0 );
	
	@Test
	void queCompartioBandaConUnArtistaPeroNoConOtro() {
		
		ArtistaContratable artista2 = new ArtistaContratable("Lautaro Claure", new HashSet<String>(Arrays.asList("violín")), new HashSet<String>(Arrays.asList("Banda2")), 2, 200.0 );
		ArtistaContratable artista3 = new ArtistaContratable("Fabricio De Barros", new HashSet<String>(Arrays.asList("guitarra eléctrica")), new HashSet<String>(Arrays.asList("Banda3")), 3, 300.0 );
		
		assertTrue(artista1.compartioBandaCon(artista2));
		assertTrue(artista2.compartioBandaCon(artista1));
		assertFalse(artista1.compartioBandaCon(artista3));
		assertFalse(artista2.compartioBandaCon(artista3));		
	}
	
	@Test
	void queSeAumentaSuCostoAlEntrenarloYSeLeAgregaUnNuevoRol() {
		
		String nuevoRol = "Fagot";
		double costoAnt = artista1.getCosto();
		
		artista1.entrenar(nuevoRol);
		assertTrue(artista1.tieneRol(nuevoRol));
		assertTrue(artista1.getCosto() > costoAnt);
	}
}
