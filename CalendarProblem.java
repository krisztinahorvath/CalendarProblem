import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalendarProblem {

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    public static int timeToMinutes(String time) {
        // converts time string in the format "hh:mm" to minutes

        LocalTime localTime = LocalTime.parse(time, TIME_FORMAT);
        return localTime.getHour() * 60 + localTime.getMinute();
    }

    public static int[][] convertTimeRangesToMinutes(String[][] timeRanges) {
        // converts each booked time slot in the calendars from string to minutes using timeToMinutes()

        int[][] minutesRanges = new int[timeRanges.length][2];
        for (int i = 0; i < timeRanges.length; i++) {
            String startTime = timeRanges[i][0];
            String endTime = timeRanges[i][1];
            int startMinutes = timeToMinutes(startTime);
            int endMinutes = timeToMinutes(endTime);
            minutesRanges[i] = new int[] {startMinutes, endMinutes};
        }
        return minutesRanges;
    }

    public static String minutesToTime(int minutes) {
        // converts a time in minutes into "hh:mm" format

        LocalTime localTime = LocalTime.of((int)(minutes/60), minutes % 60);
        return localTime.format(TIME_FORMAT);
    }

    public static List<String[]> convertToTimeSlots(List<int[]> timeSlots) {
        // converts the common available time slots from minutes to time strings using minutesToTime()

        List<String[]> timeSlotsStrings = new ArrayList<>();
        for (int[] slot : timeSlots) {
            timeSlotsStrings.add(new String[] {minutesToTime(slot[0]), minutesToTime(slot[1])});
        }
        return timeSlotsStrings;
    }

    public static String formatTime(String time) {
        // converts a time string in the format "h:mm" to "hh:mm"

        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return String.format("%02d:%02d", hours, minutes);
    }

    public static String[][] formatTimeRanges(String[][] timeRanges) {
        // formats each booked time slot in the calendars using formatTime()

        String[][] formattedRanges = new String[timeRanges.length][2];
        for (int i = 0; i < timeRanges.length; i++) {
            String startTime = formatTime(timeRanges[i][0]);
            String endTime = formatTime(timeRanges[i][1]);
            formattedRanges[i] = new String[] {startTime, endTime};
        }
        return formattedRanges;
    }

    public static List<int[]> findAvailableTime(int[][] booked, int minRange, int maxRange) {
        // takes a booked calendar and the range of time in which meetings can take place
        // returns a list of available time slots in the same format as the booked time slots

        List<int[]> available = new ArrayList<>();
        if (booked.length == 0) {
            available.add(new int[] {minRange, maxRange});
            return available;
        }
        int start = minRange;
        for (int[] meeting : booked) {
            int end = meeting[0];
            if (start != end) {
                available.add(new int[] {start, end});
            }
            start = meeting[1];
        }
        if (start != maxRange) {
            available.add(new int[] {start, maxRange});
        }
        return available;
    }

    public static List<int[]> findCommonAvailableTime(int[][] calendar1, int minRange1, int maxRange1,
                                                      int[][] calendar2, int minRange2, int maxRange2,
                                                      int meetingTime) {
        // method takes two booked calendars and their respective ranges of time, and the minimum meeting time,
        // returns a list of common available time slots for both calendars

        List<int[]> available1 = findAvailableTime(calendar1, minRange1, maxRange1);
        List<int[]> available2 = findAvailableTime(calendar2, minRange2, maxRange2);

        List<int[]> commonAvailable = new ArrayList<>();

        for (int[] slot1 : available1) {
            for (int[] slot2 : available2) {
                int start = Math.max(slot1[0], slot2[0]);
                int end = Math.min(slot1[1], slot2[1]);
                if (end - start >= meetingTime) {
                    commonAvailable.add(new int[] {start, end});
                }
            }
        }

        return commonAvailable;
    }

    public static void solution(String[][] calendar1, String[] range1, String[][] calendar2, String[] range2, int meetingTime){
        // format the time string from "h:mm" to "hh:mm" and convert them into minutes
        int[][] calendar1_booked = convertTimeRangesToMinutes(formatTimeRanges(calendar1));
        int[][] calendar2_booked= convertTimeRangesToMinutes(formatTimeRanges(calendar2));

        int minRange1 = timeToMinutes(formatTime(range1[0]));
        int maxRange1 = timeToMinutes(formatTime(range1[1]));

        int minRange2 = timeToMinutes(formatTime(range2[0]));
        int maxRange2 = timeToMinutes(formatTime(range2[1]));

        // find the common available time slots
        List<int[]> commonAvailable = findCommonAvailableTime(calendar1_booked, minRange1, maxRange1, calendar2_booked, minRange2, maxRange2, meetingTime);

        // convert the available slots from minutes to string of the format "hh:mm"
        List<String[]> commonAvailableStrings = convertToTimeSlots(commonAvailable);

        System.out.print('[');
        for (String[] slot : commonAvailableStrings) {
            System.out.print('[' + slot[0] + "," + slot[1] + ']');
        }
        System.out.print(']');
    }

    public static void main(String[] args) {
        String[][] calendar1 = {{"9:00", "10:30"}, {"12:00", "13:00"}, {"16:00", "18:00"}};
        String[] range1 = {"9:00", "20:00"};

        String[][] calendar2 = {{"10:00", "11:30"}, {"12:30", "14:30"}, {"14:30", "15:00"}, {"16:00", "17:00"}};
        String[] range2 = {"10:00", "18:30"};

        int meetingTime = 30;

        solution(calendar1, range1, calendar2, range2, meetingTime);
    }
}