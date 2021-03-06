#Changelog
All notable changes to this project will be documented in this file.

### [Bugs found / TODO]
- Get rid of code duplication

## [1.8.0] - 2019-01-22
### Added
- Show Info button to get information about a visit
- Info can't be shown if visit wasn't at least confirmed 
- DB samples
### Fixed
- Bugs with queries


## [1.7.0] - 2019-01-16
### Added
- Added Show Visit button for patient and doctor to get information about selected visit (desc/disease/medicine) 
- Added diseases samples

### Changed
- Removed duplicate code. Instead created Universal GUI Package that's in use for more than one user

## [1.6.1] - 2019-01-16
### Added
- Doctor can add multiple diseases/medicines to one visit
### Fixed
- Doctor couldn't add history visit (medicine & disease)

## [1.6.0] - 2019-01-16
### Added
- Now all passwords are hashed

## [1.5.2] - 2019-01-15
### Changed
- Console prints once again for better understanding
- SQL queries

## [1.5.1] - 2019-01-15
### Changed
- Console prints
- Removed warning about java version
- Removed some useless code that did nothing and still was in the project

## [1.5.0] - 2019-01-14
### Added
- UML's for this version
### Fixed
- Finally fixed bug that made values not appear in tableview

## [1.4.1] - 2019-01-14
### Change
- Code & Warnings clean-up

## [1.4.0] - 2019-01-14
### Added
- Now patient can change a visit that's unconfirmed.

## [1.3.1] - 2019-01-14
### Added
- Now you can type in doctor's name to search for visit hours in one textfield

## [1.3.0] - 2019-01-13
### Added
- Button for patients to show up all names of the doctors in database
- Table to view actual upcoming visits for logged in patient

## [1.2.2] - 2019-01-13
### Added
- Now Password will be covered up
- Password in mainmenu can be visible if someone wants it
- UML Diagram

## [1.2.1] - 2019-01-13
### Added
- QoF - autopesel for patient
- Receptionist can no longer delete confirmed visit
### Fixed
- Adding a Visit works now
- Tab on registry works now correctly in order

## [1.2.0] - 2019-01-13
### Added
- New Patient Registration
- Added alerts to make application more responsive
- Now Doctor will see only oonfirmed visits
### Fixed
- Correct names for tables in archive

## [1.1.1] - 2019-01-13
### Added
- Functionality to edit history by doctor

## [1.1.0] - 2019-01-13
### Added
- Functionality to edit history by receptionist

## [1.0.0] - 2019-01-13
### Changed
- ID is not shown up anymore as it's useless value
- Small other changes
### Fixed
- My god fixed that weird problem that some columns are not showing up a value even though they have it in

## [0.6.1] - 2019-01-12
### Fixed
- Typo in login checks

## [0.6.0] - 2019-01-12
### Added
- Login check
## Fixed
- You can log-in as anyone with valid credentials.
- You can log-in w/o valid credentials

## [0.5.0] - 2019-01-12
### Added
- Alert if nothing was typed to find Doc
### Fixed
- Show Doctors Visit Hours (in Patient) says that user have no permission
- Show Doctors Visit Hours (in Patient) in same window instead of opening new one

## [0.4.5] - 2019-01-12
### Added
- Added Alerts for Receptionist, renamed buttons

## [0.4.4] - 2019-01-12
### Added
- Added Alerts while adding visit history.

## [0.4.3] - 2019-01-12
### Changed
- SQL Table Name fix

## [0.4.2] - 2019-01-12
### Changed
- Warnings cleanup

## [0.4.1] - 2019-01-12
### Changed
- Titles
- Console Prints

## [0.4.0] - 2019-01-12
### Changed
- Somewhat connected tables to read db
- Rescaled window size (the Add stuff) and corrected typos
- Last Project Folder Organisation

## [0.3.0] - 2019-01-12
### Changed
- Now project is based on .fxml's 
- Better quality I guess, it's now more eye-pleasant
- Last Project Folder Organisation

## [0.2.3] - 2019-01-11
### Changed
- Packages

## [0.2.3] - 2019-01-11
### Changed
- Project Restructure (folder organization)

## [0.2.2] - 2019-01-10
### Added
- Changelog formatting

## [0.2.1] - 2019-01-10
### Added
- Changelog

## [0.2.0] - 2019-01-10

### Description:
Project is now successfully running.

### Added
- Maven with MySQL Dependency

### Changed
- Few .sql lines like
  - Renamed procedures to not have two of the same name
  - Updated dates for 2019
  - Updated two sample PWZ to make them correct with check alghoritm

### Removed
- Random /out/ folder
- Some other unimportant stuff I can't remember (sorry ._.)

## [0.1.0] - 2019-01-11
- Base Project
