# Meeting_Scheduler
## Classes
a) meeting class:
This class acts as an object of meetings that are scheduled. ArrayList of this meeting will be for each employees who has scheduled a valid meeting.

b) slot class:
This acts as a time chunk it blocks a time for a partiular meeting in a partiular room i.e a room will have an arraylist of these chunks.

c) main class:
It is from where the program will start running.

## Methods
a) betweenDates:
This method takes input of two date object and returns the number of days between those dates, it is used to check the constraints and validity.

b) canhappen:_
This method takes input of start_time, end_time and date and checks the availability of room for the given parameters and if does have that blocks that slot using slot class object.

c) cancel:
This method takes the input a HashMap which have the data of meetings for a particular employee , cancel meeting id and employee id who cancels meeting, this method checks if the employee is valid to cancel the meeting id or not, and if does that it cancels the particular meeting id and free the slot from that room.

