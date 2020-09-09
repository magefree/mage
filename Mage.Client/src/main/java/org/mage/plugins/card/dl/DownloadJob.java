package org.mage.plugins.card.dl;

import org.mage.plugins.card.dl.beans.properties.Property;
import org.mage.plugins.card.dl.lm.AbstractLaternaBean;
import org.mage.plugins.card.utils.CardImageUtils;

import javax.swing.*;
import java.io.*;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

/**
 * Downloader job to download one resource
 *
 * @author Clemens Koza, JayDi85
 */
public class DownloadJob extends AbstractLaternaBean {

    public enum State {
        NEW, PREPARING, WORKING, FINISHED, ABORTED
    }

    private final String name;
    private Source source;
    private final Destination destination;
    private final Property<State> state = properties.property("state", State.NEW);
    private final Property<String> message = properties.property("message");
    private final Property<Exception> error = properties.property("error");
    private final BoundedRangeModel progress = new DefaultBoundedRangeModel();

    public DownloadJob(String name, Source source, Destination destination) {
        this.name = name;
        this.source = source;
        this.destination = destination;
    }

    /**
     * Sets the job's state. If the state is {@link State#ABORTED}, it instead
     * sets the error to "ABORTED"
     *
     * @param state
     */
    public void setState(State state) {
        if (state == State.ABORTED) {
            setError("ABORTED");
        } else {
            this.state.setValue(state);
        }
    }

    /**
     * Sets the job's state to {@link State#ABORTED} and the error message to
     * the given message. Logs a warning with the given message.
     *
     * @param message
     */
    public void setError(String message) {
        setError(message, null);
    }

    /**
     * Sets the job's state to {@link State#ABORTED} and the error to the given
     * exception. Logs a warning with the given exception.
     *
     * @param error
     */
    public void setError(Exception error) {
        setError(null, error);
    }

    /**
     * Sets the job's state to {@link State#ABORTED} and the error to the given
     * exception. Logs a warning with the given message and exception.
     *
     * @param message
     * @param error
     */
    public void setError(String message, Exception error) {
        if (message == null) {
            message = "Download of " + name + " from " + source.toString() + " caused error: " + error.toString();
        }
        this.state.setValue(State.ABORTED);
        this.error.setValue(error);
        this.message.setValue(message);
    }

    /**
     * Inner prepare cycle from new to working
     */
    public void doPrepareAndStartWork() {
        if (this.state.getValue() != State.NEW) {
            setError("Can't call prepare at this point.");
            return;
        }

        this.state.setValue(State.PREPARING);

        try {
            onPreparing();
        } catch (Exception e) {
            setError("Prepare error: " + e.getMessage(), e);
            return;
        }

        // can continue
        this.state.setValue(State.WORKING);
    }


    /**
     * Prepare code to override in custom download tasks (it's calls before work start)
     */
    public void onPreparing() throws Exception {
    }

    /**
     * Sets the job's message.
     *
     * @param message
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

    public void setSource(Source source) {
        this.source = source;
    }

    public Destination getDestination() {
        return destination;
    }

    public static Source fromURL(final String url) {
        return fromURL(CardImageUtils.getProxyFromPreferences(), url);
    }

    public static Source fromURL(final URL url) {
        return fromURL(CardImageUtils.getProxyFromPreferences(), url);
    }

    public static Source fromURL(final Proxy proxy, final String url) {
        return new Source() {
            private URLConnection c;

            public URLConnection getConnection() throws IOException {
                if (c == null) {
                    c = proxy == null ? new URL(url).openConnection() : new URL(url).openConnection(proxy);
                }
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

            @Override
            public String toString() {
                return proxy != null ? proxy.type().toString() + ' ' : url;
            }

        };
    }

    public static Source fromURL(final Proxy proxy, final URL url) {
        return new Source() {
            private URLConnection c;

            public URLConnection getConnection() throws IOException {
                if (c == null) {
                    c = proxy == null ? url.openConnection() : url.openConnection(proxy);
                }
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

            @Override
            public String toString() {
                return proxy != null ? proxy.type().toString() + ' ' : String.valueOf(url);
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
                if (!parent.mkdirs() && !parent.exists()) {
                    throw new IOException(parent + ": directory could not be created");
                }
                return new FileOutputStream(file);
            }

            @Override
            public boolean isValid() throws IOException {
                if (file.isFile()) {
                    return file.length() > 0;
                }
                return false;
            }

            @Override
            public boolean exists() {
                return file.isFile();
            }

            @Override
            public void delete() throws IOException {
                if (file.exists() && !file.delete()) {
                    throw new IOException(file + " couldn't be deleted");
                }
            }
        };
    }

    public interface Source {

        InputStream open() throws IOException;

        int length() throws IOException;
    }

    public interface Destination {

        OutputStream open() throws IOException;

        boolean exists() throws IOException;

        boolean isValid() throws IOException;

        void delete() throws IOException;
    }
}
