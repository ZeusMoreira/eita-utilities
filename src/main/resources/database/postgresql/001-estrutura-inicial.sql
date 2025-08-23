CREATE SCHEMA IF NOT EXISTS eita_utilities;

CREATE TABLE IF NOT EXISTS eita_utilities.utilitario (
    ut_id UUID NOT NULL PRIMARY KEY,
    ut_lado VARCHAR(20) NOT NULL,
    ut_mapa VARCHAR(20) NOT NULL,
    ut_link VARCHAR(2083) UNIQUE NOT NULL,
    ut_tipo VARCHAR(20) NOT NULL,
    ut_titulo VARCHAR(100) NOT NULL,
    ut_dificuldade VARCHAR(20) NOT NULL,
    ut_data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_lado CHECK (ut_lado IN ('CT', 'TR')),
    CONSTRAINT chk_tipo CHECK (ut_tipo IN ('MOLOTOV', 'SMOKE', 'HE', 'FLASH', 'DECOY', 'INCENDIARIA')),
    CONSTRAINT chk_dificuldade CHECK (ut_dificuldade IN ('FACIL', 'MEDIO', 'DIFICIL', 'IMPOSSIVEL'))
);

CREATE INDEX idx_ut_lado ON eita_utilities.utilitario(ut_lado);
CREATE INDEX idx_ut_mapa ON eita_utilities.utilitario(ut_mapa);
CREATE INDEX idx_ut_tipo ON eita_utilities.utilitario(ut_tipo);
CREATE INDEX idx_ut_titulo ON eita_utilities.utilitario(ut_titulo);
CREATE INDEX idx_ut_dificuldade ON eita_utilities.utilitario(ut_dificuldade);

