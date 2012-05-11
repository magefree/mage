package com.magefree.update;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * @author Loki
 */
public class ChechsumHelper {

    public static byte[] createChecksum(String filename) throws Exception {
        InputStream fis = null;
        MessageDigest complete;

        try {
            fis = new FileInputStream(filename);

            byte[] buffer = new byte[1024];
            complete = MessageDigest.getInstance("SHA1");

            int numRead;
            do {
                numRead = fis.read(buffer);
                if (numRead > 0) {
                    complete.update(buffer, 0, numRead);
                }
            } while (numRead != -1);

            return complete.digest();
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }

    // see this How-to for a faster way to convert
    // a byte array to a HEX string
    public static String getSHA1Checksum(String filename) throws Exception {
        byte[] b = createChecksum(filename);
        String result = "";
        for (int i = 0; i < b.length; i++) {
            result +=
                    Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }
}
