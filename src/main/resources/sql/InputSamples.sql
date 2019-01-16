INSERT INTO `doctors` (`PWZ`, `name`, `surname`, `phone`) VALUES ('1234567', 'Martin', 'Luther', '567876234');
INSERT INTO `doctorspass` (`Login`, `Password`, `Doctor`) VALUES ('martinek', '40bd001563085fc35165329ea1ff5c5ecbdbbeef', '1234567');
INSERT INTO `doctors` (`PWZ`, `name`, `surname`, `phone`) VALUES ('0987567', 'Stephen', 'King', '447893024');
INSERT INTO `doctorspass` (`Login`, `Password`, `Doctor`) VALUES ('stiwen', '40bd001563085fc35165329ea1ff5c5ecbdbbeef', '0987567');

INSERT INTO `patients` (`PESEL`, `name`, `surname`, `birthday`, `city`, `street`, `house numer`, `flat number`, `postal code`, `phone`)
VALUES ('87034578954', 'Janusz', 'Koran-Mekka', '1789-01-09', 'Bangkok', 'Srebrna', '34', '23', '90-900', '786456098');
INSERT INTO `patients` (`PESEL`, `name`, `surname`, `birthday`, `city`, `street`, `house numer`, `flat number`, `postal code`, `phone`)
VALUES ('12345678909', 'Tomek', 'Tytka', '1992-04-13', 'Warszawa', 'Pokreta', '45', '76', '78-966', '504747523');

-- password: 123 for docs and receptionist
-- przykladowe dodanie godzin pracy (za doctor trzeba podstawić wyciągnięty z tabeli doctorspass klucz doctora

INSERT INTO `office hours` (`doctor`, `day`, `beginning`, `end`) VALUES ('0987567', 'Monday', '03:00:00', '09:00:00');
INSERT INTO `office hours` (`doctor`, `day`, `beginning`, `end`) VALUES ('0987567', 'Tuesday', '04:00:00', '12:00:00');

-- przykladowe wyciaganie klucza po zalogowaniu:

SELECT Doctor FROM doctorspass WHERE Login = 'martinek' AND Password = '40bd001563085fc35165329ea1ff5c5ecbdbbeef';

-- przykladowe dodanie wizyty przez pacjenta:

INSERT INTO `visits` (`Doctor`, `Patient`, `time`, `date`)
VALUES ('0987567', '12345678909', '14:00:00', '2018-01-20');

-- przykladowe usuniecie wizyty przez recepcjoniste:

DELETE FROM `visits` WHERE date = '2018-01-20' AND time = '14:00:00' AND Doctor = '0987567';

-- lub to samo po ID:

DELETE FROM visits WHERE ID = '6';

-- dodanie historii choroby przez doktora:

INSERT INTO `visit history` (`visit_ID`, `advices`)
VALUES ('7', 'Cancer, Stroke');

-- dodanie nowej choroby:

INSERT INTO `diseases` (`name`) VALUES ('Cancer');
INSERT INTO `diseases` (`name`) VALUES ('Stroke');

-- dodanie lekarstwa:

INSERT INTO `medicines` (`name`) VALUES ('Rutinoscorbin');
INSERT INTO `medicines` (`name`) VALUES ('Diabolotic');

-- przegladanie lekarstw: (tutaj za r wstawiasz to co lekarz wpisuje przy wyszukiwaniu leku

SELECT * FROM medicines WHERE name LIKE '%r%';

-- przegladanie chorob: podobnie jak wyzej

SELECT * FROM diseases WHERE name LIKE '%r%';

-- dodanie choroby do wizyty: (id wizyty masz bo możesz je wyciągnąć podczas tworzenia historii wizyty, np poleceniami:
-- SELECT id FROM visits WHERE Patient = 'pesel' AND Doctor = 'pwz' AND date = CURRENT_DATE;
-- SELECT id FROM `visit history`  WHERE visit_ID = 'tu id wizyty';
-- powinno wystarczyc przy zalozeniu ze pacjent nie bedzie mial wielu wizyt u jednego doktora tego samego dnia
-- id choroby wyciagasz selecta chorob, gdzies tam wyzej jest
INSERT INTO `recognition` (`visit_ID`, `disease`) VALUES ('1', '3');

-- dodanie lekarstwa do wizyty: podobnie jak wyzej

INSERT INTO `prescription` (`visit_ID`, `medicine`) VALUES ('1', '2');
DELETE FROM `prescription` WHERE visit_ID = '1';

