CREATE DATABASE "firma_userDataManagement_db"
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;
	
CREATE TABLE USR (
	user_id SERIAL PRIMARY KEY,
	first_name VARCHAR(50) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
	email VARCHAR(100) NOT NULL UNIQUE,
	password VARCHAR(255) NOT NULL
);
CREATE TABLE TOKN(
	token_id SERIAL PRIMARY KEY,
	user_id int NOT NULL,
	token_str VARCHAR(255) NOT NULL,
	FOREIGN KEY (user_id) REFERENCES USR(user_id)
);