-- PATIENT

-- lista lekarzy
SELECT name, surname, phone FROM doctors;

-- godziny pracy

SELECT * FROM `office hours` WHERE doctor = 'pwz';

-- dodanie nowej wizyty
INSERT INTO `visits` (`Doctor`, `Patient`, `time`, `date`)
VALUES ('0987567', '12345678909', '06:00:00', '2018-01-18');

SELECT DAYNAME('2018-01-13');


-- RECEPTIONIST

-- usuwanie
DELETE FROM `visits` WHERE `visits`.`ID` = 12;

-- zatwierdzanie
UPDATE `visits` SET `confirmation` = '1' WHERE `visits`.`ID` = 10;

-- DOCTOR

-- zapytanie wyswietlajace historie przyebytych przez pacjenta chorób
-- mozna uzyc zarowno peselu jak i imienia oraz nazwiska pacjenta
-- program waliduje zeby to byl mimo wszystko jeden pacjent, a nie tak jak w ponizszym przykladzie dwoch

SELECT
  v.date        AS DATE,
  d.name        AS Disease,
  `v h`.advices AS ADVICES,
  m.name        AS Medicine,
  doc.surname   AS DOCTOR
FROM ((((visits v RIGHT JOIN `visit history` `v h` ON v.ID = `v h`.visit_ID) LEFT JOIN (recognition r
  JOIN diseases d ON r.disease = d.ID) ON `v h`.ID = r.visit_ID) LEFT JOIN (prescription p
  JOIN medicines m ON p.medicine = m.ID) ON `v h`.ID = p.visit_ID) JOIN doctors doc ON v.Doctor = doc.PWZ) JOIN
  patients pat ON pat.PESEL = v.Patient
WHERE v.Patient = 12345678909 OR (pat.name = 'Janusz' AND pat.surname = 'Krowin-Mekka')
ORDER BY DATE;

-- Pozostale funkcjonalnosci w pliku Input Samples, zapraszam do lektury
-- Dodalem dwa nowe triggery to zrob update
-- Ten trigger od sprawdzania czy zapisujesz sie na odpowiednia godzine do odpowiedniego lekarza jest zrabany ale o tej porze juz nie ogarniam, jutro go naprawie
