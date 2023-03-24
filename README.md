# Scheduling-Application

## Introduction ##
This application provides a user-friendly interface for managing customer data and scheduling appointments efficiently. The application pulls data from a MySQL database that has been provided by the consulting organization.

## Description ##
The application comes with several features, including the ability to add, update, and delete customer records, as well as appointments, capturing the type of appointment and a link to the specific customer record in the database. The application also provides the ability to view the calendar by month and by week, adjust appointment times based on user time zones and daylight saving time, and generate reports on appointment types and schedules for consultants.

The application also includes features for preventing scheduling conflicts and data entry errors, such as scheduling appointments outside business hours, scheduling overlapping appointments, entering nonexistent or invalid customer data, and entering an incorrect username and password.

Additionally, the application provides a log-in form that can determine the user’s location and translate log-in and error control messages into two languages. The application also includes lambda expressions to make the program more efficient, and an alert feature that notifies users of appointments within 15 minutes of log-in.

Finally, the application provides the ability to track user activity by recording timestamps for user log-ins in a .txt file.

## How to use the Program ##
#### Getting Started: ####
When you launch the application, you will be directed to the login form. Input your credentials for any user in the database. The valid credentials are username 'test' and password 'test' or username 'admin' and password 'admin'. Additionally, you can choose between English or French language from the login screen.

#### Appointments Screen: ####
Once you have successfully logged in, the appointments screen will open. Here, you can view all the appointments and the appointments information in a table. You can also add, update, or delete appointments. You can view appointments on a weekly, monthly, or all-time basis. The user can also select a button to view reports or view customers' information.

#### Reports Form: ####
The Reports form provides the option to view three different types of reports. Select any report to have it displayed. 

#### View Customers Form: ####
In the View Customers form, you can see the customers' information and add, update, or delete customers.

