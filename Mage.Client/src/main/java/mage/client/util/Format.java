/*
* Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.client.util;

import java.util.Date;

/**
 *
 * @author LevelX2
 */
public class Format {

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
        sb.append(h).append(":");
        if (m<10) {
            sb.append("0");
        }
        sb.append(m).append(":");
        if (s<10) {
            sb.append("0");
        }
        sb.append(s);
        return sb.toString();
    }
}
