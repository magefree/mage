package mage.client.util;

import java.io.File;

public class FileUtils {
	public static File getTempDir(String key) {
		String tmpDir = System.getProperty("java.io.tmpdir");
		String sep = System.getProperty("file.separator");

		if (!tmpDir.endsWith(sep))
			tmpDir += sep;

		tmpDir += key + "-" + java.util.UUID.randomUUID().toString();

		File dir = new File(tmpDir);
		if (!dir.mkdirs()) {
			throw new RuntimeException("couldn't create temp directory " + tmpDir);
		}

		return dir;

	}
}
