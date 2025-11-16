package com.grupobeta.ArmadoDeRecital;

import java.util.ArrayList;

public class ListarCancionesComando implements Comando{
	
	Recital recital = null;
	
	public ListarCancionesComando(Recital recital) {
		this.recital = recital;
	}
	
	//punto 7
	@Override
	public void ejecutar() {
		int i = 1;
		double costoTotal = 0, costoCancion;
		
		for(Cancion cancion : recital.getCanciones()) {
			ArrayList<Contratacion> contratos = new ArrayList<Contratacion>();
			costoCancion = recital.obtenerCostoContratacionesYContratosCancion(cancion, contratos);
			System.out.println(Menu.ANSI_YELLOW + i + ". " + Menu.ANSI_PURPLE + cancion.getTitulo() + Menu.ANSI_GREEN + " - Costo: $" + costoCancion + Menu.ANSI_RESET);
			i++;
			costoTotal += costoCancion;
			for(Contratacion contrato : contratos) {
				System.out.println(contrato.getArtista().getNombre() + " - " + contrato.getRol());
			}
			if(cancion.getRolesRequeridos().isEmpty()) {
				System.out.println(Menu.ANSI_CYAN + "No quedan roles por cubrir !\n" + Menu.ANSI_RESET);
			}
			else {
				System.out.println("Roles faltantes por cubrir:\n" + Menu.ANSI_RED + cancion.getRolesRequeridos() + Menu.ANSI_RESET + "\n");		
			}
		}
		System.out.println("Costo total del recital: " + Menu.ANSI_GREEN +costoTotal + Menu.ANSI_RESET);		
	}
}
