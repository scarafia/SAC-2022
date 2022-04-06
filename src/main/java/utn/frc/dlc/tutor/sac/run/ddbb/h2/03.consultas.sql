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
--    C O N S U L S T A S   B B D D   ( s a c d b )
--        D B M S :   H 2   D a t a b a s e   E n g i n e
--
--    A U T O R :    S c a r a f i a
--
-- =============================================================================
SELECT *
FROM v_persona
ORDER BY apellido, nombre;

SELECT * FROM v_curso c
ORDER BY c.curso, c.apellido, c.nombre;

-- =============================================================================
