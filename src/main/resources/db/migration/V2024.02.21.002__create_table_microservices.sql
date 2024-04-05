CREATE TABLE mircoservices
(
    id_microservice character varying(36) NOT NULL,
    name character varying(256) NOT NULL,
    squadResponsavel character varying(256) NOT NULL,
    CONSTRAINT mircoservices_pkey PRIMARY KEY (id_microservice)
);