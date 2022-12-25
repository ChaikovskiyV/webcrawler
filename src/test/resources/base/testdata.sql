INSERT INTO seeds (id, url)
VALUES (1, 'firstUrl'),
       (2, 'secondUrl'),
       (3, 'thirdUrl'),
       (4, 'fourthUrl'),
       (5, 'fifthUrl');

INSERT INTO terms (id, repetitions_number, term_name)
VALUES (6, 3, 'firstTerm'),
       (7, 5, 'firstTerm'),
       (8, 0, 'firstTerm'),
       (9, 3, 'secondTerm'),
       (10, 4, 'secondTerm'),
       (11, 0, 'secondTerm'),
       (12, 6, 'thirdTerm'),
       (13, 0, 'thirdTerm'),
       (14, 1, 'thirdTerm');

INSERT INTO seeds_terms (seed_id, term_id)
VALUES (1, 6),
       (1, 9),
       (1, 14),
       (2, 7),
       (2, 10),
       (2, 13),
       (3, 8),
       (3, 11),
       (3, 12),
       (4, 7),
       (4, 9),
       (4, 14),
       (5, 8),
       (5, 10),
       (5, 12);