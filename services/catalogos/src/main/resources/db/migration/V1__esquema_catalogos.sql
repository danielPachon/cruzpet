CREATE TABLE especie (
    codigo VARCHAR(40)  PRIMARY KEY,
    nombre VARCHAR(80)  NOT NULL,
    orden  INTEGER      NOT NULL
);

CREATE TABLE raza (
    id             BIGINT       PRIMARY KEY,
    codigo_especie VARCHAR(40)  NOT NULL REFERENCES especie (codigo),
    nombre         VARCHAR(120) NOT NULL,
    CONSTRAINT uq_raza_por_especie UNIQUE (codigo_especie, nombre)
);

CREATE TABLE vacuna (
    id                       BIGINT       PRIMARY KEY,
    codigo_especie           VARCHAR(40)  NOT NULL REFERENCES especie (codigo),
    nombre                   VARCHAR(120) NOT NULL,
    previene                 VARCHAR(255) NOT NULL,
    intervalo_refuerzo_meses INTEGER      NOT NULL CHECK (intervalo_refuerzo_meses > 0),
    CONSTRAINT uq_vacuna_por_especie UNIQUE (codigo_especie, nombre)
);

CREATE INDEX ix_raza_especie ON raza (codigo_especie);
CREATE INDEX ix_vacuna_especie ON vacuna (codigo_especie);
