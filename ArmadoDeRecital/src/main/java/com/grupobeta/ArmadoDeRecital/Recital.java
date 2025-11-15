package com.grupobeta.ArmadoDeRecital;

import java.util.ArrayList;
import java.util.HashMap;

public class Recital {
	
	ArrayList<Cancion> canciones = null; 
	ArrayList<Contratacion> contrataciones = null;
	ArrayList<ArtistaContratado> artistasContratables = null;
	ArrayList<ArtistaBase> artistasBase = null;
	Contratador contratador = null;
	
	public Recital(CargadorDeArchivos cargador) {
		this.canciones = cargador.cargarArchivoRecital();
		this.artistasContratables = cargador.cargarArchivoArtistas();
		this.artistasBase = cargador.cargarArchivoArtistasBase(artistasContratables);
		this.contrataciones = new ArrayList<Contratacion>();
		this.contratador = new Contratador();
		this.agregarArtistasBase();
		this.aplicarDescuentos();
	}
	
	///agregar artistas base
	/// recorro la lista de canciones y obtengo su lista de roles
		///recorro la lista de roles faltantes y busco en el repertorio un artista base (me fijo que el nombre del artista esté en la lista de artistas base)
			///si lo encuentro, me fijo si en la lista de contrataciones ya está contratado para esa misma cancion
				///si lo está, paso al siguiente artista.
				//si no lo está, me fijo si tiene el rol
					//lo tiene? lo contrato
						//creo un contrato para ese artista y ese rol, le resto 1 a la cantidad de ese rol en el hashmap de roles de la cancion y agrego el contrato a la lista de contratos
							//si el hashmap tiene 0 para ese rol, elimino esa entrada del map
						//sigo con otro rol 
					//no lo tiene? sigo con otro artista
	
	private void agregarArtistasBase() { //contratar all es basicamente esto pero contratando a mansalva a todos sin chequear que sea base o no, simplemente contrata
		
		//se me ocurre remover temporalmente al artista ya contratado del repertorio para que no pregunte 
		//por él varias veces para una misma canción. Después lo agregamos otra vez al finalizar
		ArrayList<ArtistaBase> artistasContratadosParaCancionTemp = new ArrayList<ArtistaBase>(); 
		
		for(Cancion cancion : canciones) { 
			
			///si lo hago con foreach, al hacer remove tenemos problemas de concurrencia
			for(int i = 0; i< artistasBase.size() ; i++) {
				ArtistaBase artista = artistasBase.get(i);
				if(contratador.artistaEsContratableParaCancion(this.contrataciones, artista, cancion)) {
					this.contrataciones.add(this.contratador.contratarArtistaParaUnRolCualquieraEnCancion(artista, cancion));
					artistasContratadosParaCancionTemp.add(artista);
					this.artistasBase.remove(artista);
					i--;
				}
							
			}
			
		}
		this.artistasBase.addAll(artistasContratadosParaCancionTemp); 
	}
	
	private void aplicarDescuentos() {
		for(ArtistaContratado artista : this.artistasContratables) {
			if(this.contratador.artistaCompartioBandaConBase(artista, artistasBase)) {
				artista.reducirCostoContratacion();
			}
		}
	}
	
	///punto 1
	public HashMap<String, Integer> consultarRolesFaltantesParaCancion(Cancion cancion) {
						
		if(cancion == null){
			return null;
		}
		
		return cancion.getRolesRequeridos();
	}
	
	///punto 2
	public ArrayList<Cancion> getCanciones() {
		return this.canciones;		
	}
	
	///punto 3
	///	/// 	si está, obtenemos su lista de roles faltantes ✓
	///			si la lista está vacía, return "YA TIENE SUS ROLES CUBIERTOS" ✓
	///			si la lista NO está vacía, empezar a iterar sobre la lista de artistas contratables. Marcamos el precio del primer artista como el mínimo. ✓
	///			Luego, preguntamos si artista tiene ALGUN rol necesario para la canción ✓
	///				si tiene al menos un rol, buscamos si compartió banda con algún artista base.
	///					si compartió banda, guardamos en una variable cómo va a quedar el costo del artista con descuento (en caso de ser contratado)
	///					si no compartió banda, guardamos en una variable su costo base
	///					Luego, comparamos el precio con el del mínimo. 
	///						Si es menor al mínimo, tomamos a ese artista como nuevo mínimo.  
	///						Si es mayor al mínimo, no hacemos nada
	///				si no tiene ningún rol, no hacemos nada
	///			Seguimos con el siguiente artista
	/// 	si no está, return "ERROR, la cancion ingresada no existe"
	/// Retornamos el artista de menor costo Y LO CONTRATAMOS
	
	public CodigoDeRetorno contratarArtistasParaCancion(Cancion cancion) {
		
		boolean primeraVuelta = true, huboCambio = false;
		Artista artistaCostoMin = null;
		HashMap<String, Integer> roles = cancion.getRolesRequeridos();
		ArrayList<ArtistaContratado> artistasContratadosParaCancionTemp = new ArrayList<ArtistaContratado>(); 
		
		if(roles.isEmpty()) {
			return CodigoDeRetorno.YA_TIENE_LOS_ROLES_CUBIERTOS; // ya tiene los roles cubiertos
		}
		
		///falta un bucle externo donde iteramos por cada rol y lo llenamos (de ser posible) usando el for este de acá abajo
		
		for( String rol : cancion.getRolesRequeridos().keySet() ) {
			
			for(int i = 0; i < this.artistasContratables.size() ; i++) {
				
				Artista artista = this.artistasContratables.get(i);
				
				if(this.contratador.artistaEsContratableParaCancionRol(contrataciones, artista, cancion, rol)) {
					
					if(primeraVuelta) {
						artistaCostoMin = artista;
						primeraVuelta = false;
					}
					if(artista.getCosto() < artistaCostoMin.getCosto()) {
						artistaCostoMin = artista;
						huboCambio = true;
					}
				}
			}
						
			if(huboCambio) {
				huboCambio = false;
				primeraVuelta = true;
				contratador.contratarArtistaParaUnRolEnCancion(artistaCostoMin, cancion, rol);
				this.artistasContratables.remove(artistaCostoMin);
			}
		}
		
		
		this.artistasContratables.addAll(artistasContratadosParaCancionTemp);
		
		if(!cancion.getRolesRequeridos().isEmpty()) {
			return CodigoDeRetorno.NO_SE_PUEDEN_CUBRIR_TODOS_LOS_ROLES;
		}
		
		return CodigoDeRetorno.ARTISTA_CONTRATADO;
	}
	
	///punto 4
	public void contratarArtistasAll() {
		
	}
	
	///punto 5
	public void entrenarArtista() {
		
	}
	
	///punto 6
	public ArrayList<Contratacion> listarArtistasContratados() {
		this.contrataciones.sort(null);
		return this.contrataciones;
	}
	
	public double obtenerCostoContratacionesCancion(String nombreCancion) {
		
		double costoCancion = 0;;
		
		for(Contratacion con : contrataciones) {
			if(con.getCancion().getTitulo().toLowerCase().equals(nombreCancion.toLowerCase())) {
				costoCancion += con.getCosto();
			}
		}
		
		return costoCancion;
	}
	
	public double obtenerCostoTotal() {
		
		double costoTotal = 0;
		
		for(Contratacion con : contrataciones) {
			costoTotal += con.getCosto();
		}
		
		return costoTotal;
	}

	public Cancion buscarCancion(String ncanc) {
		for(Cancion c : this.canciones) {
			if(c.getTitulo().toLowerCase().equals(ncanc.toLowerCase())) {
				return c;
			}
		}
		return null;
	}
}
