package com.grupobeta.ArmadoDeRecital;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

class EntrenarArtistaComandoTests {

	@Test
	void queAgregaRolYAumentaCosto() {
		
		Recital recital = new Recital(new CargadorDeArchivos());
		ArtistaContratable artista = recital.buscarArtistaContratable("Bono");
		double costoOriginal = artista.getCosto();

		String inputEntrenar = "\nBono\ntriángulo\n";
		Scanner scanner = new Scanner(inputEntrenar);

		EntrenarArtistaComando comando = new EntrenarArtistaComando(scanner, recital, new Menu());

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;
		System.setOut(new PrintStream(baos));

		comando.ejecutar();

		System.out.flush();
		System.setOut(originalOut);

		assertTrue(artista.tieneRol("triángulo"));
		assertEquals(costoOriginal * ArtistaContratable.AUMENTO_POR_ENTRENAMIENTO, artista.getCosto());
	}

	@Test
	void queNoEntrenaSiYaTieneElRol() {
	
		Recital recital = new Recital(new CargadorDeArchivos());
		ArtistaContratable artista = recital.buscarArtistaContratable("Dave Grohl");
		double costoOriginal = artista.getCosto();

		String inputEntrenar = "\nDave Grohl\nbatería\n";
		Scanner scanner = new Scanner(inputEntrenar);

		EntrenarArtistaComando comando = new EntrenarArtistaComando(scanner, recital, new Menu());

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;
		System.setOut(new PrintStream(baos));

		comando.ejecutar();

		System.out.flush();
		System.setOut(originalOut);

		// El costo no debe cambiar si ya tenía el rol
		assertEquals(costoOriginal, artista.getCosto());
	}
	
	@Test
	void queNoEntrenaSiArtistaAlcanzaMaxCanciones() {

		Recital recital = new Recital(new CargadorDeArchivos());

		recital.contratarArtistasParaCancion(recital.buscarCancion("Heart-Shaped Box"));
		recital.contratarArtistasParaCancion(recital.buscarCancion("Cities in Dust"));

		ArtistaContratable artista = recital.buscarArtistaContratable("Kate Bush");
		double costoOriginal = artista.getCosto();

		// Intentar entrenar
		String inputEntrenar = "\nStevie Wonder\nbajo\n";
		Scanner scanner = new Scanner(inputEntrenar);

		EntrenarArtistaComando comando = new EntrenarArtistaComando(scanner, recital, new Menu());

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;
		System.setOut(new PrintStream(baos));

		comando.ejecutar();

		System.out.flush();
		System.setOut(originalOut);

		// No debe entrenar si ya alcanzó max canciones
		assertFalse(artista.tieneRol("bajo"));
		assertEquals(costoOriginal, artista.getCosto());
	}
}
