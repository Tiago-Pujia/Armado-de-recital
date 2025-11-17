% ===== PREDICADOS AUXILIARES =====

% Obtener todos los roles únicos requeridos en todo el recital
roles_unicos_recital(RolesUnicos) :-
    findall(Rol, rol_requerido(_, _, Rol), TodosLosRoles),
    list_to_set(TodosLosRoles, RolesUnicos).

% Verificar si un artista base puede cubrir un rol
base_puede_cubrir(Artista, Rol) :-
    artista_base(Artista),
    artista_cubre(Artista, Rol).

% Obtener roles que ningún artista base puede cubrir
roles_no_cubiertos_por_bases(RolesNoCubiertos) :-
    roles_unicos_recital(TodosRoles),
    findall(Rol, 
            (member(Rol, TodosRoles), 
             \+ base_puede_cubrir(_, Rol)),
            RolesNoCubiertos).

% Obtener roles que aún necesitan asignación
% Todos los roles en rol_requerido/3 necesitan ser asignados
% (las contrataciones previas solo bloquean artistas, no roles específicos)
roles_por_asignar(RolesPorAsignar) :-
    findall([IdCancion, IdRol, TipoRol], 
            rol_requerido(IdCancion, IdRol, TipoRol), 
            RolesPorAsignar).

% Asignar roles a artistas (bases primero, luego candidatos)
% Las contrataciones previas bloquean artistas en canciones específicas
asignar_roles(Asignaciones) :-
    roles_por_asignar(RolesPorAsignar),
    asignar_roles_helper(RolesPorAsignar, [], Asignaciones).

% Caso base: no hay más roles por asignar
asignar_roles_helper([], Asignaciones, Asignaciones).

% Caso recursivo: asignar un rol
asignar_roles_helper([[IdCancion, IdRol, TipoRol]|Resto], AsignacionesPrevias, AsignacionesFinales) :-
    % Intentar asignar a un artista base disponible
    (   seleccionar_artista_base(IdCancion, TipoRol, AsignacionesPrevias, ArtistaBase)
    ->  NuevaAsignacion = asignacion(IdCancion, IdRol, TipoRol, base, ArtistaBase)
    ;   % Si no hay base disponible, asignar a candidato
        seleccionar_candidato(IdCancion, TipoRol, AsignacionesPrevias, Candidato),
        NuevaAsignacion = asignacion(IdCancion, IdRol, TipoRol, candidato, Candidato)
    ),
    asignar_roles_helper(Resto, [NuevaAsignacion|AsignacionesPrevias], AsignacionesFinales).

% Seleccionar un artista base que pueda cubrir el rol y no esté ocupado en esa canción
seleccionar_artista_base(IdCancion, TipoRol, AsignacionesPrevias, Artista) :-
    base_puede_cubrir(Artista, TipoRol),
    \+ artista_ocupado_en_cancion(Artista, IdCancion, AsignacionesPrevias).

% Verificar si un artista ya está ocupado en una canción específica
% Ahora también considera las contrataciones previas
artista_ocupado_en_cancion(Artista, IdCancion, Asignaciones) :-
    (   member(asignacion(IdCancion, _, _, _, Artista), Asignaciones)
    ;   artista_ya_contratado(Artista, IdCancion)
    ).

% Seleccionar un candidato que no esté ocupado en esa canción
% y que no exceda su límite de canciones
seleccionar_candidato(IdCancion, _TipoRol, AsignacionesPrevias, Candidato) :-
    artista_candidato(Candidato),
    \+ artista_ocupado_en_cancion(Candidato, IdCancion, AsignacionesPrevias),
    max_canciones_candidato(Candidato, MaxCanciones),
    findall(C, member(asignacion(C, _, _, candidato, Candidato), AsignacionesPrevias), CancionesAsignadas),
    list_to_set(CancionesAsignadas, CancionesUnicas),
    length(CancionesUnicas, NumCanciones),
    NumCanciones < MaxCanciones.

% Contar entrenamientos únicos necesarios
contar_entrenamientos(Asignaciones, NumEntrenamientos, Entrenamientos) :-
    findall(entrenamiento(Candidato, TipoRol),
            member(asignacion(_, _, TipoRol, candidato, Candidato), Asignaciones),
            TodosEntrenamientos),
    list_to_set(TodosEntrenamientos, Entrenamientos),
    length(Entrenamientos, NumEntrenamientos).

% ===== PREDICADO PRINCIPAL =====
% Calcula el número mínimo de entrenamientos necesarios
entrenamientos_minimos(NumEntrenamientos, Entrenamientos, Asignaciones) :-
    asignar_roles(Asignaciones),
    contar_entrenamientos(Asignaciones, NumEntrenamientos, Entrenamientos).

% ===== CONSULTAS DE EJEMPLO =====
% Para ejecutar:
% ?- entrenamientos_minimos(Num, Entrenamientos, Asignaciones).
% ?- entrenamientos_minimos(Num, _, _).