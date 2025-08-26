ALTER TABLE eita_utilities.utilitario
ADD COLUMN ut_video_disponivel BOOLEAN NOT NULL DEFAULT TRUE;

ALTER TABLE eita_utilities.utilitario
ADD COLUMN ut_data_alteracao TIMESTAMP;
