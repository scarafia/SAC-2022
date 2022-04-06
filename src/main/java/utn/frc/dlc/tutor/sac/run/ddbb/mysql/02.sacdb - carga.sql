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
--    C A R G A   B B D D   ( s a c d b )
--        D B M S :   M y S Q L
--
--    A U T O R :    S c a r a f i a
--
-- =============================================================================
USE sacdb;
-- =============================================================================
-- PERSONAS
-- =============================================================================
INSERT INTO persona (dni, apellido, nombre) VALUES
  ('99.999.001', 'Newton', 'Isaac'),
  ('99.999.002', 'Einstein', 'Albert'),
  ('99.999.003', 'Bohr', 'Niels'),
  ('99.999.004', 'Huygens', 'Christiaan'),
  ('99.999.005', 'Leibniz', 'Gottfried'),
  ('99.999.006', 'Galilei', 'Galileo'),
  ('99.999.007', 'Joule', 'James Prescott'),
  ('99.999.008', 'Mastropiero', 'Johann Sebastian'),
  ('99.999.009', 'Morricone', 'Enio'),
  ('99.999.010', 'Vivaldi', 'Antonio'),
  ('99.999.011', 'Van Beethoven', 'Ludwig'),
  ('99.999.012', 'Bach', 'Johann Sebastian'),
  ('99.999.013', 'Strauss', 'Johann'),
  ('99.999.014', 'Mozart', 'Wolfgang Amadeus'),
  ('99.999.015', 'Sartre', 'Jean Paul'),
  ('99.999.016', 'Descartes', 'René'),
  ('99.999.017', 'Kant', 'Immanuel'),
  ('99.999.018', 'Nietzsche', 'Friedrich'),
  ('99.999.019', 'Heidegger', 'Martin'),
  ('99.999.020', 'Schopenhauer', 'Arthur'),
  ('99.999.021', 'Comte', 'Auguste')
;

-- =============================================================================
-- DOCENTES
-- =============================================================================
INSERT INTO docente (pid, legajo) VALUES
  (1, 'LD-001'), (8, 'LD-008'), (15, 'LD-015')
;

-- =============================================================================
-- ALUMNOS
-- =============================================================================
INSERT INTO alumno (pid, legajo) VALUES
  (1, 'LA-10.001'), (2, 'LA-10.002'), (3, 'LA-10.003'),
  (4, 'LA-10.004'), (5, 'LA-10.005'), (6, 'LA-10.006'),
  (7, 'LA-10.007'), (8, 'LA-10.008'), (9, 'LA-10.009'),
  (10, 'LA-10.010'), (11, 'LA-10.011'), (12, 'LA-10.012'),
  (13, 'LA-10.013'), (14, 'LA-10.014'), (15, 'LA-10.015'),
  (16, 'LA-10.016'), (17, 'LA-10.017'), (18, 'LA-10.018'),
  (19, 'LA-10.019'), (20, 'LA-10.020'), (21, 'LA-10.021')
;

-- =============================================================================
-- MATERIAS (y ASOCIACIONES)
-- =============================================================================
INSERT INTO materia (nombre, descripcion) VALUES
  ('Física', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae dolor eget felis pellentesque sagittis non in odio. Maecenas risus nibh, feugiat quis varius eu, mollis sit amet massa. Vivamus.'),
  ('Música', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae dolor eget felis pellentesque sagittis non in odio. Maecenas risus nibh, feugiat quis varius eu, mollis sit amet massa. Vivamus.'),
  ('Filosofía', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae dolor eget felis pellentesque sagittis non in odio. Maecenas risus nibh, feugiat quis varius eu, mollis sit amet massa. Vivamus.')
;

INSERT INTO materia_docentes (mid, pid) VALUES
  (1, 1), (2, 8), (3, 15)
;

-- =============================================================================
-- CURSOS (e INSCRIPCIONES)
-- =============================================================================
INSERT INTO curso (nombre, descripcion, cupo, finicio, mid, pid) VALUES
  ('Física Teórica', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae dolor eget felis pellentesque sagittis non in odio. Maecenas risus nibh, feugiat quis varius eu, mollis sit amet massa. Vivamus.', 24, '2016-03-01 13:45:10', 1, 1),
  ('Historia de la Música', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae dolor eget felis pellentesque sagittis non in odio. Maecenas risus nibh, feugiat quis varius eu, mollis sit amet massa. Vivamus.', 24, '2016-03-01 13:47:18', 2, 8),
  ('Introducción a la Filosofía', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae dolor eget felis pellentesque sagittis non in odio. Maecenas risus nibh, feugiat quis varius eu, mollis sit amet massa. Vivamus.', 24, '2016-03-01 13:52:22', 3, 15)
;

-- sin inscripciones por ahora

-- =============================================================================
-- COMMIT;
-- =============================================================================
