package com.grupobeta.ArmadoDeRecital;

public class SalirComando implements Comando{
	
	Menu menu;
	
	public SalirComando(Menu menu) {
		this.menu = menu;
	}
	
	@Override
	public void ejecutar() {
		menu.setEstaCorriendo(false);
		System.out.println("\nGuardando estado antes de salir...");
		//ManejadorSalida manejador = new ManejadorSalida();
		//manejador.guardarEstado(this.recital);
	}
}
