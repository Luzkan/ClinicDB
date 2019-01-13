# ClinicDB - UEPMS
## Unified Electronic Patient Management System

### Description
UEPMS is an application created to cover up clinic or ambulatory needs that allows to hold up data about doctors and patients that are receiving at given clinic in database.
Thanks to the system patient will be able to independently make an appointment and the doctor will be able to keep the history of the diseasee using the database of diseases and medicines.

### Functionality
- Logging in registered users
- Adding, deleting, editing patients
- Adding, removing, editing doctors
- Adding, deleting and editing visits
- Adding the history of visits
- Adding removing, editing diseases
- Adding, deleting and editing medicines
- Automatic verification of the availability of date & doctor
- Acceptance of appointments (by the receptionist)

### Additionally:
- System is safe and reliable
- Each user will be authenticated by the receptionist
- The application will be stable and in the future can be developed with additional functionalities
- Checking the consistency of database by foreign keys and triggers
- Communication of the application with the database will be based on the JDBC engine

### Triggers:

- Before adding a visit system checks whether the visit with the same data is no longer introduced
- If the patient has not signed up for an appointment within the last 5 years, he is removed from the table "patients", however the history of his illness remains
- If the doctor has not added any visits to the history of vistits withing the last 5 years, he is removed from "doctors" table, however, the story kept by given doctor stays in the database
- The date of the visit entered by the patient must be greater than the current one
- The doctor can not add an unapproved visit to the history of visits
- Validation of the entered pesel (date of birth)