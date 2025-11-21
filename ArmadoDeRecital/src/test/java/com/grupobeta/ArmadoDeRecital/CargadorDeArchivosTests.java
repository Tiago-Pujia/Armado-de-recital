package com.grupobeta.ArmadoDeRecital;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

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
	
	@Test
	void queParseaCorrectamenteLosArchivos() throws IOException {
		
		String cancionesJSON = "[{\"titulo\":\"Love Will Tear Us Apart\","
				+ "\"rolesRequeridos\":[\"voz principal\",\"guitarra eléctrica\",\"bajo\",\"batería\",\"piano\"]},{\"titulo\":\"I Wanna Be Sedated\",\"rolesRequeridos\":[\"voz principal\",\"guitarra eléctrica\",\"bajo\",\"batería\"]},{\"titulo\":\"These Are the Days of Our Lives\",\"rolesRequeridos\":[\"voz principal\",\"guitarra eléctrica\",\"bajo\",\"batería\"]},{\"titulo\":\"Lucretia My Reflection\",\"rolesRequeridos\":[\"voz principal\",\"coros\",\"guitarra eléctrica\",\"bajo\",\"batería\"]},{\"titulo\":\"Heaven Knows I'm Miserable Now\",\"rolesRequeridos\":[\"voz principal\",\"guitarra eléctrica\",\"piano\",\"bajo\",\"batería\"]},{\"titulo\":\"I Don't Wanna Be Me\",\"rolesRequeridos\":[\"voz principal\",\"piano\",\"bajo\",\"guitarra acústica\",\"batería\"]},{\"titulo\":\"Falling Away from Me\",\"rolesRequeridos\":[\"voz principal\",\"guitarra eléctrica\",\"guitarra acústica\",\"bajo\",\"batería\",\"sintetizador\"]},{\"titulo\":\"Heart-Shaped Box\",\"rolesRequeridos\":[\"voz principal\",\"guitarra eléctrica\",\"bajo\",\"batería\"]},{\"titulo\":\"Welcome To The Black Parade\",\"rolesRequeridos\":[\"voz principal\",\"guitarra eléctrica\",\"bajo\",\"batería\"]},{\"titulo\":\"Last Caress\",\"rolesRequeridos\":[\"voz principal\",\"guitarra eléctrica\",\"bajo\",\"batería\"]},{\"titulo\":\"Friday I'm In Love\",\"rolesRequeridos\":[\"voz principal\",\"guitarra eléctrica\",\"bajo\",\"batería\",\"teclado\"]},{\"titulo\":\"21st Century Schizoid Man\",\"rolesRequeridos\":[\"voz principal\",\"guitarra eléctrica\",\"bajo\",\"batería\",\"saxofón\"]},{\"titulo\":\"Man In The Box\",\"rolesRequeridos\":[\"voz principal\",\"guitarra eléctrica\",\"guitarra eléctrica\",\"bajo\",\"batería\"]},{\"titulo\":\"Paranoid\",\"rolesRequeridos\":[\"voz principal\",\"guitarra eléctrica\",\"guitarra eléctrica\",\"bajo\",\"batería\"]},{\"titulo\":\"Cities in Dust\",\"rolesRequeridos\":[\"voz principal\",\"guitarra eléctrica\",\"bajo\",\"batería\"]},{\"titulo\":\"In The End\",\"rolesRequeridos\":[\"voz principal\",\"voz secundaria\",\"guitarra eléctrica\",\"bajo\",\"batería\"]},{\"titulo\":\"Cherry Waves\",\"rolesRequeridos\":[\"voz principal\",\"guitarra eléctrica\",\"bajo\",\"batería\"]},{\"titulo\":\"Toxicity\",\"rolesRequeridos\":[\"voz principal\",\"voz secundaria\",\"guitarra eléctrica\",\"bajo\",\"batería\"]},"
				+ "{\"titulo\":\"We Don't Believe What's on TV\",\"rolesRequeridos\":[\"voz principal\",\"ukelele\",\"teclado\",\"batería\",\"sintetizador\"]},{\"titulo\":\"Even Flow\",\"rolesRequeridos\":[\"voz principal\",\"guitarra eléctrica\",\"bajo\",\"batería\"]},{\"titulo\":\"Around the World\",\"rolesRequeridos\":[\"voz principal\",\"teclado\",\"sintetizador\"]}]";
		
		String artistasJSON = "[{\"bandas\":[\"Queen\"],\"maxCanciones\":100,\"costo\":0,\"roles\":[\"guitarra eléctrica\",\"voz secundaria\"],\"nombre\":\"Brian May\"},{\"bandas\":[\"Queen\"],\"maxCanciones\":100,\"costo\":0,\"roles\":[\"batería\",\"voz secundaria\"],\"nombre\":\"Roger Taylor\"},{\"bandas\":[\"Queen\"],\"maxCanciones\":100,\"costo\":0,\"roles\":[\"bajo\"],\"nombre\":\"John Deacon\"},{\"bandas\":[\"Billy Joel Band\"],\"maxCanciones\":100,\"costo\":0,\"roles\":[\"piano\"],\"nombre\":\"Billy Joel\"},{\"bandas\":[\"The Smiths\"],\"maxCanciones\":100,\"costo\":0,\"roles\":[\"voz principal\"],\"nombre\":\"Morrissey\"},{\"bandas\":[\"Wham!\",\"George Michael\"],\"maxCanciones\":3,\"costo\":1000,\"roles\":[\"voz principal\"],\"nombre\":\"George Michael\"},{\"bandas\":[\"Elton John Band\"],\"maxCanciones\":2,\"costo\":1200,\"roles\":[\"voz principal\",\"piano\"],\"nombre\":\"Elton John\"},{\"bandas\":[\"Tin Machine\",\"David Bowie\"],\"maxCanciones\":2,\"costo\":1500,\"roles\":[\"voz principal\"],\"nombre\":\"David Bowie\"},{\"bandas\":[\"Eurythmics\"],\"maxCanciones\":2,\"costo\":900,\"roles\":[\"voz principal\"],\"nombre\":\"Annie Lennox\"},{\"bandas\":[\"Lisa Stansfield\"],\"maxCanciones\":2,\"costo\":800,\"roles\":[\"voz principal\"],\"nombre\":\"Lisa Stansfield\"},{\"bandas\":[\"Bad Company\"],\"maxCanciones\":3,\"costo\":1000,\"roles\":[\"voz principal\",\"guitarra acústica\"],\"nombre\":\"Paul Rodgers\"},{\"bandas\":[\"Stevie Wonder\"],\"maxCanciones\":2,\"costo\":1400,\"roles\":[\"piano\",\"teclado\",\"voz principal\",\"sintetizador\"],\"nombre\":\"Stevie Wonder\"},{\"bandas\":[\"The Police\"],\"maxCanciones\":3,\"costo\":1100,\"roles\":[\"bajo\",\"voz principal\"],\"nombre\":\"Sting\"},{\"bandas\":[\"U2\"],\"maxCanciones\":2,\"costo\":1300,\"roles\":[\"voz principal\"],\"nombre\":\"Bono\"},{\"bandas\":[\"Foo Fighters\",\"Nirvana\"],\"maxCanciones\":4,\"costo\":1000,\"roles\":[\"guitarra eléctrica\",\"batería\",\"voz secundaria\"],\"nombre\":\"Dave Grohl\"},{\"bandas\":[\"Pink Floyd\"],\"maxCanciones\":3,\"costo\":1000,\"roles\":[\"bajo\",\"voz secundaria\"],\"nombre\":\"Roger Waters\"},{\"bandas\":[\"Kate Bush\"],\"maxCanciones\":2,\"costo\":1000,\"roles\":[\"voz principal\",\"piano\"],\"nombre\":\"Kate Bush\"},{\"bandas\":[\"My Chemical Romance\"],\"maxCanciones\":2,\"costo\":1200,\"roles\":[\"voz principal\"],\"nombre\":\"Gerard Way\"},{\"bandas\":[\"Twenty One Pilots\"],\"maxCanciones\":3,\"costo\":1500,\"roles\":[\"voz principal\",\"guitarra acústica\",\"ukelele\"],\"nombre\":\"Tyler Joseph\"}]";
		String artistasBase = "[\"Brian May\",\"Roger Taylor\",\"John Deacon\",\"Billy Joel\",\"Morrissey\"]";
		
		CargadorDeArchivos cargador = new CargadorDeArchivos();
		JSONArray arrayCanciones = cargador.parsearJSONArray(ARCHIVO_RECITAL);
		JSONArray arrayArtistas = cargador.parsearJSONArray(ARCHIVO_ARTISTAS);
		JSONArray arrayArtistasBase = cargador.parsearJSONArray(ARCHIVO_ARTISTAS_BASE);
		assertEquals(arrayCanciones.toString(), cancionesJSON);
		assertEquals(arrayArtistas.toString(), artistasJSON);
		assertEquals(arrayArtistasBase.toString(), artistasBase);
	}
}
