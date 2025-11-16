artista_base('Brian May').
artista_base('Roger Taylor').
artista_base('John Deacon').

artista_cubre('Brian May', 'guitarra electrica').
artista_cubre('Brian May', 'voz secundaria').
artista_cubre('Roger Taylor', 'bateria').
artista_cubre('John Deacon', 'bajo').

max_canciones_base('Brian May', 100).
max_canciones_base('Roger Taylor', 100).
max_canciones_base('John Deacon', 100).

artista_candidato('ArtistaE1').
artista_candidato('ArtistaE2').
artista_candidato('ArtistaE3').

max_canciones_candidato('ArtistaE1', 3).
max_canciones_candidato('ArtistaE2', 3).
max_canciones_candidato('ArtistaE3', 3).

cancion(1, 'Somebody to Love').
cancion(2, 'We Will Rock You').
cancion(3, 'These Are the Days of Our Lives').
cancion(4, 'Under Pressure').

rol_requerido(1, 1, 'voz principal').
rol_requerido(1, 2, 'guitarra electrica').
rol_requerido(1, 3, 'bajo').
rol_requerido(1, 4, 'piano').

% --- Set 2 (Roles r5-r8) ---
rol_requerido(2, 5, 'voz principal').
rol_requerido(2, 6, 'guitarra electrica').
rol_requerido(2, 7, 'bajo').
rol_requerido(2, 8, 'bateria').

% --- Set 3 (Roles r9-r12) ---
rol_requerido(3, 9, 'voz principal').
rol_requerido(3, 10, 'guitarra acustica').
rol_requerido(3, 11, 'bajo').
rol_requerido(3, 12, 'percusion').

% --- Set 4 (Roles r13-r16, completando tus dos últimos roles) ---
rol_requerido(4, 13, 'guitarra electrica').
rol_requerido(4, 14, 'bajo').
rol_requerido(4, 15, 'voz secundaria'). 
rol_requerido(4, 16, 'percusion').


% ===== PREDICADOS AUXILIARES =====

%Dios nos ayude esto fue lo mas feo que ([{-_<**"hice"**>_-}]) en mi vida.

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

% Asignar roles a artistas (bases primero, luego candidatos)
asignar_roles(Asignaciones) :-
    findall([IdCancion, IdRol, TipoRol], 
            rol_requerido(IdCancion, IdRol, TipoRol), 
            TodosRolesRequeridos),
    asignar_roles_helper(TodosRolesRequeridos, [], Asignaciones).

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
artista_ocupado_en_cancion(Artista, IdCancion, Asignaciones) :-
    member(asignacion(IdCancion, _, _, _, Artista), Asignaciones).

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
