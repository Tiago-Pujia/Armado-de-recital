package com.grupobeta.ArmadoDeRecital;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

class QuitarArtistaComandoTests {
	
	Recital recital = new Recital(new CargadorDeArchivos());
	
	@Test
	void queRemueveContratosDeArtista() {
		
		Cancion cancion = recital.buscarCancion("Even Flow");
		List<Contratacion> contratos = recital.contratarArtistasParaCancion(cancion);

		assertEquals(4, contratos.size()); /// 1 de los 4 contratos corresponde a Brian May

		String entrada = "\nBrian May\n";
		Scanner scanner = new Scanner(entrada);
		QuitarUnArtistaComando comando = new QuitarUnArtistaComando(recital, scanner);

		comando.ejecutar();

		assertEquals(3, recital.getContrataciones().size()); // se espera que se remueva exactamente un contrato de la canción
	}

	@Test
	void queNoFuncionaSiElArtistaNoExiste() {

		String entrada = "\nRob Zombie\n";
		Scanner scanner = new Scanner(entrada);
		QuitarUnArtistaComando comando = new QuitarUnArtistaComando(recital, scanner);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;
		System.setOut(new PrintStream(baos));

		comando.ejecutar();

		System.out.flush();
		System.setOut(originalOut);

		String salida = baos.toString();
		assertTrue(salida.contains("no coincide") || salida.contains("no se encontró"));
	}

	@Test
	void queNoFuncionaSiArtistaNoTieneContratos() {

		String entrada = "\nJohn Deacon\n";
		Scanner scanner = new Scanner(entrada);
		QuitarUnArtistaComando comando = new QuitarUnArtistaComando(recital, scanner);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream originalOut = System.out;
		System.setOut(new PrintStream(baos));

		comando.ejecutar();

		System.out.flush();
		System.setOut(originalOut);

		String salida = baos.toString();
		assertTrue(salida.contains("no tiene contratos"));
	}

	@Test
	void queDevuelveRolACancionAlQuitarArtista() {
		
		Cancion cancion = recital.buscarCancion("Falling Away from Me");

		// Quitar artista
		String inputQuitarArtista = "\nMorrissey\n";
		Scanner scanner = new Scanner(inputQuitarArtista);
		QuitarUnArtistaComando comando = new QuitarUnArtistaComando(recital, scanner);
		comando.ejecutar();

		// Verificar que el rol se devolvió
		assertTrue(cancion.getRolesRequeridos().containsKey("voz principal"));
		assertEquals(1, cancion.getRolesRequeridos().get("voz principal"));
	}
}
