# General notes
Default conditions, applicable in each flux: the user must have the application installed and have a working internet connection.

A user being "recorded" is shorthand for only requiring the second factor in authentication.

Use cases are as follows: [account actions](acct-actions.png), [standard operations](authd-actions.png), [user actions](regd-auth.png)

# Fast-forward authentication flux
## Main flux
 Pre-condition: the user must have been logged in during last use.
* 1. The system will send a request to the database about the last logged in user and whether the data is still valid.
* 2. The database will confirm the credentials that were last used.
* 3. The system will proceed to the "Biometric authentication" flux.

 Post-condition: the user is recorded.
## Alternatives
* 2. 1. If the database does not confirm the credentials, enter flux "Account specification".

# Account specification flux
## Main flux
 Pre-condition: the user must not be already logged in or recorded.
* 1. When starting the application, the user will be presented with the login screen: an email field, a password field, two "log in" buttons and a "register" button.
* 2. The user should complete the email and password fields and press one of the "Log in" buttons.
* 3. The system will check the data formatting and forward it to the external database.
* 4. The database will validate the data against its internal resources and forward a confirmation to the application.
* 5. The system will display a screen, depending on the button pressed.

 Post-condition: the user is recorded.
## Alternatives 
* 3. 1. If the data format is incorrect (such as an email address lacking a '@'), the user will be returned to step 2.
* 4. 1. If the database determines the data is incorrect:
        2. The database will send an infirmation to the application.
        3. The user will be then returned to step 2.
     2. If the button pressed at step 2 is "log in via OTP", send an OTP to the user.
* 5. 1. If the button pressed at step 2 is "Log in via OTP", continue with flux "OTP Authentication".
     2. If the button pressed at step 2 is "Log in via fingerprint", continue with flux "Biometric authentication".
## Diagram
[This sequence](otp-login-flux.png), steps 5 - 9.

# OTP authentication flux
## Main flux
 Pre-condition: the user must be recorded but not logged in and have cell signal available.
* 1. The system will display the "Input code" screen.
* 2. The user should input the code received.
* 3. The application will send the code back to the database.
* 4. The database will validate the code and send a confirmation message.
* 5. The user will be then able to select an account to view.

 Post-condition: the user is authenticated and able to perform standard operations.
## Alternatives
* 4. 1. If the code that was inputted is incorrect:
        1. The database will send an infirmation to the application.
        2. The database will send a new SMS, with a new code.
        3. The user will then be returned to step 6.

        Note: this redirection can take place at most 2 times.
## Diagram
[This sequence](otp-login-flux.png), steps 12 - 16.

# Biometric authentication flux
## Main flux
 Pre-condition: the user must be recorded but not already logged in and have a phone capable of reading fingerprints.
* 1. The system will display the "Input fingerprint" screen.
* 2. The user should input their fingerprint. (Methods to do so vary across devices.)
* 3. The application will perform internal checks to ensure the same person that unlocked the phone is entering the application.
* 4. The user will be able to select an account to view.

 Post-condition: the user is authenticated and able to perform standard operations.
## Alternatives
* 3. 1. If the application does not recognize the fingerprint, return to step 2. (This can happen 4 times at most.)
## Diagram
[This sequence](ff-biometric-login-flux.png), steps 4 - 7.

