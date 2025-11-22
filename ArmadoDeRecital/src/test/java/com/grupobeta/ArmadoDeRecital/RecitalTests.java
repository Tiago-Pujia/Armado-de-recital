package com.grupobeta.ArmadoDeRecital;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RecitalTests {
	
	static Recital recital = new Recital(new CargadorDeArchivos());
	static ContratarArtistasAllComando comando = new ContratarArtistasAllComando(recital);
	
	@BeforeAll
	public static void llenarContratos() {
		comando.ejecutar();
	}
	
	@Test
	void queNoSePuedenCubrirTodosLosRolesParaUnaCancionConElRolCorosYDaFalse() {		
		assertTrue(!recital.buscarCancion("Lucretia My Reflection").getRolesRequeridos().isEmpty());
	}
	
	@Test
	void queSePuedanCubrirTodosLosRolesParaUnaCancionYSusRolesEstenVacios() {		
		assertTrue(recital.buscarCancion("Love Will Tear Us Apart").getRolesRequeridos().isEmpty());
	}
	
	@Test
	void queLosRolesRestantesPorCubrirPorCancionSeanLosEsperados() {
				
		assertEquals(0, recital.buscarCancion("love will tear us apart").getRolesRequeridos().size());
		assertEquals(0, recital.buscarCancion("I Wanna Be Sedated").getRolesRequeridos().size());
		assertEquals(0, recital.buscarCancion("These Are the Days of Our Lives").getRolesRequeridos().size());
		assertEquals(1, recital.buscarCancion("Lucretia My Reflection").getRolesRequeridos().size());
		assertEquals(0, recital.buscarCancion("Heaven Knows I'm Miserable Now").getRolesRequeridos().size());
		assertEquals(0, recital.buscarCancion("I Don't Wanna Be Me").getRolesRequeridos().size());
		assertEquals(0, recital.buscarCancion("Falling Away from Me").getRolesRequeridos().size());
		assertEquals(0, recital.buscarCancion("Heart-Shaped Box").getRolesRequeridos().size());
		assertEquals(0, recital.buscarCancion("Welcome To The Black Parade").getRolesRequeridos().size());
		assertEquals(0, recital.buscarCancion("Last Caress").getRolesRequeridos().size());
		assertEquals(0, recital.buscarCancion("Friday I'm In Love").getRolesRequeridos().size());
		assertEquals(1, recital.buscarCancion("21st Century Schizoid Man").getRolesRequeridos().size());
		assertEquals(0, recital.buscarCancion("Man In The Box").getRolesRequeridos().size());
		assertEquals(0, recital.buscarCancion("Paranoid").getRolesRequeridos().size());
		assertEquals(0, recital.buscarCancion("Cities in Dust").getRolesRequeridos().size());
		assertEquals(0, recital.buscarCancion("In The End").getRolesRequeridos().size());
		assertEquals(0, recital.buscarCancion("Cherry Waves").getRolesRequeridos().size());
		assertEquals(0, recital.buscarCancion("Toxicity").getRolesRequeridos().size());
		assertEquals(2, recital.buscarCancion("We Don\'t Believe What\'s on TV").getRolesRequeridos().size());
		assertEquals(0, recital.buscarCancion("Even Flow").getRolesRequeridos().size());
		assertEquals(2, recital.buscarCancion("Around the World").getRolesRequeridos().size());
	}
	
	@Test
	void queAlBuscarUnaCancionSeAplicaTrimYToLowerAlString() {		
		assertNotNull(recital.buscarCancion("      loVe WILL TeAr Us aPaRt "));
	}
	
	@Test
	void queAlBuscarUnArtistaSeAplicaTrimYToLowerAlString() {		
		assertNotNull(recital.buscarArtistaAll("    GeRaRd wAY      "));
	}
	
	@Test
	void queNoSePuedeBuscarCancionesQueNoExisten() {		
		assertThrows(CancionNoEncontradaException.class, ()->{
			recital.buscarCancion("Man In The Mirror");
		});
	}
	
	@Test
	void queNoSePuedeBuscarArtistasQueNoExisten() {		
		assertThrows(ArtistaNoEncontradoException.class, ()->{
			recital.buscarArtistaAll("Robert Fripp");
		});
	}
}
