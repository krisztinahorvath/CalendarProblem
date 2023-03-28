# CalendarProblem

Given the calendar booked time of two people find all available time they can meet.
Restrictions:
- each calendar will have limits, min and max range (ex: one maybe from 8:00 until 20:00,
maybe one starting from 10:00 until 18:00)
- the range of all available (free) time they can meet will have to fit into the meeting
required time (a variable input set to minutes)
- you must find all free time between the 2 calendars that is bigger or equal to the given
meeting minutes time

*Sample input:*<br>
booked calendar1: <br> 
[['9:00','10:30'], ['12:00','13:00'], ['16:00','18:00]] <br>
calendar1 range limits: ['9:00','20:00']
<br><br>booked calendar2:
 [['10:00','11:30'], ['12:30','14:30'],['14:30','15:00], ['16:00','17:00']]
<br>calendar2 range limits: ['10:00','18:30']
<br><br>Meeting Time Minutes: 30

Sample output:
[['11:30','12:00'], ['15:00','16:00'], ['18:00':'18:30']]

**Solution**:
- the program converts the two given calendars and their ranges into time strings of the form "hh:mm"
- transforms the time strings into minutes
- forms a list for each calendar with its available time slots
- creates a list of common available time slots from the two calendars
- convert the available slots from minutes into time string of the format "hh:mm"