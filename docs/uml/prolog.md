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
