package org.mage.plugins.card.dl;

import org.mage.plugins.card.dl.beans.properties.Property;
import org.mage.plugins.card.dl.lm.AbstractLaternaBean;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Download: download job to load one resource, used for symbols
 *
 * @author Clemens Koza, JayDi85
 */
public class DownloadJob extends AbstractLaternaBean {

    public enum State {
        NEW, PREPARING, WORKING, FINISHED, ABORTED
    }

    private final String name;
    private String url;
    private final Destination destination;
    private final boolean forceToDownload; // download image everytime, do not keep old image
    private final Property<State> state = properties.property("state", State.NEW);
    private final Property<String> message = properties.property("message");
    private final Property<Exception> error = properties.property("error");
    private final BoundedRangeModel progress = new DefaultBoundedRangeModel();

    public DownloadJob(String name, String url, Destination destination, boolean forceToDownload) {
        this.name = name;
        this.url = url;
        this.destination = destination;
        this.forceToDownload = forceToDownload;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
            message = "Download: " + name + " from " + url + " caused error - " + error;
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

    @Override
    public String getMessage() {
        return message.getValue();
    }

    public String getName() {
        return name;
    }

    public Destination getDestination() {
        return destination;
    }

    public boolean isForceToDownload() {
        return forceToDownload;
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

    public interface Destination {

        OutputStream open() throws IOException;

        boolean exists() throws IOException;

        boolean isValid() throws IOException;

        void delete() throws IOException;
    }
}
