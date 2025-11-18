package com.grupobeta.ArmadoDeRecital;

import java.util.Set;

public class ArtistaBase extends Artista{

	public ArtistaBase(String name, Set<String> roles, Set<String> historial) {
		super(name, roles, historial, 0);
	}

	@Override
	public boolean esContratable() {
		return true;
	}
}
