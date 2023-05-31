# Sprint 1 completion

The following flux detailed in the user specification is completed in an altered form:

* 1. ### Registration
    * 1. The user should complete their name and agree to data handling, then click the "Next" button.
    * 2. The system will request an email from the user.
    * 3. The user should complete the email.
    * 4. The system will do an internal format verification of the email.
    * 5. The system will request a database entry for the given information.
    * 6. The database should send an e-mail to the given address containing a link.
    * 7. The user should click on the link.
    * 8. The database website should display a "success" page.
    * 9. The user should return to the system and enter their phone number.
    * 10. The system will forward the phone number to the database.
    * 11. Steps 1 - 5 of the "OTP sending" flux will be taken.
    * 12. The database should send an email asking the user to change their password, containing a link.
    * 13. The user should click on that links.
    * 14. The database website should display a not-a-robot test.
    * 15. The user should complete the test.
    * 16. The database should confim and reply by displaying a new page.
    * 17. The user should complete the new page with their password, and then confirm it in the next page.

* 2. ### OTP sending
    * 1. The database website should display a human verification puzzle.
    * 2. The user should complete the puzzle.
    * 3. The database website shoiuld display success and send a numerical code via SMS.
    * 4. The system will display the "Input code" screen.
    * 5. The user should input the code received.
    * 6. The application will send the code back to the database.
    * 7. The database will validate the code and send a confirmation message.

 Post-condition: the user is registered, authenticated and able to access their pre-made account.
Barring rearrangements, alternate fluxes and preconditions are identical.

 The folowing fluxes detailed in the user specification are completed:
* 1. ### Fast-forward authentication
* 2. ### Biometric authentication flux

# Sprint 2 targets

Performing transfers from one account to another is the target for the next sprint (2 weeks).