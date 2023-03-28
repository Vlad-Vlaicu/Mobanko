# 4. System models
## General notes
Use-cases are described [here](all-actions.png).

## Registration flux
### Main flux
 Pre-condition: the user must not be already logged in.
* 1. The user will press the "register" button.
* 2. The system will prompt the user for their phone number.
* 3. The user should fill it in.
* 4. The system will validate the data format.
* 5. The system will send the data to the database.
* 6. The database will update and send a confirmation.
* 7. Steps 1 - 5 of the "OTP sending" flux will be taken, along with exceptions.
* 8. The system will prompt the user for other data: email, phone number, password.
* 9. The user should complete the data.
* 10. The system will validate the data format and send the data to the database for registering.
* 11. The database will reply with a confirmation.
* 12. Steps 1 - 5 of the "Account creation" flux will be taken.
* 13. The system will display the main page of the new account.

 Post-condition: the user will be registered, authenticated and have one account.
### Alternatives
* 4. 1. If the phone number format is incorrect, return to step 2.
* 6. 1. If the database does not confirm, return to step 2.
* 7. 1. If any exception happens, follow corresponding steps in "OTP sending" flux.
* 10. 1. If the data format is invalid, return to step 8.
* 11. 1. If the database does not reply with a confirmation, return to step 8.
* 12. 1. If any exception happens, follow correspinding steps in "Account creation" flux.
### [Diagram](registering-flux.png)

## Account creation flux
### Main flux
 Pre-condition: the user must be authenticated.
* 1. The user will press the button "New account".
* 2. The system will prompt the user for the account currency.
* 3. The user should select the wanted currency.
* 4. The system will forward the user's data and the currency to the database.
* 5. The database will send an update confirmation.
* 6. The user will be able to select an account to view.

 Post-condition: the user will have created a new, empty account.
### Alternatives
* 5. 1. If the database does not confirm, the user will be returned to step 2.
## [Diagram](acct-creation-flux.png)

## Fast-forward authentication flux
### Main flux
 Pre-condition: the user must be recorded.
* 1. The system will send a request to the database about the last logged in user and whether the data is still valid.
* 2. The database will confirm the credentials that were last used.
* 3. The system will proceed to the "Biometric authentication" flux.

 Post-condition: none.
### Alternatives
* 2. 1. If the database does not confirm the credentials, enter flux "Account specification".
### [Diagram](ff-biometric-login-flux.png), steps 1 - 4

## Authentication flux
### Main flux
 Pre-condition: the user must not be already logged in or recorded, but be registered.
* 1. When starting the application, the user will be presented with the login screen: an email field, a password field, two "log in" buttons and a "register" button.
* 2. The user should complete the email and password fields and press one of the "Log in" buttons.
* 3. The system will check the data formatting and forward it to the external database.
* 4. The database will validate the data against its internal resources and forward a confirmation to the application.
* 5. The system will display a screen, depending on the button pressed.

 Post-condition: the user is recorded.
### Alternatives 
* 3. 1. If the data format is incorrect (such as an email address lacking a '@'), the user will be returned to step 2.
* 4. 1. If the database determines the data is incorrect:
        * 1. The database will send an infirmation to the application.
        * 2. The user will be then returned to step 2.
* 5. 1. If the button pressed at step 2 is "Log in via OTP", continue with flux "OTP sending".
     2. If the button pressed at step 2 is "Log in via fingerprint", continue with flux "Biometric checking".
### [Diagram](otp-login-flux.png), steps 5 - 9

## OTP sending flux
### Main flux
 Pre-condition: the user must be recorded but not logged in and have cell signal available.
* 1. The database will send an SMS containing the OTP to the user.
* 2. The system will display the "Input code" screen.
* 3. The user should input the code received.
* 4. The application will send the code back to the database.
* 5. The database will validate the code and send a confirmation message.
* 6. The user will then be able to select an account to view, or directly enter their account should they only have one.

 Post-condition: the user is authenticated and able to perform standard operations.
### Alternatives
* 4. 1. If the code that was inputted is incorrect:
        * 1. The database will send an infirmation to the application.
        * 2. The database will send a new SMS, with a new code.
        * 3. The user will then be returned to step 1.

        Note: this redirection can take place at most 2 times.
### [Diagram](otp-login-flux.png), steps 12 - 16

## Biometric checking flux
### Main flux
 Pre-condition: the user must be recorded but not already logged in and have a phone capable of reading fingerprints.
* 1. The system will display the "Input fingerprint" screen.
* 2. The user should input their fingerprint. (Methods to do so vary across devices.)
* 3. The application will perform internal checks to ensure the same person that unlocked the phone is entering the application.
* 4. The user will be able to select an account to view, or directly enter their account should they only have one.

 Post-condition: the user is authenticated and able to perform standard operations.
### Alternatives
* 3. 1. If the application does not recognize the fingerprint, return to step 2. (This can happen 4 times at most.)
### [Diagram](ff-biometric-login-flux.png) (steps 4 - 7)

## Statistics flux
### Main flux
 Pre-condition: the user must be capable of standard operations.
* 1. The user will press the "Statistics" button.
* 2. The system will request the recent transactions from the database.
* 3. The database will reply with the data.
* 4. The system will process the data and display it.
 Post-condition: the user will see the desired result.
### Alternatives
None.
### [Diagram](statistics-flux.png)

## Transfer (transaction) flux
### Main flux
 Pre-condition: the user must be capable of standard operations.
* 1. The user will press the "New transaction" button.
* 2. The system will request recipient details from the user.
* 3. The user should complete said details, most importantly the IBAN of the account.
* 4. The system will send a query to the database confirming all the details.
* 5. The database will send a confirmation of the details.
* 6. The system will prompt the user to enter any other transaction details, notably the amount of moeny transferred.
* 7. The user should complete said details.
* 8. The system will confirm the details with the database in order to validate the transaction.
* 9. The database will confirm the details.
* 10. The system will send the transaction in full for recording and processing, along with update requests.
* 11. The database will update itself and confirm the transaction taking place.
* 12. The system will relay the confirmation to the user and return to the main screen.

 Post-condition: the transaction will have been done.
### Alternatives
* 5. 1. If the database does not confirm the details of the account, return to step 3.
     2. If the account found is in a different currency:
        1. The database will send the exchange rate of the two currencies along with the confirmation.
        2. The flux proceeds to step 6 as normal.
        3. The system will display the exchange in the second "details" page.
        4. The flux proceeds to step 10 as normal.
        5. The system will compute the update queries according to the exchange rate.
        6. The flux then proceeds as normal.
* 8. 1. If the database does not confirm the details of the transaction (such as not enoug money being avaliable), return to step 6.
* 11. 1. If the database does not confirm the update process:
        * 1. Display an error message and a "continue" button.
        * 2. The user should press the button.
        * 3. The system will exit the flux and return to the main screen.
        
        Note: the postcondition will not be fulfilled.
### [Diagram](transaction-flux.png)

## Account extract flux
### Main flux
 Pre-condition: the user must be capable of standard operations.
* 1. The user will press the "Account extract" button.
* 2. The system will request recent transaction data from the database.
* 3. The database should relay the data to the system.
* 4. The system will process the data into the standard viewing format, in a widely recognized electronic format.
* 5. The system will save the data to the filesystem and display it to the user.
* 6. The system will return to the main screen.

 Post-condition: the user will have a copy of their recent transactions for viewing and printing.
 ### [Diagram](acct-extract-flux.png)

## Account deactivation flux
 Pre-condition: the user must be capable of standard operations, have the account be empty and have at least two accounts.
* 1. The user will request deactivation of an account.
* 2. The system will check whether the user still has currency in their account with the database.
* 3. The database will reply with a confirmation.
* 4. The system will relay the appropriate updates to the database.
* 5. The database will update itself and send confirmation.
* 6. The system will display success, and the user will enter their account should they only have one, or be able to select an account to view otherwise.

 Post-condition: the account will be deactivated, remaining in the database only for tracing purposes.
### Alternatives
* 3. 1. If the system does not receive the confirmation:
        1. The system will display an error message.
        2. The system will exit the flux, returning to the main screen.
### [Diagram](acct-deactivation-flux.png)

## User deactivation flux
 Pre-condition: the user must be capable of standard operation.
* 1. The user will request their deactivation.
* 2. For each account the user has active, steps 2 - 3 of the "Account deactivation" flux will be taken, disregarding the third precondition.
* 3. For each account the user has active, steps 2 - 5 of the "Account deactivation" flux will be taken, disregarding the third precondition.
* 3. The system will relay updates to the database requesting deactivation of the user.
* 4. The database will update itself and relay confirmation.
* 5. The system will display success and then the login screen.

 Post-condition: the user will only be registered in the system for tracing purposes and will no longer be able to login.
### Alternatives
* 2. 1. If the system does not receive the confirmation, follow branch 3.1 of the "Account deactivation" flux.
* 3. 1. If the system does not receive the confirmation, follow branch 3.1 of the "Account deactivation" flux.
### [Diagram](user-deactivation-flux.png)

## Deposit creation flux
### Main flux
 Pre-condition: the user must be capable of standard operation.
* 1. The user will request the creation of a deposit.
* 2. Proceed with steps 1 - 5 of the "Account creation" flux.
* 3. The system will retain the IBAN of the new account.
* 4. Proceed with steps 6 - 11 of the "Transfer" flux.
* 5. The system will display a confirmation and return to the main menu.

 Post-condition: the user will have created a new account dedicated to deposits.
### Alternatives
* 2. 1. If any exception happens, follow the corresponding steps in the "Account creation" flux.
* 4. 1. If any exception happens, follow the corresponding steps in the "Transfer" flux.
### [Diagram](deposit-creation.png)