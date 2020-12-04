DROP DATABASE IF EXISTS medicineinventoryervice;
DROP USER IF EXISTS `medicine_inventory_service`@`%`;
CREATE DATABASE IF NOT EXISTS medicineinventoryervice CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS `medicine_inventory_service`@`%` IDENTIFIED WITH mysql_native_password BY 'password';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, REFERENCES, INDEX, ALTER, EXECUTE, CREATE VIEW, SHOW VIEW,
CREATE ROUTINE, ALTER ROUTINE, EVENT, TRIGGER ON `medicineinventoryervice`.* TO `medicine_inventory_service`@`%`;
FLUSH PRIVILEGES;
