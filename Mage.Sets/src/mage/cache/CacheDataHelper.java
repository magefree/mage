package mage.cache;

import org.apache.log4j.Logger;

import java.io.*;

/**
 * @author noxx
 */
public class CacheDataHelper {

    private static final Logger log = Logger.getLogger(CacheDataHelper.class);
    
    /**
     * Save object on disk.
     *
     * @param cache Cache object to save.
     * @param name Part of name that will be used to form original filename to save object to.
     */
    public static void cacheObject(Cache cache, String name) {
        ObjectOutputStream oos = null;
        try {
            File dir = new File("cache");
            if (!dir.exists() || dir.exists() && dir.isFile()) {
                boolean bCreated = dir.mkdir();
                if (!bCreated) {
                    log.error("Couldn't create directory for cache.");
                    return;
                }
            }
            File f = new File("cache" + File.separator + name + ".obj");
            if (!f.exists()) {
                f.createNewFile();
            }
            oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(cache);
            oos.close();

        } catch (FileNotFoundException e) {
            log.error("Error while caching data: ", e);
            return;
        } catch (IOException io) {
            log.error("Error while caching data: ", io);
            return;
        }
    }

    /**
     * Gets Cache object from cache folder.
     *
     * @param name
     * @return
     */
    public static Cache getCachedObject(String name) {
        ObjectInputStream ois = null;
        try {
            File dir = new File("cache");
            if (!dir.exists() || dir.exists() && dir.isFile()) {
                return null;
            }
            File f = new File("cache" + File.separator + name + ".obj");
            if (!f.exists()) {
                log.warn("Couldn't find cache for name: " + name);
                return null;
            }
            ois = new ObjectInputStream(new FileInputStream(f));
            Object object = ois.readObject();

            if (!(object instanceof Cache)) {
                log.error("Cached object has wrong type: " + object.getClass().getName());
                return null;
            }

            return (Cache)object;

        } catch (FileNotFoundException e) {
            log.error("Error while reading cached data: ", e);
            return null;
        } catch (IOException io) {
            log.error("Error while reading cached data: ", io);
            return null;
        } catch (ClassNotFoundException e) {
            log.error("Error while reading cached data: ", e);
            return null;
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
