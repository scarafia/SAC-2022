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
--    E S T R U C T U R A   B B D D   ( s a c d b )
--        D B M S :   P o s t g r e S Q L
--
--    A U T O R :    S c a r a f i a
--
-- =============================================================================
-- =============================================================================
--    T A B L A S ,   Í N D I C E S   Y   S E Q U E N C I A S
-- =============================================================================
-- =============================================================================
-- PERSONAS
-- =============================================================================
-- DROP SEQUENCE IF EXISTS sq_curso CASCADE;
-- CREATE SEQUENCE sq_curso;
DROP TABLE IF EXISTS persona CASCADE;
CREATE TABLE persona (
  pid                   SERIAL                          NOT NULL,
  dni                   VARCHAR(64)                     NOT NULL,
  apellido              VARCHAR(64)                     NOT NULL,
  nombre                VARCHAR(64)                     NOT NULL,
  PRIMARY KEY (pid),
  UNIQUE (dni)
);
CREATE INDEX idx_persona_apenom ON persona(apellido, nombre);

-- =============================================================================
-- ALUMNOS
-- =============================================================================
DROP TABLE IF EXISTS alumno CASCADE;
CREATE TABLE alumno (
  pid                   INTEGER                         NOT NULL,
  legajo                VARCHAR(16)                     NOT NULL,
  PRIMARY KEY (pid),
  FOREIGN KEY (pid)
    REFERENCES persona(pid),
  UNIQUE (legajo)
);

-- =============================================================================
-- DOCENTES
-- =============================================================================
DROP TABLE IF EXISTS docente CASCADE;
CREATE TABLE docente (
  pid                   INTEGER                         NOT NULL,
  legajo                VARCHAR(16)                     NOT NULL,
  PRIMARY KEY (pid),
  FOREIGN KEY (pid)
    REFERENCES persona(pid),
  UNIQUE (legajo)
);

-- =============================================================================
-- MATERIAS
-- =============================================================================
DROP TABLE IF EXISTS materia CASCADE;
CREATE TABLE materia (
  mid                   SERIAL                          NOT NULL,
  nombre                VARCHAR(64)                     NOT NULL,
  descripcion           TEXT,
  PRIMARY KEY(mid),
  UNIQUE (nombre)
);

-- =============================================================================
-- ASOCIACIONES (DOCENTES x MATERIA)
-- =============================================================================
-- TODO: agregar información (campos) de asociación, como fecha, observaciones,
-- etc...
DROP TABLE IF EXISTS materia_docentes CASCADE;
CREATE TABLE materia_docentes (
  mid                   INTEGER                         NOT NULL,
  pid                   INTEGER                         NOT NULL,
  PRIMARY KEY(mid, pid),
  FOREIGN KEY(mid)
    REFERENCES materia(mid),
  FOREIGN KEY(pid)
    REFERENCES docente(pid)
);

-- =============================================================================
-- CURSOS
-- =============================================================================
DROP TABLE IF EXISTS curso CASCADE;
CREATE TABLE curso (
  cid                   SERIAL                          NOT NULL,
  mid                   INTEGER                         NOT NULL,
  pid                   INTEGER                         NOT NULL,
  nombre                VARCHAR(64)                     NOT NULL,
  descripcion           TEXT,
  cupo                  SMALLINT        DEFAULT 0       NOT NULL,
  finicio               TIMESTAMP                       NOT NULL,
  ffin                  TIMESTAMP,
  PRIMARY KEY (cid),
  FOREIGN KEY (mid, pid)
    REFERENCES materia_docentes(mid, pid),
  UNIQUE (nombre),
  CHECK (cupo > 0),
  CHECK ((ffin IS NULL) OR (finicio < ffin))
);
CREATE INDEX idx_curso_finicio ON curso(finicio);

-- =============================================================================
-- INSCRIPCIONES (ALUMNOS x CURSO)
-- =============================================================================
-- TODO: agregar información (campos) de inscripción, como fecha, observaciones,
-- etc...
DROP TABLE IF EXISTS curso_alumnos CASCADE;
CREATE TABLE curso_alumnos (
  cid                   INTEGER                         NOT NULL,
  pid                   INTEGER                         NOT NULL,
  PRIMARY KEY (cid, pid),
  FOREIGN KEY (cid)
    REFERENCES curso(cid),
  FOREIGN KEY (pid)
    REFERENCES alumno(pid)
);

-- =============================================================================
-- CALIFICACIONES (TODO)
-- =============================================================================
-- TODO: ...

-- =============================================================================
--    V I S T A S
-- =============================================================================
CREATE OR REPLACE VIEW v_persona AS
  SELECT p.*, d.legajo AS legajo_docente, a.legajo AS legajo_alumno
  FROM persona p
  LEFT JOIN docente d ON p.pid = d.pid
  LEFT JOIN alumno a ON p.pid = a.pid
;

CREATE OR REPLACE VIEW v_alumno AS
  SELECT p.*, a.legajo
  FROM alumno a
  JOIN persona p ON a.pid = p.pid
;

CREATE OR REPLACE VIEW v_docente AS
  SELECT p.*, d.legajo
  FROM docente d
  JOIN persona p ON d.pid = p.pid
;

CREATE OR REPLACE VIEW v_curso AS
  SELECT
    c.cid, c.nombre AS curso,
    m.mid, m.nombre AS materia,
    c.cupo,
    c.pid AS did,
    ('(' || d.legajo || ') ' || pd.apellido || ', ' || pd.nombre) AS docente,
    c.descripcion, c.finicio, c.ffin,
    a.legajo, pa.*
  FROM curso c
  JOIN materia m ON c.mid = m.mid
  JOIN docente d ON c.pid = d.pid
  JOIN persona pd ON c.pid = pd.pid
  LEFT JOIN curso_alumnos ca ON c.cid = ca.cid
  LEFT JOIN alumno a ON ca.pid = a.pid
  LEFT JOIN persona pa ON a.pid = pa.pid
;

-- =============================================================================
--COMMIT;
-- =============================================================================
