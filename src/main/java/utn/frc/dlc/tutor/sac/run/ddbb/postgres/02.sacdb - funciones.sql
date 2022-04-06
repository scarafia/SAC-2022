-- =============================================================================
--
--    U N I V E R S I D A D   T E C N O L Ó G I C A   N A C I O N A L
--    F A C U L T A D   R E G I O N A L   C Ó R D O B A
--
--    D I S E Ñ O   D E   L E N G U A J E S   D E   C O N S U L T A   ( D L C )
--    P R Á C T I C O   T U T O R:
--        S I S T E M A   D E
--        A D M I N I S T R A C I Ó N   D E   C U R S O S   ( S A C )
--
--    F U N C I O N E S / P R O C E D I M I E N T O S   B B D D   ( s a c d b )
--        D B M S :   P o s t g r e S Q L
--
--    A U T O R :    S c a r a f i a
--
-- =============================================================================

-- =============================================================================
-- PERSONAS
-- =============================================================================
DROP FUNCTION IF EXISTS fn_savepersona (INTEGER, VARCHAR, VARCHAR, VARCHAR);
CREATE OR REPLACE FUNCTION fn_savepersona (
  pin_pid                           INTEGER,
  pin_dni                           VARCHAR,
  pin_apellido                      VARCHAR,
  pin_nombre                        VARCHAR

) RETURNS INTEGER AS $$

  DECLARE
    var_pid                         INTEGER         := pin_pid;
    var_dni                         VARCHAR         := TRIM(pin_dni);
    var_apellido                    VARCHAR         := TRIM(pin_apellido);
    var_nombre                      VARCHAR         := TRIM(pin_nombre);

  BEGIN
    IF (var_pid IS NULL) THEN
      -- no existe ==> insert
      INSERT INTO persona(dni, apellido, nombre)
        VALUES (var_dni, var_apellido, var_nombre)
        RETURNING pid INTO var_pid;
    ELSE
      -- sí existe ==> update
      UPDATE persona SET
        dni = var_dni,
        apellido = var_apellido,
        nombre = var_nombre
      WHERE pid = var_pid;
    END IF;

    RETURN var_pid;
  END;

$$ LANGUAGE plpgsql;

-- -----------------------------------------------------------------------------
DROP FUNCTION IF EXISTS fn_savepersona (VARCHAR, VARCHAR, VARCHAR);
CREATE OR REPLACE FUNCTION fn_savepersona (
  pin_dni                           VARCHAR,
  pin_apellido                      VARCHAR,
  pin_nombre                        VARCHAR

) RETURNS INTEGER AS $$

  BEGIN
    RETURN fn_savepersona(NULL, pin_dni, pin_apellido, pin_nombre);
  END;

$$ LANGUAGE plpgsql;

-- -----------------------------------------------------------------------------
DROP FUNCTION IF EXISTS pr_deletepersona (INTEGER);
CREATE OR REPLACE FUNCTION pr_deletepersona (
  pin_pid                           INTEGER

) RETURNS VOID AS $$

  BEGIN
    DELETE FROM persona WHERE pid = pin_pid;

    RETURN;
  END;

$$ LANGUAGE plpgsql;

-- =============================================================================
-- ALUMNOS
-- =============================================================================
DROP FUNCTION IF EXISTS pr_savealumno (INTEGER, VARCHAR);
CREATE OR REPLACE FUNCTION pr_savealumno (
  pin_pid                           INTEGER,
  pin_legajo                        VARCHAR

) RETURNS VOID AS $$

  DECLARE
    var_legajo                      VARCHAR         := TRIM(pin_legajo);
    var_count                       INTEGER         := 0;

  BEGIN

    SELECT COUNT(*)
      INTO var_count
      FROM alumno
      WHERE pid = pin_pid;

    IF (var_count = 0) THEN
      -- no existe ==> insert
      INSERT INTO alumno(pid, legajo) VALUES (pin_pid, var_legajo);

    ELSE
      -- sí existe ==> update
      UPDATE alumno SET legajo = var_legajo WHERE pid = pin_pid;
    END IF;

  END;

$$ LANGUAGE plpgsql;

-- -----------------------------------------------------------------------------
DROP FUNCTION IF EXISTS fn_savealumno (INTEGER, VARCHAR, VARCHAR, VARCHAR, VARCHAR);
CREATE OR REPLACE FUNCTION fn_savealumno (
  pin_pid                           INTEGER,
  pin_dni                           VARCHAR,
  pin_apellido                      VARCHAR,
  pin_nombre                        VARCHAR,
  pin_legajo                        VARCHAR

) RETURNS INTEGER AS $$

  DECLARE
    var_pid                         INTEGER;
    var_dni                         VARCHAR         := TRIM(pin_dni);
    var_apellido                    VARCHAR         := TRIM(pin_apellido);
    var_nombre                      VARCHAR         := TRIM(pin_nombre);
    var_legajo                      VARCHAR         := TRIM(pin_legajo);

  BEGIN
    
    var_pid = fn_savepersona(pin_pid, var_dni, var_apellido, var_nombre);

    PERFORM pr_savealumno(var_pid, var_legajo);
    
    RETURN var_pid;
  END;

$$ LANGUAGE plpgsql;

-- -----------------------------------------------------------------------------
DROP FUNCTION IF EXISTS fn_savealumno (VARCHAR, VARCHAR, VARCHAR, VARCHAR);
CREATE OR REPLACE FUNCTION fn_savealumno (
  pin_dni                           VARCHAR,
  pin_apellido                      VARCHAR,
  pin_nombre                        VARCHAR,
  pin_legajo                        VARCHAR

) RETURNS INTEGER AS $$

  BEGIN
    RETURN fn_savealumno(NULL, pin_dni, pin_apellido, pin_nombre, pin_legajo);
  END;

$$ LANGUAGE plpgsql;

-- -----------------------------------------------------------------------------
DROP FUNCTION IF EXISTS pr_deletealumno (INTEGER);
CREATE OR REPLACE FUNCTION pr_deletealumno (
  pin_pid                           INTEGER

) RETURNS VOID AS $$

  BEGIN
    DELETE FROM alumno WHERE pid = pin_pid;

    RETURN;
  END;

$$ LANGUAGE plpgsql;

-- =============================================================================
-- DOCENTES
-- =============================================================================
DROP FUNCTION IF EXISTS pr_savedocente (INTEGER, VARCHAR);
CREATE OR REPLACE FUNCTION pr_savedocente (
  pin_pid                           INTEGER,
  pin_legajo                        VARCHAR

) RETURNS VOID AS $$

  DECLARE
    var_legajo                      VARCHAR         := TRIM(pin_legajo);
    var_count                       INTEGER         := 0;

  BEGIN

    SELECT COUNT(*)
      INTO var_count
      FROM docente
      WHERE pid = pin_pid;

    IF (var_count = 0) THEN
      -- no existe ==> insert
      INSERT INTO docente(pid, legajo) VALUES (pin_pid, var_legajo);

    ELSE
      -- sí existe ==> update
      UPDATE docente SET legajo = var_legajo WHERE pid = pin_pid;
    END IF;

  END;

$$ LANGUAGE plpgsql;

-- -----------------------------------------------------------------------------
DROP FUNCTION IF EXISTS fn_savedocente (INTEGER, VARCHAR, VARCHAR, VARCHAR, VARCHAR);
CREATE OR REPLACE FUNCTION fn_savedocente (
  pin_pid                           INTEGER,
  pin_dni                           VARCHAR,
  pin_apellido                      VARCHAR,
  pin_nombre                        VARCHAR,
  pin_legajo                        VARCHAR

) RETURNS INTEGER AS $$

  DECLARE
    var_pid                         INTEGER;
    var_dni                         VARCHAR         := TRIM(pin_dni);
    var_apellido                    VARCHAR         := TRIM(pin_apellido);
    var_nombre                      VARCHAR         := TRIM(pin_nombre);
    var_legajo                      VARCHAR         := TRIM(pin_legajo);

  BEGIN
    
    var_pid = fn_savepersona(pin_pid, var_dni, var_apellido, var_nombre);

    PERFORM pr_savedocente(var_pid, var_legajo);
    
    RETURN var_pid;
  END;

$$ LANGUAGE plpgsql;

-- -----------------------------------------------------------------------------
DROP FUNCTION IF EXISTS fn_savedocente (VARCHAR, VARCHAR, VARCHAR, VARCHAR);
CREATE OR REPLACE FUNCTION fn_savedocente (
  pin_dni                           VARCHAR,
  pin_apellido                      VARCHAR,
  pin_nombre                        VARCHAR,
  pin_legajo                        VARCHAR

) RETURNS INTEGER AS $$

  BEGIN
    RETURN fn_savedocente(NULL, pin_dni, pin_apellido, pin_nombre, pin_legajo);
  END;

$$ LANGUAGE plpgsql;

-- -----------------------------------------------------------------------------
DROP FUNCTION IF EXISTS pr_deletedocente (INTEGER);
CREATE OR REPLACE FUNCTION pr_deletedocente (
  pin_pid                           INTEGER

) RETURNS VOID AS $$

  BEGIN
    DELETE FROM docente WHERE pid = pin_pid;

    RETURN;
  END;

$$ LANGUAGE plpgsql;

-- =============================================================================
-- MATERIAS
-- =============================================================================
DROP FUNCTION IF EXISTS fn_savemateria (INTEGER, VARCHAR, TEXT);
CREATE OR REPLACE FUNCTION fn_savemateria (
  pin_mid                           INTEGER,
  pin_nombre                        VARCHAR,
  pin_descripcion                   TEXT
  
) RETURNS INTEGER AS $$

  DECLARE
    var_mid                         INTEGER         := pin_mid;
    var_nombre                      VARCHAR         := TRIM(pin_nombre);

  BEGIN
    IF (var_mid IS NULL) THEN
      -- no existe ==> insert
      INSERT INTO materia(nombre, descripcion)
        VALUES (var_nombre, pin_descripcion)
        RETURNING mid INTO var_mid;
    ELSE
      -- sí existe ==> update
      UPDATE materia SET
        nombre = var_nombre,
        descripcion = pin_descripcion
      WHERE mid = var_mid;
    END IF;

    RETURN var_mid;
  END;

$$ LANGUAGE plpgsql;

-- -----------------------------------------------------------------------------
DROP FUNCTION IF EXISTS fn_savemateria (VARCHAR, TEXT);
CREATE OR REPLACE FUNCTION fn_savemateria (
  pin_nombre                        VARCHAR,
  pin_descripcion                   TEXT
  
) RETURNS INTEGER AS $$

  BEGIN
    RETURN fn_savemateria(NULL, pin_nombre, pin_descripcion);
  END;

$$ LANGUAGE plpgsql;

-- -----------------------------------------------------------------------------
DROP FUNCTION IF EXISTS pr_deletemateria (INTEGER);
CREATE OR REPLACE FUNCTION pr_deletemateria (
  pin_mid                           INTEGER

) RETURNS VOID AS $$

  BEGIN
    DELETE FROM materia WHERE mid = pin_mid;
  END;

$$ LANGUAGE plpgsql;

-- =============================================================================
-- ASOCIACIONES
-- =============================================================================
-- TODO: agregar información (campos) de asociación, como fecha, observaciones,
-- etc...
DROP FUNCTION IF EXISTS pr_saveasociacion (INTEGER, INTEGER);
CREATE OR REPLACE FUNCTION pr_saveasociacion (
  pin_mid                           INTEGER,
  pin_pid                           INTEGER

) RETURNS VOID AS $$

  BEGIN
    INSERT INTO materia_docentes(mid, pid)
      VALUES (pin_mid, pin_pid);
  END;

$$ LANGUAGE plpgsql;

-- -----------------------------------------------------------------------------
DROP FUNCTION IF EXISTS pr_deleteasociacion (INTEGER, INTEGER);
CREATE OR REPLACE FUNCTION pr_deleteasociacion (
  pin_mid                           INTEGER,
  pin_pid                           INTEGER

) RETURNS VOID AS $$

  BEGIN
    DELETE FROM materia_docentes
      WHERE mid = pin_mid
      AND pid = pin_pid;
  END;

$$ LANGUAGE plpgsql;

-- =============================================================================
-- CURSOS
-- =============================================================================
DROP FUNCTION IF EXISTS fn_savecurso (INTEGER, INTEGER, INTEGER, VARCHAR, TEXT, SMALLINT, TIMESTAMP, TIMESTAMP);
CREATE OR REPLACE FUNCTION fn_savecurso (
  pin_cid                           INTEGER,
  pin_mid                           INTEGER,
  pin_pid                           INTEGER,
  pin_nombre                        VARCHAR,
  pin_descripcion                   TEXT,
  pin_cupo                          SMALLINT,
  pin_finicio                       TIMESTAMP,
  pin_ffin                          TIMESTAMP
  
) RETURNS INTEGER AS $$

  DECLARE
    var_cid                         INTEGER         := pin_cid;
    var_nombre                      VARCHAR         := TRIM(pin_nombre);

  BEGIN
    IF (var_cid IS NULL) THEN
      -- no existe ==> insert
      INSERT INTO curso(mid, pid, nombre, descripcion, cupo, finicio, ffin)
        VALUES (pin_mid, pin_pid, var_nombre, pin_descripcion, pin_cupo, pin_finicio, pin_ffin)
        RETURNING cid INTO var_cid;
    ELSE
      -- sí existe ==> update
      UPDATE curso SET
        mid = pin_mid,
        pid = pin_pid,
        nombre = var_nombre,
        descripcion = pin_descripcion,
        finicio = pin_finicio,
        ffin = pin_ffin
      WHERE cid = var_cid;
    END IF;

    RETURN var_cid;
  END;

$$ LANGUAGE plpgsql;

-- -----------------------------------------------------------------------------
DROP FUNCTION IF EXISTS fn_savecurso (INTEGER, INTEGER, VARCHAR, TEXT, SMALLINT, TIMESTAMP, TIMESTAMP);
CREATE OR REPLACE FUNCTION fn_savecurso (
  pin_mid                           INTEGER,
  pin_pid                           INTEGER,
  pin_nombre                        VARCHAR,
  pin_descripcion                   TEXT,
  pin_cupo                          SMALLINT,
  pin_finicio                       TIMESTAMP,
  pin_ffin                          TIMESTAMP
  
) RETURNS INTEGER AS $$

  BEGIN
    RETURN fn_savecurso(NULL, pin_mid, pin_pid, pin_nombre, pin_descripcion, pin_cupo, pin_finicio, pin_ffin);
  END;

$$ LANGUAGE plpgsql;

-- -----------------------------------------------------------------------------
DROP FUNCTION IF EXISTS fn_savecurso (INTEGER, INTEGER, VARCHAR, TEXT, SMALLINT, TIMESTAMP);
CREATE OR REPLACE FUNCTION fn_savecurso (
  pin_mid                           INTEGER,
  pin_pid                           INTEGER,
  pin_nombre                        VARCHAR,
  pin_descripcion                   TEXT,
  pin_cupo                          SMALLINT,
  pin_finicio                       TIMESTAMP
  
) RETURNS INTEGER AS $$

  BEGIN
    RETURN fn_savecurso(pin_mid, pin_pid, pin_nombre, pin_descripcion, pin_cupo, pin_finicio, NULL);
  END;

$$ LANGUAGE plpgsql;

-- -----------------------------------------------------------------------------
DROP FUNCTION IF EXISTS pr_deletecurso (INTEGER);
CREATE OR REPLACE FUNCTION pr_deletecurso (
  pin_cid                           INTEGER

) RETURNS VOID AS $$

  BEGIN
    DELETE FROM curso WHERE cid = pin_cid;
  END;

$$ LANGUAGE plpgsql;

-- =============================================================================
-- INSCRIPCIONES
-- =============================================================================
-- TODO: agregar información (campos) de inscripción, como fecha, observaciones,
-- etc...
DROP FUNCTION IF EXISTS pr_saveinscripcion (INTEGER, INTEGER);
CREATE OR REPLACE FUNCTION pr_saveinscripcion (
  pin_cid                           INTEGER,
  pin_pid                           INTEGER

) RETURNS VOID AS $$

  BEGIN
    INSERT INTO curso_alumnos(cid, pid)
      VALUES (pin_cid, pin_pid);
  END;

$$ LANGUAGE plpgsql;

-- -----------------------------------------------------------------------------
DROP FUNCTION IF EXISTS pr_deleteinscripcion (INTEGER, INTEGER);
CREATE OR REPLACE FUNCTION pr_deleteinscripcion (
  pin_cid                           INTEGER,
  pin_pid                           INTEGER

) RETURNS VOID AS $$

  BEGIN
    DELETE FROM curso_alumnos
      WHERE cid = pin_cid
      AND pid = pin_pid;
  END;

$$ LANGUAGE plpgsql;

-- =============================================================================
-- CALIFICACIONES (TODO)
-- =============================================================================
-- TODO: ...

-- =============================================================================
-- COMMIT;
-- =============================================================================
