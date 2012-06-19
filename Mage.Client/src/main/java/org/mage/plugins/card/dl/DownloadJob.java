/**
 * DownloadJob.java
 * 
 * Created on 25.08.2010
 */

package org.mage.plugins.card.dl;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;

import org.mage.plugins.card.dl.beans.properties.Property;
import org.mage.plugins.card.dl.lm.AbstractLaternaBean;


/**
 * The class DownloadJob.
 * 
 * @version V0.0 25.08.2010
 * @author Clemens Koza
 */
public class DownloadJob extends AbstractLaternaBean {
    public static enum State {
        NEW, WORKING, FINISHED, ABORTED;
    }

    private final String              name;
    private final Source              source;
    private final Destination         destination;
    private final Property<State>     state    = properties.property("state", State.NEW);
    private final Property<String>    message  = properties.property("message");
    private final Property<Exception> error    = properties.property("error");
    private final BoundedRangeModel   progress = new DefaultBoundedRangeModel();

    public DownloadJob(String name, Source source, Destination destination) {
        this.name = name;
        this.source = source;
        this.destination = destination;
    }

    /**
     * Sets the job's state. If the state is {@link State#ABORTED}, it instead sets the error to "ABORTED"
     */
    public void setState(State state) {
        if(state == State.ABORTED) setError("ABORTED");
        else this.state.setValue(state);
    }

    /**
     * Sets the job's state to {@link State#ABORTED} and the error message to the given message. Logs a warning
     * with the given message.
     */
    public void setError(String message) {
        setError(message, null);
    }

    /**
     * Sets the job's state to {@link State#ABORTED} and the error to the given exception. Logs a warning with the
     * given exception.
     */
    public void setError(Exception error) {
        setError(null, error);
    }

    /**
     * Sets the job's state to {@link State#ABORTED} and the error to the given exception. Logs a warning with the
     * given message and exception.
     */
    public void setError(String message, Exception error) {
        if(message == null) message = error.toString();
        log.warn(message, error);
        this.state.setValue(State.ABORTED);
        this.error.setValue(error);
        this.message.setValue(message);
    }

    /**
     * Sets the job's message.
     */
    public void setMessage(String message) {
        this.message.setValue(message);
    }

    public BoundedRangeModel getProgress() {
        return progress;
    }

    public State getState() {
        return state.getValue();
    }

    public Exception getError() {
        return error.getValue();
    }

    public String getMessage() {
        return message.getValue();
    }


    public String getName() {
        return name;
    }

    public Source getSource() {
        return source;
    }

    public Destination getDestination() {
        return destination;
    }

    public static Source fromURL(final String url) {
        return fromURL(null, url);
    }

    public static Source fromURL(final URL url) {
        return fromURL(null, url);
    }

    public static Source fromURL(final Proxy proxy, final String url) {
        return new Source() {
            private URLConnection c;

            public URLConnection getConnection() throws IOException {
                if(c == null) c = proxy == null? new URL(url).openConnection():new URL(url).openConnection(proxy);
                return c;
            }

            @Override
            public InputStream open() throws IOException {
                return getConnection().getInputStream();
            }

            @Override
            public int length() throws IOException {
                return getConnection().getContentLength();
            }
        };
    }

    public static Source fromURL(final Proxy proxy, final URL url) {
        return new Source() {
            private URLConnection c;

            public URLConnection getConnection() throws IOException {
                if(c == null) c = proxy == null? url.openConnection():url.openConnection(proxy);
                return c;
            }

            @Override
            public InputStream open() throws IOException {
                return getConnection().getInputStream();
            }

            @Override
            public int length() throws IOException {
                return getConnection().getContentLength();
            }
        };
    }

    public static Destination toFile(final String file) {
        return toFile(new File(file));
    }

    public static Destination toFile(final File file) {
        return new Destination() {
            @Override
            public OutputStream open() throws IOException {
                File parent = file.getAbsoluteFile().getParentFile();
                //Trying to create the directory before checking it exists makes it threadsafe
                if(!parent.mkdirs() && !parent.exists()) throw new IOException(parent
                        + ": directory could not be created");
                return new FileOutputStream(file);
            }

            @Override
            public boolean exists() {
                return file.isFile();
            }

            @Override
            public void delete() throws IOException {
                if(file.exists() && !file.delete()) throw new IOException(file + " couldn't be deleted");
            }
        };
    }

    public static interface Source {
        public InputStream open() throws IOException;

        public int length() throws IOException;
    }

    public static interface Destination {
        public OutputStream open() throws IOException;

        public boolean exists() throws IOException;

        public void delete() throws IOException;
    }
}
