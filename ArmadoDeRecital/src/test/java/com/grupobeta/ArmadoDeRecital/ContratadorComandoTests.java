package com.grupobeta.ArmadoDeRecital;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

class ContratadorComandoTests {

    @Test
    void rolesQuePuedeTomarArtistaParaCancioninterseccionCorrecta() {
        Contratador contratador = new Contratador();

        Set<String> rolesArtista = new HashSet<String>(Arrays.asList("guitarra","voz"));
        ArtistaContratable artista = new ArtistaContratable("A", rolesArtista, new HashSet<String>(), 1, 100);

        HashMap<String,Integer> rolesCancion = new HashMap<String,Integer>();
        rolesCancion.put("guitarra", 1);
        rolesCancion.put("bajo", 1);
        Cancion cancion = new Cancion("T", rolesCancion);

        Set<String> interseccion = contratador.rolesQuePuedeTomarArtistaParaCancion(artista, cancion);

        assertTrue(interseccion.contains("guitarra"));
        assertEquals(1, interseccion.size());
    }

    @Test
    void contratarArtistaParaUnRolEnCancionRemueveRolYAumentaContratos() {
        Contratador contratador = new Contratador();

        Set<String> rolesArtista = new HashSet<String>(Arrays.asList("voz"));
        ArtistaContratable artista = new ArtistaContratable("Cantante", rolesArtista, new HashSet<String>(), 2, 200);

        HashMap<String,Integer> rolesCancion = new HashMap<String,Integer>();
        rolesCancion.put("voz", 1);
        Cancion cancion = new Cancion("Balada", rolesCancion);

        Contratacion contrato = contratador.contratarArtistaParaUnRolEnCancion(artista, cancion, "voz", false);

        assertNotNull(contrato);
        assertEquals("voz", contrato.getRol());
        assertEquals(1, artista.getCantContratos());
        assertFalse(cancion.getRolesRequeridos().containsKey("voz"));
    }

    @Test
    void evaluarDescuentoDevuelveTrueSiHayBaseContratadaYCompartieronBanda() {
        Contratador contratador = new Contratador();

        Set<String> rolesBase = new HashSet<String>(Arrays.asList("guitarra"));
        Set<String> bandas = new HashSet<String>(Arrays.asList("LaBanda"));
        ArtistaBase base = new ArtistaBase("Base", rolesBase, bandas);

        Set<String> rolesArt = new HashSet<String>(Arrays.asList("bateria","guitarra"));
        Set<String> bandasArt = new HashSet<String>(Arrays.asList("LaBanda"));
        ArtistaContratable artista = new ArtistaContratable("Contratable", rolesArt, bandasArt, 2, 150);

        HashMap<String,Integer> rolesCancion = new HashMap<String,Integer>();
        rolesCancion.put("guitarra", 1);
        Cancion cancion = new Cancion("Rock", rolesCancion);

        List<Contratacion> contrataciones = new ArrayList<Contratacion>();
        // añadir un contrato directo del artista base para la cancion
        Contratacion contratoBase = Contratacion.contratarArtistaRolDirecto(cancion, base, "guitarra", 0);
        contrataciones.add(contratoBase);

        List<ArtistaBase> listaBases = Arrays.asList(base);

        assertTrue(contratador.evaluarDescuento(artista, cancion, listaBases, contrataciones));

        // si no hay contratos previos de base -> false
        assertFalse(contratador.evaluarDescuento(artista, cancion, listaBases, new ArrayList<Contratacion>()));
    }

    @Test
    void comandoContratarArtistasParaCancionMuestraMensajeCuandoNoHayRoles() {
        // cargador que devuelve una cancion sin roles
        CargadorDeArchivos cargador = new CargadorDeArchivos() {
            @Override
            public List<Cancion> cargarArchivoRecital() {
                HashMap<String,Integer> roles = new HashMap<String,Integer>();
                Cancion c = new Cancion("EmptySong", roles);
                return Arrays.asList(c);
            }

            @Override
            public List<ArtistaContratable> cargarArchivoArtistas() {
                return new ArrayList<ArtistaContratable>();
            }

            @Override
            public List<ArtistaBase> cargarArchivoArtistasBase(List<ArtistaContratable> repertorio) {
                return new ArrayList<ArtistaBase>();
            }
        };

        Recital recital = new Recital(cargador);

        // preparar entrada: primer nextLine que descarta, luego el nombre de la cancion
        java.util.Scanner scanner = new java.util.Scanner("\nEmptySong\n");

        ContratarArtistasParaCancionComando comando = new ContratarArtistasParaCancionComando(scanner, recital);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(baos));

        comando.ejecutar();

        System.out.flush();
        System.setOut(originalOut);

        String salida = baos.toString();
        assertTrue(salida.contains("La cancion ya tiene todos sus roles cubiertos."));
    }

}
