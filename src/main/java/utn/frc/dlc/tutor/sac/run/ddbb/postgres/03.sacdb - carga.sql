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
--        D B M S :   P o s t g r e S Q L
--
--    A U T O R :    S c a r a f i a
--
-- =============================================================================

-- =============================================================================
-- PERSONAS, DOCENTES, ALUMNOS... (directo y/o con asociación/es)
-- =============================================================================
SELECT fn_savepersona('99.999.001', 'Newton', 'Isaac');
SELECT pr_savedocente(1, 'LD-001');
SELECT pr_savealumno(1, 'LA-10.001');

SELECT fn_savealumno('99.999.002', 'Einstein', 'Albert', 'LA-10.002');
SELECT fn_savealumno('99.999.003', 'Bohr', 'Niels', 'LA-10.003');
SELECT fn_savealumno('99.999.004', 'Huygens', 'Christiaan', 'LA-10.004');
SELECT fn_savealumno('99.999.005', 'Leibniz', 'Gottfried', 'LA-10.005');
SELECT fn_savealumno('99.999.006', 'Galilei', 'Galileo', 'LA-10.006');
SELECT fn_savealumno('99.999.007', 'Joule', 'James Prescott', 'LA-10.007');

SELECT fn_savedocente('99.999.008', 'Mastropiero', 'Johann Sebastian', 'LD-008');
SELECT pr_savealumno(8, 'LA-10.008');

SELECT fn_savealumno('99.999.009', 'Morricone', 'Enio', 'LA-10.009');
SELECT fn_savealumno('99.999.010', 'Vivaldi', 'Antonio', 'LA-10.010');
SELECT fn_savealumno('99.999.011', 'Van Beethoven', 'Ludwig', 'LA-10.011');
SELECT fn_savealumno('99.999.012', 'Bach', 'Johann Sebastian', 'LA-10.012');
SELECT fn_savealumno('99.999.013', 'Strauss', 'Johann', 'LA-10.013');
SELECT fn_savealumno('99.999.014', 'Mozart', 'Wolfgang Amadeus', 'LA-10.014');

SELECT fn_savealumno('99.999.015', 'Sartre', 'Jean Paul', 'LA-10.015');
SELECT pr_savedocente(15, 'LD-015');

SELECT fn_savealumno('99.999.016', 'Descartes', 'René', 'LA-10.016');
SELECT fn_savealumno('99.999.017', 'Kant', 'Immanuel', 'LA-10.017');
SELECT fn_savealumno('99.999.018', 'Nietzsche', 'Friedrich', 'LA-10.018');
SELECT fn_savealumno('99.999.019', 'Heidegger', 'Martin', 'LA-10.019');
SELECT fn_savealumno('99.999.020', 'Schopenhauer', 'Arthur', 'LA-10.020');
SELECT fn_savealumno('99.999.021', 'Comte', 'Auguste', 'LA-10.021');

-- =============================================================================
-- MATERIAS (y ASOCIACIONES)
-- =============================================================================
SELECT fn_savemateria('Física', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae dolor eget felis pellentesque sagittis non in odio. Maecenas risus nibh, feugiat quis varius eu, mollis sit amet massa. Vivamus.');
SELECT fn_savemateria('Musica', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae dolor eget felis pellentesque sagittis non in odio. Maecenas risus nibh, feugiat quis varius eu, mollis sit amet massa. Vivamus.');
SELECT fn_savemateria(2, 'Música', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae dolor eget felis pellentesque sagittis non in odio. Maecenas risus nibh, feugiat quis varius eu, mollis sit amet massa. Vivamus.');
SELECT fn_savemateria('Filosofía', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae dolor eget felis pellentesque sagittis non in odio. Maecenas risus nibh, feugiat quis varius eu, mollis sit amet massa. Vivamus.');

SELECT pr_saveasociacion(1, 1);
SELECT pr_saveasociacion(2, 8);
SELECT pr_saveasociacion(3, 15);

-- =============================================================================
-- CURSOS (e INSCRIPCIONES)
-- =============================================================================
SELECT fn_savecurso(1, 1, 'Física Teórica'::VARCHAR, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae dolor eget felis pellentesque sagittis non in odio. Maecenas risus nibh, feugiat quis varius eu, mollis sit amet massa. Vivamus.'::VARCHAR, 24::SMALLINT, '2016-03-01 13:45:10'::TIMESTAMP);
SELECT fn_savecurso(2, 8, 'Historia de la Música'::VARCHAR, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae dolor eget felis pellentesque sagittis non in odio. Maecenas risus nibh, feugiat quis varius eu, mollis sit amet massa. Vivamus.'::VARCHAR, 24::SMALLINT, '2016-03-01 13:47:18'::TIMESTAMP);
SELECT fn_savecurso(3, 15, 'Introducción a la Filosofía'::VARCHAR, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae dolor eget felis pellentesque sagittis non in odio. Maecenas risus nibh, feugiat quis varius eu, mollis sit amet massa. Vivamus.'::VARCHAR, 24::SMALLINT, '2016-03-01 13:52:22'::TIMESTAMP);

-- sin inscripciones por ahora

-- =============================================================================
-- COMMIT;
-- =============================================================================
