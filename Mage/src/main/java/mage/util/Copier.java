

package mage.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

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
            StreamUtils.closeQuietly(fbos);
            StreamUtils.closeQuietly(out);
            StreamUtils.closeQuietly(in);
        }
        return copy;

    }

    public byte[] copyCompressed(T obj) {
        FastByteArrayOutputStream fbos = null;
        ObjectOutputStream out = null;
        try {
            fbos = new FastByteArrayOutputStream();
            out = new ObjectOutputStream(new GZIPOutputStream(fbos));

            // Write the object out to a byte array
            out.writeObject(obj);
            out.flush();

            byte[] copy = new byte[fbos.getSize()];
            System.arraycopy(fbos.getByteArray(), 0, copy, 0, fbos.getSize());
            return copy;
        }
        catch(IOException e) {
            e.printStackTrace();
        } finally {
            StreamUtils.closeQuietly(fbos);
            StreamUtils.closeQuietly(out);
        }
        return null;
    }

    public T uncompressCopy(byte[] buffer) {
        T copy = null;
        try (ObjectInputStream in = new CopierObjectInputStream(loader, new GZIPInputStream(new ByteArrayInputStream(buffer)))) {
            copy = (T) in.readObject();
        }
        catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return copy;
    }
}
