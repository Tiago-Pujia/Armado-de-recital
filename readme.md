- [Documentación de las Funcionalidades del Sistema de Gestión de Recital](#documentación-de-las-funcionalidades-del-sistema-de-gestión-de-recital)
  - [Funcionalidad 1: Roles Faltantes para una Canción Específica](#funcionalidad-1-roles-faltantes-para-una-canción-específica)
  - [Funcionalidad 2: Roles Faltantes para Todo el Recital](#funcionalidad-2-roles-faltantes-para-todo-el-recital)
  - [Funcionalidad 3: Contratar Artistas para una Canción Específica](#funcionalidad-3-contratar-artistas-para-una-canción-específica)
  - [Funcionalidad 4: Contratar Artistas para Todas las Canciones Simultáneamente](#funcionalidad-4-contratar-artistas-para-todas-las-canciones-simultáneamente)
  - [Funcionalidad 5: Entrenar a un Artista](#funcionalidad-5-entrenar-a-un-artista)
  - [Funcionalidad 6: Listar Artistas Contratados](#funcionalidad-6-listar-artistas-contratados)
  - [Funcionalidad 7: Listar Canciones con Estado](#funcionalidad-7-listar-canciones-con-estado)
  - [Funcionalidad 8: Integración con Prolog - Entrenamientos Mínimos](#funcionalidad-8-integración-con-prolog---entrenamientos-mínimos)
  - [Funcionalidad 9: Quitar un Artista del Recital](#funcionalidad-9-quitar-un-artista-del-recital)
  - [Funcionalidad 10: Cargar un Estado Previo](#funcionalidad-10-cargar-estado-previo)
  - [Funcionalidad 11: Mostrar Grafo de Colaboraciones](#funcionalidad-11-mostrar-grafo-de-colaboraciones)
  - [Funcionalidad 12: Salir del Sistema](#funcionalidad-12-salir-del-sistema)

# Documentación de las Funcionalidades del Sistema de Gestión de Recital

## Funcionalidad 1: Roles Faltantes para una Canción Específica

Identificar qué roles y en qué cantidad no están cubiertos para una canción particular del recital.

1. El sistema localiza la canción específica dentro del recital
2. Obtiene todos los roles requeridos para esa canción (incluyendo duplicados si se necesitan múltiples intérpretes del mismo rol)
3. Consulta la contratación existente para esa canción para ver qué artistas ya han sido asignados y a qué roles
4. Compara los roles requeridos con los roles ya cubiertos
5. Calcula la diferencia para determinar qué roles faltan y en qué cantidad
6. Devuelve un resumen indicando, por ejemplo: "faltan 1 voz principal y 2 coros"

## Funcionalidad 2: Roles Faltantes para Todo el Recital

Obtener una visión global de todos los roles no cubiertos across todas las canciones del recital.

1. El sistema recorre cada canción del recital
2. Para cada canción, calcula los roles faltantes (similar a la funcionalidad 1)
3. Agrega estos resultados sumando las cantidades por tipo de rol
4. Genera un reporte consolidado que muestra el total de cada rol que falta en todo el recital 
5. Ejemplo: "En total faltan: 3 voces principales, 2 guitarras eléctricas, 1 batería"

## Funcionalidad 3: Contratar Artistas para una Canción Específica

Asignar automáticamente artistas a los roles faltantes de una canción, optimizando el costo.

1. Identifica los roles faltantes específicos de la canción
2. Busca entre los artistas disponibles aquellos que puedan desempeñar los roles necesarios
3. Prioriza artistas base (costo cero) siempre que sea posible
4. Para artistas externos, aplica el algoritmo de minimización de costos:
   1. Considera el costo base de cada artista
   2. Aplica descuento del 50% si el artista comparte historial de bandas con algún artista base
   3. Selecciona la combinación más económica que cubra todos los roles
5. Realiza las asignaciones y actualiza el estado de la contratación

Restricciones:

Un artista solo puede ser asignado a roles que figuren en su historial

Se respeta el límite máximo de canciones por artista externo

## Funcionalidad 4: Contratar Artistas para Todas las Canciones Simultáneamente

Realizar una contratación global optimizada para todo el recital, considerando interdependencias.

1. Analiza todas las canciones del recital simultáneamente
2. Identifica todos los roles faltantes a nivel global
3. Considera las limitaciones de los artistas externos (máximo de canciones)
4. Aplica una estrategia de optimización que:
   1. Maximiza el uso de artistas base
   2. Asigna artistas externos de manera que se minimice el costo total
   3. Aprovecha descuentos por historial compartido con artistas base
   4. Respeta los límites de participación de cada artista
5. Las canciones que ya tenían contrataciones previas se mantienen sin cambios

## Funcionalidad 5: Entrenar a un Artista

Permitir que un artista externo adquiera un nuevo rol, expandiendo sus capacidades.

1. Se selecciona un artista externo no contratado (no puede ser artista base)
2. Se verifica que el artista no tenga ya el rol que se quiere agregar
3. Se añade el nuevo rol al conjunto de habilidades del artista
4. Incremento de costo: El costo base del artista aumenta en un 50% por cada rol adicional adquirido mediante entrenamiento
5. El artista queda disponible para ser asignado a ese rol en futuras contrataciones

Restricciones Estrictas:

- No se puede entrenar artistas base
- No se puede entrenar artistas ya contratados para alguna canción
- El entrenamiento es permanente y afecta el costo futuro del artista

## Funcionalidad 6: Listar Artistas Contratados

Proporcionar un reporte completo de todos los artistas externos contratados y su información relevante.

Es tan simple como recorrer una lista e imprimir la información del mismo

Información Incluida:
- Nombre del artista
- Roles asignados en el recital
- Cantidad de canciones en las que participa
- Costo total del artista en el recital
- Historial de bandas (para verificar posibles descuentos)

## Funcionalidad 7: Listar Canciones con Estado

Mostrar el estado de preparación de cada canción del recital.

Información por Canción:

- Nombre de la canción

- Estado (COMPLETA/INCOMPLETA) basado en si todos los roles están cubiertos

- Lista de roles requeridos

- Roles actualmente cubiertos (y por quiénes)

- Roles faltantes (si aplica)

- Costo total de artistas externos asignados a esa canción

Visualización: Proporciona un dashboard rápido del progreso del recital.

## Funcionalidad 8: Integración con Prolog - Entrenamientos Mínimos

Calcular el número mínimo de entrenamientos necesarios para cubrir todos los roles usando solo recursos internos.

1. Identifica todos los roles únicos requeridos en todo el recital
2. Determina qué roles pueden ser cubiertos actualmente por artistas base
3. Identifica la brecha: roles que no pueden ser cubiertos por nadie actualmente
4. Calcula cuántos entrenamientos se necesitarían para que los artistas base y externos disponibles puedan cubrir todos los roles
5. Considera que un artista puede ser entrenado en múltiples roles, pero cada entrenamiento tiene un costo incremental

## Funcionalidad 9: Quitar Un Artista del Recital

Para un artista dado, recorre todas sus colaboraciones realizadas y las deshace, devolviéndole a cada canción los roles que ya habían sido tomados y ajustando el costo total.

## Funcionalidad 10: Cargar Estado Previo

Busca un archivo "recital-out.json" y, de encontrarlo, devuelve el Recital a un estado previo, volviendo a cargar todas las contrataciones realizadas, entrenamientos llevados a cabo y actualizando el costo total.

## Funcionalidad 11: Mostrar Grafo de Colaboraciones

Muestra por pantalla un grafo simple de qué artistas ya contratados comparten alguna banda entre sus historiales, de manera tal de poder ver qué artistas tendrán la posibilidad de tener un descuento

## Funcionalidad 12: Salir del Sistema

Finalizar la sesión de manera controlada, preservando el estado del trabajo.

1. Verifica que no hay operaciones pendientes
2. Bonus: Genera archivo de salida con el estado actual del recital. El archivo incluye:
   1. Todas las canciones con sus estados de contratación
   2. Artistas contratados y sus asignaciones
   3. Costos totales y desglosados
   4. Roles faltantes pendientes
3. Cierra la aplicación de manera segura