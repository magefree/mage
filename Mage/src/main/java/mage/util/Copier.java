

package mage.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author BetaSteward_at_googlemail.com
 * @param <T>
 */
public class Copier<T> {

    private static ClassLoader loader;

    public static void setLoader(ClassLoader loader) {
        Copier.loader = loader;
    }

    public T copy(T obj) {
        T copy = null;

        FastByteArrayOutputStream fbos = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try {
            fbos = new FastByteArrayOutputStream();
            out = new ObjectOutputStream(fbos);

            // Write the object out to a byte array
            out.writeObject(obj);
            out.flush();

            // Retrieve an input stream from the byte array and read
            // a copy of the object back in.
            in = new CopierObjectInputStream(loader, fbos.getInputStream());
            copy = (T) in.readObject();
        }
        catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            StreamUtil.closeQuietly(fbos);
            StreamUtil.closeQuietly(out);
            StreamUtil.closeQuietly(in);
        }
        return copy;

    }
}
