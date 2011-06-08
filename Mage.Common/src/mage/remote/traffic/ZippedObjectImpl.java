package mage.remote.traffic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Implementation for compressing and decompressing objects using {@link GZIPInputStream} and {@link GZIPOutputStream}.
 * Can be used to send any {@link Object} over internet to reduce traffic usage.
 *
 * @author ayrat
 */
public class ZippedObjectImpl<T> implements ZippedObject<T>, Serializable {

	private byte[] data;

	public ZippedObjectImpl(T object) {
		zip(object);
	}
	
	public void zip(T object) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			GZIPOutputStream gz = new GZIPOutputStream(bos);
			ObjectOutputStream oos = new ObjectOutputStream(gz);
			oos.writeObject(object);
			oos.close();
			data = bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public T unzip() {
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			GZIPInputStream gz = new GZIPInputStream(bis);
			ObjectInputStream ois = new ObjectInputStream(gz);
			Object o = ois.readObject();
			return (T)o;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static final long serialVersionUID = 1L;
}
