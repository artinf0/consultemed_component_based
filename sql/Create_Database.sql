CREATE DATABASE consultemed_action_based

use consultemed_action_based

CREATE TABLE TB_USUARIOS (
	
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	login VARCHAR (255) NOT NULL,
	senha VARCHAR (10) NOT NULL
);

INSERT INTO TB_USUARIOS (login, senha) VALUES ('cbgomes','123');
INSERT INTO TB_USUARIOS (login, senha) VALUES ('maria','123');
INSERT INTO TB_USUARIOS (login, senha) VALUES ('barbara','123');
INSERT INTO TB_USUARIOS (login, senha) VALUES ('gabryella','123');