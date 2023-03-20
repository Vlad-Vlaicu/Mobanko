# OTP authentication flux
## Main flux
 Pre-condition: the user must have the application installed, have a working internet connection and call signal and not be already logged in.
* 1. When starting the application, the user will be presented with the login screen: an email field, a password field, a "log in" button and a "register" button.
* 2. The user should complete the email and password fields and press the log in button.
* 3. The system will check the data formatting and forward it to the external database.
* 4. The database will validate the data against its internal resources and forward a confirmation to the application and send an SMS to the user with the code.
* 5. The system will display a screen for inputting the code received by SMS.
* 6. The user should input the code received.
* 7. The application will send the code back to the database.
* 8. The database will validate the code and send a confirmation message.
* 9. The user will be then able to select an account to view.
## Alternatives
* 3. 1. If the data format is incorrect (such as an email address lacking a '@'), the user will be returned to step 2.
* 4. 1. If the database determines the data is incorrect:
        * 1. No SMS will be sent to the user.
        * 2. The database will send an infirmation to the application.
        * 3. The user will be then returned to step 2.
* 8. 1. If the code that was inputted is incorrect:
        * 1. The database will send an infirmation to the application.
        * 2. The database will send a new SMS, with a new code.
        * 3. The user will then be returned to step 6.
        Note: this redirection can take place at most 2 times.

