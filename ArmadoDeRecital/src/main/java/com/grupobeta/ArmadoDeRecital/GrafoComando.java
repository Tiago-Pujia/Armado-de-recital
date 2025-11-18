package com.grupobeta.ArmadoDeRecital;

import java.util.ArrayList;

public class GrafoComando implements Comando{
	
	public Recital recital;
	public GrafoComando(Recital recital) {
		this.recital = recital;
	}
	@Override
	public void ejecutar() {
		ArrayList<Artista> repertorio = new ArrayList<Artista>(this.recital.getArtistasBase());
		repertorio.addAll(this.recital.getArtistasContratables());
		GrafoColaboraciones grafo = new GrafoColaboraciones(repertorio);
		System.out.println(grafo.mostrarGrafoDetallado());
	}
}