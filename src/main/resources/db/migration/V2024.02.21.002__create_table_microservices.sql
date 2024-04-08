CREATE TABLE microservices
(
    id_microservice character varying(36) NOT NULL,
    name character varying(256) NOT NULL,
    squad_responsavel character varying(256) NOT NULL,
    CONSTRAINT mircoservices_pkey PRIMARY KEY (id_microservice)
);