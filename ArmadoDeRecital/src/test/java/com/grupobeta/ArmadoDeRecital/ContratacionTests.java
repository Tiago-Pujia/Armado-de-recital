package com.grupobeta.ArmadoDeRecital;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ContratacionTests {
	
	static Recital recital = new Recital(new CargadorDeArchivos());
	
	@Test
	void queNoPuedaContratarAUnArtistaConUnRolQueNoTiene() {
		assertThrows(IllegalArgumentException.class, ()->{
			Contratacion.contratarArtistaRol(recital.buscarCancion("Paranoid"), recital.buscarArtistaAll("Tyler Joseph"), "Coros", false);
		});
	}
}
