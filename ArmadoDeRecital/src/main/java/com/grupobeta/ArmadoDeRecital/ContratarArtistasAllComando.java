package com.grupobeta.ArmadoDeRecital;

import java.util.ArrayList;

public class ContratarArtistasAllComando implements Comando{
	
	private Recital recital;

	public ContratarArtistasAllComando(Recital recital) {
		this.recital = recital;
	}
	
	//punto 4
	@Override
	public void ejecutar() {

		for(Cancion cancion : recital.getCanciones()) {
			
			ArrayList<Contratacion> contratosRealizados = recital.contratarArtistasParaCancion(cancion);
	
			ContratarArtistasParaCancionComando.mostrarContratosRealizados(contratosRealizados);
			if(!cancion.getRolesRequeridos().isEmpty()) {
				System.out.println("No se pudieron cubrir todos los roles de la cancion " + Menu.ANSI_PURPLE + cancion.getTitulo() + Menu.ANSI_RESET + " ¿Deseas entrenar a un artista?"  );
			}
			else {
				if(contratosRealizados.isEmpty()) {
					System.out.println("La cancion " + Menu.ANSI_PURPLE + cancion.getTitulo() + Menu.ANSI_GREEN +" tiene todos sus roles cubiertos" + Menu.ANSI_RESET);
				}
			}
		}
	}
}
