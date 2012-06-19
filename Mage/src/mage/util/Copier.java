/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
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
 */
public class Copier<T> {

    private static ClassLoader loader;

    public static void setLoader(ClassLoader loader) {
        Copier.loader = loader;
    }

    public T copy(T obj) {
        T copy = null;
        try {
            FastByteArrayOutputStream fbos = new FastByteArrayOutputStream();
            ObjectOutputStream out= new ObjectOutputStream(fbos);

            // Write the object out to a byte array
            out.writeObject(obj);
            out.flush();
            out.close();

            // Retrieve an input stream from the byte array and read
            // a copy of the object back in.
            ObjectInputStream in = new CopierObjectInputStream(loader, fbos.getInputStream());
            copy = (T) in.readObject();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        catch(ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        return copy;

    }

    public byte[] copyCompressed(T obj) {
        try {
            FastByteArrayOutputStream fbos = new FastByteArrayOutputStream();
            ObjectOutputStream out= new ObjectOutputStream(new GZIPOutputStream(fbos));

            // Write the object out to a byte array
            out.writeObject(obj);
            out.flush();
            out.close();

            byte[] copy = new byte[fbos.getSize()];
            System.arraycopy(fbos.getByteArray(), 0, copy, 0, fbos.getSize());
            return copy;
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public T uncompressCopy(byte[] buffer) {
        T copy = null;
        try {
            ObjectInputStream in = new CopierObjectInputStream(loader, new GZIPInputStream(new ByteArrayInputStream(buffer)));
            copy = (T) in.readObject();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        catch(ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        return copy;
    }
}
