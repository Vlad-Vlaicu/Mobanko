# 1. Introduction
## Document purpose
This document aims to describe the expected behaviour of the application "Mobanko", described below.

## System purpose
The system (hereafter also referred to as "Mobanko") aims to function as a mobile banking service.

## Definitions and abbreviations
A user that has logged in before last closing the application, or that has inputted his basic credentials and passed the first database check during the log-in procedure, will be called "recorded".

There are default conditions for each flux detailed in section: the user must have a working internet connection during any operation and have the application installed.

# 2. General description
## Short description
Mobanko is a mobile banking application developed to allow users to access basic banking services via their Android mobile devices. The application aims to provide a seamless banking experience, allowing users to manage their finances on the go securely. The following user specification model outlines the necessary features, functionalities, and requirements that will make Mobanko a successful mobile banking application.
## Motivation
Since many people find in-person interaction to be cumbersome and scheduling an appointment to find details about the current state of a bank account in-person is difficult, an application that simplifies the necessary procedures can find a growing number of customers. Mobanko aims to be such an application.
## Similar products
Since the beginning of the millenium, PayPal has provided a satisfactory experience for personal computers, and more recently, banks have started distributing applications aimed at their clients in particular.
## Project risks
As the competition is well established, this application must prove itself to be superior to them in some way.

# 3. The system
## Users
There will be only three categories of individuals:
* 1. Users, who are capable of performing standard operations. Estimated number: ~10.000. Competency required: reading knowledge, basic level of familiarity with technical financial terms.
* 2. Providers, who will provide services sold through the platform. Estimated number: ~20. Competency required: familiarity with financial terms.
* 3. Other individuals who have a bank account but do not directly interact with the system; they are indirectly interacting by receiving and sending money by transfers from the system. Estimated number: ~4 bln. Competency required: various.
Estimated frequency of system interaction by any individual is expected to be ~3/day.
## System requirements
The device required to use the system should have the minimum requirements as specified by the operating system Android 10, and be running said system or a more recent version. The device should also have celular signal, and optionally be capable of reading fingerprints for biometric authentication.
## Natural requirements
### Security Features
Mobanko should have strong security features to ensure safe and secure transactions.
### User Interface
The application should have an intuitive and user-friendly interface that allows for easy navigation and transaction processing.
### Account Management
Users should be able to open and manage their bank accounts through the Mobanko mobile banking application.
### Money Transfer
The application should allow users to transfer money to other bank accounts either within the same bank or other banks.
### Account Balances
Users can check and view the balance of their accounts on the mobile app.
### Transaction History
The application should allow users to view their transaction history to access details of their financial transactions.
### Notifications
Users should receive notifications for every transaction processed through their accounts.
## Functional Requirements
### Login
Mobanko users should be able to log in using their registered usernames and passwords.
### OTP Verification
The application should have a one-time password (OTP) verification process for every transaction made by users.
### Biometric Authentication
Mobanko should have biometric authentication features, as fingerprints, to secure user information.
### Navigation Menu
Mobanko should have a navigation menu that should be easily accessible to users, providing them with all essential financial services.
## Non-functional Requirements
### Security
Mobanko should have robust security features to safeguard users' sensitive data.
### Responsiveness
The mobile banking application should be responsive to users' commands, allowing for real-time transactions and updates.
### Device Compatibility
The application should be compatible with different Android devices in various screen sizes.
### Performance
Mobanko should have exceptional performance that guarantees speedy transactions with minimal downtimes.
### Reliability
The application should be reliable, ensuring that users can access its features and functions regardless of their location or time.

# 4. System models
## General notes
Use cases are as follows: [account actions](acct-actions.png), [standard operations](authd-actions.png), [user actions](regd-auth.png).

## Fast-forward authentication flux
### Main flux
 Pre-condition: the user must have been logged in during last use.
* 1. The system will send a request to the database about the last logged in user and whether the data is still valid.
* 2. The database will confirm the credentials that were last used.
* 3. The system will proceed to the "Biometric authentication" flux.

 Post-condition: the user is recorded.
### Alternatives
* 2. 1. If the database does not confirm the credentials, enter flux "Account specification".
### Diagram
[This sequence](ff-biometric-login-flux.png), steps 1 - 4.

## Account specification flux
### Main flux
 Pre-condition: the user must not be already logged in or recorded.
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
     2. If the button pressed at step 2 is "log in via OTP", send an OTP to the user.
* 5. 1. If the button pressed at step 2 is "Log in via OTP", continue with flux "OTP Authentication".
     2. If the button pressed at step 2 is "Log in via fingerprint", continue with flux "Biometric authentication".
### Diagram
[This sequence](otp-login-flux.png), steps 5 - 9.

## OTP authentication flux
### Main flux
 Pre-condition: the user must be recorded but not logged in and have cell signal available.
* 1. The system will display the "Input code" screen.
* 2. The user should input the code received.
* 3. The application will send the code back to the database.
* 4. The database will validate the code and send a confirmation message.
* 5. The user will be then able to select an account to view.

 Post-condition: the user is authenticated and able to perform standard operations.
### Alternatives
* 4. 1. If the code that was inputted is incorrect:
        * 1. The database will send an infirmation to the application.
        * 2. The database will send a new SMS, with a new code.
        * 3. The user will then be returned to step 6.

        Note: this redirection can take place at most 2 times.
### Diagram
[This sequence](otp-login-flux.png), steps 12 - 16.

## Biometric authentication flux
### Main flux
 Pre-condition: the user must be recorded but not already logged in and have a phone capable of reading fingerprints.
* 1. The system will display the "Input fingerprint" screen.
* 2. The user should input their fingerprint. (Methods to do so vary across devices.)
* 3. The application will perform internal checks to ensure the same person that unlocked the phone is entering the application.
* 4. The user will be able to select an account to view.

 Post-condition: the user is authenticated and able to perform standard operations.
### Alternatives
* 3. 1. If the application does not recognize the fingerprint, return to step 2. (This can happen 4 times at most.)
### Diagram
[This sequence](ff-biometric-login-flux.png), steps 4 - 7.

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

## Transfer flux
### Main flux
 Pre-condition: the user must be lcapable of standard operations.
* 1. The user will press the "New transaction" button.
* 2. The system will request recipient details from the user.
* 3. The user should 
 Post-condition: the user will see the desired result.
### Alternatives
None.
### [Diagram](transaction-flux.png)