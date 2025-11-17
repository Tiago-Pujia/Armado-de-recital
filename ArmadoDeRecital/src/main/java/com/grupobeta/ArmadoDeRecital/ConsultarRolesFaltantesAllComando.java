package com.grupobeta.ArmadoDeRecital;

public class ConsultarRolesFaltantesAllComando implements Comando{
	
	private Recital recital;

	public ConsultarRolesFaltantesAllComando(Recital recital) {
		this.recital = recital;
	}
	
	
	///punto 2
	@Override
	public void ejecutar() {
		int i = 1;
		System.out.println("Listado de " + Menu.ANSI_YELLOW + "canciones" + Menu.ANSI_RESET + " del recital. Se muestra para cada una de ellas los roles aún no cubiertos.\n");
		for(Cancion cancion : this.recital.getCanciones()) {
			if(cancion.getRolesRequeridos().isEmpty()) {
				System.out.println(i + ". " + Menu.ANSI_PURPLE + cancion.getTitulo() + Menu.ANSI_CYAN + "\nNo quedan roles por cubrir !\n" + Menu.ANSI_RESET);
			}
			else {
				System.out.println(i + ". " + Menu.ANSI_PURPLE + cancion.getTitulo() 
				+ Menu.ANSI_RESET + "\nRoles faltantes por cubrir:\n" + Menu.ANSI_RED + cancion.getRolesRequeridos() + Menu.ANSI_RESET + "\n");
			}
			i++;
		}
	}	
}

