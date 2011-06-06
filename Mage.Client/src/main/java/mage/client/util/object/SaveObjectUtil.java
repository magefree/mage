package mage.client.util.object;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class SaveObjectUtil {
	
	private static boolean saveIncomeData = false;
	
	static {
		saveIncomeData = System.getProperty("saveObjects") != null;
	}
	
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
				String time = now("[yyyy_MM_dd][H-mm-ss]");
				File f = new File("income" + File.separator + name + "_" + time + ".save");
				if (!f.exists()) {
					f.createNewFile();
				}
				oos = new ObjectOutputStream(new FileOutputStream(f));
				oos.writeObject(object);
				oos.close();

			} catch (FileNotFoundException e) {
				return;
			} catch (IOException io) {
				return;
			}
		}
	}
	
	public static String now(String dateFormat) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(cal.getTime());
    }
}
