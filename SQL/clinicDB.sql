CREATE DATABASE IF NOT EXISTS `ClinicDB`;

CREATE TABLE IF NOT EXISTS `clinicdb`.`Doctors` (
  `PWZ`     CHAR(7)      NOT NULL,
  `name`    VARCHAR(128) NOT NULL,
  `surname` VARCHAR(128) NOT NULL,
  `phone`   VARCHAR(128) NOT NULL,
  PRIMARY KEY (`PWZ`)
)
  ENGINE = InnoDB;

DELIMITER //
CREATE TRIGGER checkPWZ
  BEFORE INSERT
  ON `Doctors`
  FOR EACH ROW
  BEGIN
    IF (CAST(SUBSTR(NEW.PWZ, 1, 1) AS INT) = 0) OR
       CAST(SUBSTR(NEW.PWZ, 1, 1) AS INT) <> MOD(((CAST(SUBSTR(NEW.PWZ, 2, 1) AS INT) * 1) +
                                                  (CAST(SUBSTR(NEW.PWZ, 3, 1) AS INT) * 2) +
                                                  (CAST(SUBSTR(NEW.PWZ, 4, 1) AS INT) * 3) +
                                                  (CAST(SUBSTR(NEW.PWZ, 5, 1) AS INT) * 4) +
                                                  (CAST(SUBSTR(NEW.PWZ, 6, 1) AS INT) * 5) +
                                                  (CAST(SUBSTR(NEW.PWZ, 7, 1) AS INT) * 6)), 11)
    THEN
      SIGNAL SQLSTATE '12345'
      SET MESSAGE_TEXT = 'check PWZ failed';
    END IF;
  END
//
DELIMITER ;

DELIMITER //
CREATE TRIGGER createEmptyHours
  AFTER INSERT
  ON `Doctors`
  FOR EACH ROW
  BEGIN
INSERT INTO `office hours` (`doctor`, `day`, `beginning`, `end`) VALUES (NEW.PWZ, 'Monday', '00:00:00', '00:00:00');
INSERT INTO `office hours` (`doctor`, `day`, `beginning`, `end`)
VALUES (NEW.PWZ, 'Tuesday', '00:00:00', '00:00:00');
INSERT INTO `office hours` (`doctor`, `day`, `beginning`, `end`)
VALUES (NEW.PWZ, 'Wednesday', '00:00:00', '00:00:00');
INSERT INTO `office hours` (`doctor`, `day`, `beginning`, `end`)
VALUES (NEW.PWZ, 'Thursday', '00:00:00', '00:00:00');
INSERT INTO `office hours` (`doctor`, `day`, `beginning`, `end`) VALUES (NEW.PWZ, 'Friday', '00:00:00', '00:00:00');
INSERT INTO `office hours` (`doctor`, `day`, `beginning`, `end`)
VALUES (NEW.PWZ, 'Saturday', '00:00:00', '00:00:00');
INSERT INTO `office hours` (`doctor`, `day`, `beginning`, `end`) VALUES (NEW.PWZ, 'Sunday', '00:00:00', '00:00:00');
END
//
DELIMITER ;

CREATE TABLE IF NOT EXISTS `clinicdb`.`Office Hours` (
  `doctor`    CHAR(7)                                                                             NOT NULL,
  `day`       ENUM ('Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday') NOT NULL,
  `beginning` TIME                                                                                NOT NULL,
  `end`       TIME                                                                                NOT NULL,
  CONSTRAINT BeginningEnd CHECK (beginning < end),
  FOREIGN KEY (`doctor`) REFERENCES `doctors` (`PWZ`)
    ON DELETE CASCADE
)
  ENGINE = InnoDB;

DELIMITER //
CREATE TRIGGER checkTime
  BEFORE UPDATE
  ON `Office Hours`
  FOR EACH ROW
  BEGIN
    IF NEW.beginning > NEW.end
    THEN
      SIGNAL SQLSTATE '12345'
      SET MESSAGE_TEXT = 'check constraint on Office Hours failed';
    END IF;
  END
//
DELIMITER ;

CREATE TABLE IF NOT EXISTS `clinicdb`.`Patients` (
  `PESEL`       CHAR(11)     NOT NULL,
  `name`        VARCHAR(128) NOT NULL,
  `surname`     VARCHAR(128) NOT NULL,
  `birthday`    DATE         NOT NULL,
  `city`        VARCHAR(128) NOT NULL,
  `street`      VARCHAR(128) NOT NULL,
  `house numer` INT UNSIGNED NOT NULL,
  `flat number` INT UNSIGNED NOT NULL,
  `postal code` CHAR(6)      NOT NULL,
  `post office` VARCHAR(128) NOT NULL,
  `phone`       VARCHAR(128) NOT NULL,
  PRIMARY KEY (`PESEL`)
)
  ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `clinicdb`.`Visits` (
  `ID`           INT      NOT NULL AUTO_INCREMENT,
  `Doctor`       CHAR(7)  NOT NULL,
  `Patient`      CHAR(11) NOT NULL,
  `confirmation` BOOLEAN  NOT NULL DEFAULT FALSE,
  `time`         TIME     NOT NULL,
  `date`         DATE     NOT NULL,
  PRIMARY KEY (`ID`),
  FOREIGN KEY (`Doctor`) REFERENCES `doctors` (`PWZ`),
  FOREIGN KEY (`Patient`) REFERENCES `Patients` (`PESEL`)
    ON DELETE CASCADE
)
  ENGINE = InnoDB;

-- Trigger sprawdzajacy, czy nie ma juz pacjenta zapisanego na dana godzine
DELIMITER //
CREATE TRIGGER checkDoctorOfficeHour
  BEFORE INSERT
  ON `Visits`
  FOR EACH ROW
  BEGIN
    IF NOT (
      CAST(NEW.time AS TIME) > (
        SELECT CAST(H.beginning AS TIME)
        FROM Doctors D
          JOIN `Office Hours` H ON D.PWZ = H.doctor
        WHERE D.PWZ = NEW.Doctor AND H.day = (DAYNAME(NEW.date)))
      AND
      CAST(NEW.time AS TIME) < (
        SELECT CAST(H.end AS TIME)
        FROM Doctors D
          JOIN `Office Hours` H ON D.PWZ = H.doctor
        WHERE D.PWZ = NEW.Doctor AND H.day = (DAYNAME(NEW.date))))
    THEN
      SIGNAL SQLSTATE '12345'
      SET MESSAGE_TEXT = 'check constraint on Office Hours failed';
    END IF;

    IF NEW.date < CURRENT_DATE AND NEW.time < CURRENT_TIME
    THEN
      SIGNAL SQLSTATE '12345'
      SET MESSAGE_TEXT = 'check constraint on Visits-date failed';
    END IF;
  END
//
DELIMITER ;

CREATE TABLE IF NOT EXISTS `clinicdb`.`diseases` (
  `ID`   INT          NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(128) NOT NULL,
  PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `clinicdb`.`medicines` (
  `ID`   INT          NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(128) NOT NULL,
  PRIMARY KEY (`ID`)
)
  ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `clinicdb`.`visit history` (
  `ID`       INT      NOT NULL AUTO_INCREMENT,
  `visit_ID` INT      NOT NULL,
  `advices`  LONGTEXT NULL     DEFAULT NULL,
  PRIMARY KEY (`ID`),
  FOREIGN KEY (`visit_ID`) REFERENCES `Visits` (`ID`)
    ON DELETE CASCADE
)
  ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `clinicdb`.`recognition` (
  `visit_ID` INT NOT NULL,
  `disease`  INT NOT NULL,
  FOREIGN KEY (`visit_ID`) REFERENCES `visit history` (`ID`)
    ON DELETE CASCADE,
  FOREIGN KEY (`disease`) REFERENCES `diseases` (`ID`)
)
  ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `clinicdb`.`prescription` (
  `visit_ID` INT NOT NULL,
  `medicine` INT NOT NULL,
  FOREIGN KEY (`visit_ID`) REFERENCES `visit history` (`ID`)
    ON DELETE CASCADE,
  FOREIGN KEY (`medicine`) REFERENCES `medicines` (`ID`)
)
  ENGINE = InnoDB;

-- new checks because we don't want double references
DELIMITER //
CREATE TRIGGER checkIsNew
  BEFORE INSERT
  ON `prescription`
  FOR EACH ROW
  BEGIN
    IF (SELECT visit_ID
        FROM prescription
        WHERE visit_ID = NEW.visit_ID AND medicine = NEW.medicine) IS NOT NULL
    THEN
      SIGNAL SQLSTATE '23456'
      SET MESSAGE_TEXT = 'check isNew failed';
    END IF;
  END
//
DELIMITER ;

DELIMITER //
CREATE TRIGGER checkIsNew
  BEFORE INSERT
  ON `recognition`
  FOR EACH ROW
  BEGIN
    IF (SELECT visit_ID
        FROM recognition
        WHERE visit_ID = NEW.visit_ID AND disease = NEW.disease) IS NOT NULL
    THEN
      SIGNAL SQLSTATE '23456'
      SET MESSAGE_TEXT = 'check isNew failed';
    END IF;
  END
//
DELIMITER ;