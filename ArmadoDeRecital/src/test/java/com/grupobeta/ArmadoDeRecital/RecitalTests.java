package com.grupobeta.ArmadoDeRecital;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RecitalTests {

	@Test
	void queNoHayaArtistasDisponiblesParaUnaCancion() {
		
		Recital recital = new Recital(new CargadorDeArchivos());
		//recital.contratarArtistasParaCancion(new Cancion());
	}

}
