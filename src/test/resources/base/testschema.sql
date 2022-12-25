CREATE SCHEMA seeds_base;

CREATE TABLE seeds
(
    id  int8 NOT NULL,
    url varchar(255) NULL,
    CONSTRAINT seeds_pkey PRIMARY KEY (id)
);

CREATE TABLE terms
(
    id                 int8 NOT NULL,
    repetitions_number int8 NOT NULL,
    term_name          varchar(255) NULL,
    CONSTRAINT terms_pkey PRIMARY KEY (id)
);

CREATE TABLE seeds_terms
(
    seed_id int8 NOT NULL,
    term_id int8 NOT NULL
);

ALTER TABLE seeds_terms
    ADD CONSTRAINT seed_fk FOREIGN KEY (seed_id) REFERENCES public.seeds (id);
ALTER TABLE seeds_terms
    ADD CONSTRAINT term_fk FOREIGN KEY (term_id) REFERENCES public.terms (id);

CREATE SEQUENCE HIBERNATE_SEQUENCE START WITH 15 INCREMENT BY 1;