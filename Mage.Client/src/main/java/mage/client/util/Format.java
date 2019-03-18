

package mage.client.util;

import java.util.Date;

/**
 *
 * @author LevelX2
 */
public final class Format {

    /**
     * calculates the duration between two dates and returns a string in the format hhh:mm:ss
     *
     * @param fromDate - start date
     * @param toDate - end date
     * @return a string in the format hhh:mm:ss
     */
    public static String getDuration(Date fromDate, Date toDate) {
        if (fromDate == null || toDate == null || fromDate.getTime() > toDate.getTime()) {
            return "";
        }

        return getDuration((toDate.getTime() - fromDate.getTime()) / 1000);
    }
    /**
     * Converts seconds to a string with hours, minutes and seconds
     *
     * @param seconds - seconds of the duration
     * @return a string in the format hhh:mm:ss
     */
    public static String getDuration(long seconds) {
        StringBuilder sb = new StringBuilder();
        long h = seconds / 3600;
        seconds = seconds % 3600;
        long m = seconds / 60;
        long s = seconds % 60;
        sb.append(h).append(':');
        if (m<10) {
            sb.append('0');
        }
        sb.append(m).append(':');
        if (s<10) {
            sb.append('0');
        }
        sb.append(s);
        return sb.toString();
    }
}
