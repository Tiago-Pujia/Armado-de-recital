package com.grupobeta.ArmadoDeRecital;

//import java.util.Collection;
import java.util.Objects;

public class Contratacion implements Comparable<Contratacion>{
	
	private Cancion cancion;
	private Artista artista; /// 1 contrato contempla 1 artista con 1 cancion en 1 rol
	private String rol;
	private double costo = 0;
	private boolean descuento;
	
	private Contratacion(Cancion c, Artista a, String r, boolean hayDescuento) {
		this.cancion = c;
		this.artista = a;
		this.rol = r;
		this.descuento = hayDescuento;
		if(hayDescuento) {
			this.costo = a.getCosto()*ArtistaContratable.DESCUENTO_POR_COMPARTIR_BANDA;
		}
		else {
			this.costo = a.getCosto();
		}
	}
	
	private Contratacion(Cancion c, Artista a, String r, double costo) {
		this.cancion = c;
		this.artista = a;
		this.rol = r;
		this.costo = costo;
	}
	
	public static Contratacion contratarArtistaRol(Cancion cancion, Artista artista, String rol, boolean hayDescuento) {
		
		if(!artista.getRoles().contains(rol)) {
			throw new IllegalArgumentException("El artista" + artista.getNombre() + " no cuenta con el rol " + rol + " que estás intentando asignarle para la cancion " + cancion.getTitulo());
		}
		return new Contratacion(cancion, artista, rol, hayDescuento);
	}
	
	public static Contratacion contratarArtistaRolDirecto(Cancion cancion, Artista artista, String rol, double costo) {
		return new Contratacion(cancion, artista, rol, costo);
	}
	
	
	public double getCosto() {
		return this.costo;
	}
	
	public Cancion getCancion() {
		return cancion;
	}

	public Artista getArtista() {
		return artista;
	}

	public String getRol() {
		return rol;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(artista, cancion, rol);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contratacion other = (Contratacion) obj;
		return Objects.equals(artista, other.artista) && Objects.equals(cancion, other.cancion)
				&& Objects.equals(rol, other.rol);
	}

	@Override
	public int compareTo(Contratacion o) {
		return this.artista.compareTo(o.getArtista());
	}

	public boolean getDescuento() {
		// TODO Auto-generated method stub
		return this.descuento;
	}
}
