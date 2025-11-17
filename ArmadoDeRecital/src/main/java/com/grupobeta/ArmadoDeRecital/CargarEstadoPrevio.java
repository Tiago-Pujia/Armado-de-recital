package com.grupobeta.ArmadoDeRecital;

public class CargarEstadoPrevio implements Comando {
	
	private Menu menu;
	private Recital recital;
	private CargadorDeArchivos cargador;
	
	public CargarEstadoPrevio(Menu menu, Recital recital, CargadorDeArchivos cargador) {
		this.menu = menu;
		this.recital = recital;
		this.cargador = cargador;
	}
	
	@Override
	public void ejecutar() {
		
		
		
	}

}
