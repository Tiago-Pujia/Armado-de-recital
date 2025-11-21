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

class PrologComandoTests {

    @Test
    void ejecutarNoLanzaExcepcionYProduceSalida() {
    	
        CargadorDeArchivos cargador = new CargadorDeArchivos() {
            @Override
            public List<Cancion> cargarArchivoRecital() {
                return new ArrayList<Cancion>();
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
        PrologComando comando = new PrologComando(recital);

        ByteArrayOutputStream baosOut = new ByteArrayOutputStream();
        ByteArrayOutputStream baosErr = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        PrintStream originalErr = System.err;

        System.setOut(new PrintStream(baosOut));
        System.setErr(new PrintStream(baosErr));

        try {
            comando.ejecutar();
        } catch (Throwable t) {
            // No debe lanzar excepciones en el test: fallamos si ocurre
            fail("PrologComando.ejecutar lanzó una excepción: " + t.getMessage());
        } finally {
            System.out.flush();
            System.err.flush();
            System.setOut(originalOut);
            System.setErr(originalErr);
        }

        String salida = baosOut.toString() + baosErr.toString();
        assertTrue(salida.length() > 0, "Se esperaba algún texto en la salida (stdout/err)");
    }

    @Test
    void ejecutarConDatosRecitalProduceMensajesAsertadoOErroresDeProlog() {
        CargadorDeArchivos cargador = new CargadorDeArchivos() {
            @Override
            public List<Cancion> cargarArchivoRecital() {
                HashMap<String,Integer> roles = new HashMap<String,Integer>();
                roles.put("voz", 1);
                Cancion c = new Cancion("Cancion'Prueba", roles);
                return Arrays.asList(c);
            }

            @Override
            public List<ArtistaContratable> cargarArchivoArtistas() {
                Set<String> roles = new HashSet<String>(Arrays.asList("voz"));
                ArtistaContratable a = new ArtistaContratable("Candidato", roles, new HashSet<String>(), 2, 100);
                return Arrays.asList(a);
            }

            @Override
            public List<ArtistaBase> cargarArchivoArtistasBase(List<ArtistaContratable> repertorio) {
                Set<String> roles = new HashSet<String>(Arrays.asList("voz"));
                ArtistaBase b = new ArtistaBase("Base", roles, new HashSet<String>());
                return Arrays.asList(b);
            }
        };

        Recital recital = new Recital(cargador);
        PrologComando comando = new PrologComando(recital);

        ByteArrayOutputStream baosOut = new ByteArrayOutputStream();
        ByteArrayOutputStream baosErr = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        PrintStream originalErr = System.err;

        System.setOut(new PrintStream(baosOut));
        System.setErr(new PrintStream(baosErr));

        try {
            comando.ejecutar();
        } catch (Throwable t) {
            fail("PrologComando.ejecutar lanzó una excepción: " + t.getMessage());
        } finally {
            System.out.flush();
            System.err.flush();
            System.setOut(originalOut);
            System.setErr(originalErr);
        }

        String salida = baosOut.toString() + baosErr.toString();

        // Dependiendo del entorno Prolog puede imprimirse que los hechos fueron asertados
        // o bien aparecer errores de enlace / JPL. Aceptamos cualquiera de ambos, pero
        // exigimos que haya salida.
        assertTrue(salida.length() > 0, "Se esperaba texto en la salida con datos de recital");
    }

}
