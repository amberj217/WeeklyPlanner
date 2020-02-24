# WeeklyPlanner
<<<<<<<<<<<<<<<< PURPOSE <<<<<<<<<<<<<<<<
The purpose of this program is to create weekly schedules and keep track of
different types of appointments. The profile of the user is that of a
Language Teacher that works for multiple private schools which cater to
an adult audience.

<<<<<<<<<<<<<<< ENVIRONMENT <<<<<<<<<<<<<
This application has been developed and tested with Eclipse for Java on a Ubuntu 18.04 OS.
It has not been tested in any other environments, therefore if it is not working as described
here, it is best recommended to attempt to run it again on a Linux platform. Java is lauded
by its multiplatform orientation, but the usage of hard-coded paths can quickly undermine that
(especially when going from Windows to Linux), and currently there is such a path in this
application, as it is not a current priority to make it runnable on Windows.

<<<<<<<<<<<<<< HOW TO USE <<<<<<<<<<<<<
The application can be open from the command line as a simple java runnable:
java planner.StartPlanning

The application opens with a small menu:  
       --------MENU--------
       1. Print current plan
       2. Add event
       3. Delete event
       4. Save
       5. Calculate income
       6. Reprint menu
       7. Exit
To select any of the options, type in the afferent numbers. Typing other characters
will cause an instruction to appear on the screen on how to use the menu.

1. Pressing 1 will print the schedule currently contained by the file held by
the PlannerUtils.WEEKLY_SCHEDULE_FILE_PATH constant. If there is no file
given at this path, then there will be nothing printed.

The print feature is far from perfect and relies heavily on tabs alignment which
has its obvious shortcomings. This is a temporary "proof-of-concept" feature and
the long term plans have it completely replaced with a web-based interface.

2.Adding a new event must have the following format:
<Day of the week> <Type of event> <Start time> <End time>
eg. Tuesday Appointment 14:00 - 15:00
--> Currently all days are supported and the user should either type the full name or
the first 3 characters, without concern for the letter case.
--> Types of events are: Meeting, Appointment, Lesson Berlitz, Lesson Kern or Lesson Private
--> Start times and end times have a HH:MM format. NOTE: although needed when entering a new
event, the End Time is not further implemented, therefore, logic inconsistencies will not
be determined or have any impact on the current functioning

Option 2 currently only supports adding one event at a time.

3. To delete an event, the syntax is:
< Day of the week> <Start time>
eg. Tuesday 14:00
The same variables and rules apply as per option 2.

4. Saves the currently held schedule to the file determined by
PlannerUtils.WEEKLY_SCHEDULE_FILE_PATH. If there is no file, that one will be created.

5. Prints a total price for the lessons available in a  week.

Note: Berlitz lessons have a different price depending on the number in a week. Currently
the price change is easily done for when adding an event, but removing events will
not change back the price of the lessons until restart of application. This represents
a serious inconsistency of the app, which deem this feature unreliable, and will be fixed ASAP.

6. Reprints the menu encountered at the start of the application.

7. Gracefully exits program with a closing message.

<<<<<<<<<<<<< NOTES FOR CURRENT STATUS <<<<<<<<
The application is presented in this format only as a "proof of concept" and it
is not currently intended to be used as a final product.

The final product will come with the following:
--> A user friendly and intuitive interface, preferably web based.
--> One of the main attributes of the application is to calculate the income provided
by the different lessons and give the user a grand total at the end. The intention for the
appointments (such as doctor appointments or tax advisor) is to allow a cost to be added which
can be factored in the income outcome for the week.
--> End time of events must be factored in

The inspiration for this application is my partner's current line of work, English teaching
for different private schools that have "gig-economy" style contract where the employee must
keep track of schedule, income and tax contributions.
