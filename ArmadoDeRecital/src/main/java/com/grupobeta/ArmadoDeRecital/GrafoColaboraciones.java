package com.grupobeta.ArmadoDeRecital;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;

/**
 * Bonus: Grafo de colaboraciones entre artistas
 * Muestra las relaciones entre artistas según bandas compartidas
 */
public class GrafoColaboraciones {
    private Set<Artista> artistas;
    
    //Solo quiero Artistas activos, entonces busco los artistas de las contrataciones
    public GrafoColaboraciones(List<Contratacion> contrataciones) {
        this.artistas = new HashSet<>();
        for(Contratacion contrato : contrataciones) {
        	this.artistas.add(contrato.getArtista());
        }
    }
     
    public String mostrarGrafoDetallado() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== GRAFO DETALLADO DE COLABORACIONES ===\n\n");
        
        Map<String, Map<String, List<String>>> detalle = new HashMap<>();
        
        // Construir mapa detallado
        for (Artista artista1 : artistas) {
            for (Artista artista2 : artistas) {
            	if (artista1.getNombre().compareTo(artista2.getNombre()) >= 0) {
                    continue; 
                }
                // Encontrar bandas compartidas
                List<String> bandasCompartidas = new ArrayList<>();
                for (String banda : artista1.getHistorial()) {
                    if (artista2.getHistorial().contains(banda)) {
                        bandasCompartidas.add(Menu.ANSI_GREEN + banda + Menu.ANSI_RESET );
                    }
                }
                
                if (!bandasCompartidas.isEmpty()) {
                    String key =  Menu.ANSI_CYAN + artista1.getNombre() + Menu.ANSI_RESET + " ↔ " +  Menu.ANSI_YELLOW + artista2.getNombre() + Menu.ANSI_RESET;
                    detalle.put(key, new HashMap<>());
                    detalle.get(key).put("bandas", bandasCompartidas);
                }
            }
        }
        
        // Mostrar
        for (Map.Entry<String, Map<String, List<String>>> entry : detalle.entrySet()) {
            sb.append(entry.getKey()).append("\n");
            sb.append("  Bandas compartidas: ");
            List<String> bandas = entry.getValue().get("bandas");
            for (int i = 0; i < bandas.size(); i++) {
                sb.append(bandas.get(i));
                if (i < bandas.size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append("\n\n");
        }
        
        return sb.toString();
    }
}