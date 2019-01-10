CREATE TABLE IF NOT EXISTS `clinicdb`.`PatientsPass` (
  `Login`    VARCHAR(16) CHARACTER SET utf8
  COLLATE utf8_polish_ci NOT NULL,
  `Password` VARCHAR(16) CHARACTER SET utf8
  COLLATE utf8_polish_ci NOT NULL,
  `Patient`  CHAR(11)    NOT NULL,
  FOREIGN KEY (`Patient`) REFERENCES `Patients` (`PESEL`)
)
  ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `clinicdb`.`DoctorsPass` (
  `Login`    VARCHAR(16) CHARACTER SET utf8
  COLLATE utf8_polish_ci NOT NULL,
  `Password` VARCHAR(16) CHARACTER SET utf8
  COLLATE utf8_polish_ci NOT NULL,
  `Doctor`   CHAR(11)    NOT NULL,
  FOREIGN KEY (`Doctor`) REFERENCES `Doctors` (`PWZ`)
)
  ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `clinicdb`.`ReceptionistsPass` (
  `Login`    VARCHAR(16) CHARACTER SET utf8
  COLLATE utf8_polish_ci NOT NULL,
  `Password` VARCHAR(16) CHARACTER SET utf8
  COLLATE utf8_polish_ci NOT NULL
)
  ENGINE = InnoDB;

CREATE USER IF NOT EXISTS 'receptionist'@'%'
  IDENTIFIED BY 'receptionistpass';
GRANT ALL PRIVILEGES ON *.* TO 'receptionist'@'%'
REQUIRE NONE
WITH GRANT OPTION
  MAX_QUERIES_PER_HOUR 0
  MAX_CONNECTIONS_PER_HOUR 0
  MAX_UPDATES_PER_HOUR 0
  MAX_USER_CONNECTIONS 0;
GRANT ALL PRIVILEGES ON `clinicdb`.* TO 'receptionist'@'%';

CREATE USER IF NOT EXISTS 'Doctor'@'%'
  IDENTIFIED BY 'doctorpass';
GRANT SELECT, INSERT, UPDATE, DELETE ON clinicdb.`diseases` TO 'Doctor'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON clinicdb.`medicines` TO 'Doctor'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON clinicdb.`prescription` TO 'Doctor'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON clinicdb.`recognition` TO 'Doctor'@'%';
GRANT UPDATE ON clinicdb.`office hours` TO 'Doctor'@'%';
GRANT SELECT ON clinicdb.`visits` TO 'Doctor'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON clinicdb.`visit history` TO 'Doctor'@'%';
GRANT CREATE TEMPORARY TABLES,
CREATE VIEW,
SHOW VIEW,
EXECUTE ON *.* TO 'Doctor'@'%'
REQUIRE NONE
WITH MAX_QUERIES_PER_HOUR 0
  MAX_CONNECTIONS_PER_HOUR 0
  MAX_UPDATES_PER_HOUR 0
  MAX_USER_CONNECTIONS 0;

CREATE USER IF NOT EXISTS 'Patient'@'%'
  IDENTIFIED BY 'patientpass';
GRANT SELECT ON clinicdb.`office hours` TO 'Patient'@'%';
GRANT SELECT ON clinicdb.`doctors` TO 'Patient'@'%';
GRANT SELECT, INSERT (Doctor, Patient, time, date) ON clinicdb.`visits` TO 'Patient'@'%';
GRANT CREATE TEMPORARY TABLES,
CREATE VIEW,
SHOW VIEW,
EXECUTE ON *.* TO 'Doctor'@'%'
REQUIRE NONE
WITH MAX_QUERIES_PER_HOUR 0
  MAX_CONNECTIONS_PER_HOUR 0
  MAX_UPDATES_PER_HOUR 0
  MAX_USER_CONNECTIONS 0;
