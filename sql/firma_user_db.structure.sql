DROP DATABASE IF EXISTS firma_user_db;
CREATE DATABASE firma_user_db
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;

\c firma_user_db;

DROP USER IF EXISTS firma;
CREATE USER firma WITH PASSWORD 'firma';
GRANT CONNECT ON DATABASE firma_user_db TO firma;
GRANT ALL PRIVILEGES ON DATABASE firma_user_db TO firma;
GRANT ALL PRIVILEGES ON SCHEMA public TO firma;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO firma;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public to firma;
GRANT ALL PRIVILEGES ON ALL FUNCTIONS IN SCHEMA public to firma;

DROP TABLE IF EXISTS "user";	
CREATE TABLE "user" (
	user_id SERIAL PRIMARY KEY,
	first_name VARCHAR(50) NOT NULL,
	last_name VARCHAR(50) NOT NULL,
	email VARCHAR(100) NOT NULL UNIQUE,
	password VARCHAR(255) NOT NULL
);
GRANT ALL PRIVILEGES ON TABLE "user" TO firma;

insert into "user" (first_name, last_name, email, password) values ('Korry', 'Passfield', 'kpassfield0@cocolog-nifty.com', 'mP9B1mSca');