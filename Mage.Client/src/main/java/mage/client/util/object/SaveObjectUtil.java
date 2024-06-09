package mage.client.util.object;

import mage.utils.StreamUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * TODO: part of replay system? Un-used, can be deleted
 *
 * Utility class to save an object on disk.
 *
 * @author ayrat
 */
public final class SaveObjectUtil {

    /**
     * Defines should data be saved or not.
     * Read from system property:
     */
    private static boolean saveIncomeData = false;

    /**
     * Defines the system property name to get {@link #saveIncomeData} value from.
     */
    private static final String SAVE_DATA_PROPERTY = "saveObjects";

    /**
     * Date pattern used to form filename to save object to.
     */
    private static final String DATE_PATTERN = "[yyyy_MM_dd][H-mm-ss]";

    static {
        saveIncomeData = System.getProperty(SAVE_DATA_PROPERTY) != null;
    }

    /**
     * Save object on disk.
     *
     * @param object Object to save.
     * @param name Part of name that will be used to form original filename to save object to.
     */
    public static void saveObject(Object object, String name) {
        if (saveIncomeData) {
            ObjectOutputStream oos = null;
            try {
                File dir = new File("income");
                if (!dir.exists() || dir.exists() && dir.isFile()) {
                    boolean bCreated = dir.mkdir();
                    if (!bCreated) {
                        return;
                    }
                }
                String time = now(DATE_PATTERN);
                File f = new File("income" + File.separator + name + '_' + time + ".save");
                if (!f.exists()) {
                    f.getParentFile().mkdirs();
                    f.createNewFile();
                }
                oos = new ObjectOutputStream(new FileOutputStream(f));
                oos.writeObject(object);
                oos.close();

            } catch (Exception e) {
            } finally {
                StreamUtils.closeQuietly(oos);
            }
        }
    }

    public static String now(String dateFormat) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(cal.getTime());
    }
}
