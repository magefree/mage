package mage.client.remote;

import mage.client.dialog.PreferencesDialog;
import mage.remote.Connection;
import mage.utils.MageVersion;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.Map;

/**
 * Network: proxy class to set up and use network connections like URLConnection
 * <p>
 * It used stream logic for data access
 * <p>
 * For text:
 * - download text data by XmageURLConnection.downloadText
 * <p>
 * For binary data (e.g. file download):
 * - get stream by XmageURLConnection.downloadBinary
 * - save stream to file or process
 * <p>
 * TODO: no needs in POST requests (support only GET), but can be added later for another third party APIs
 *
 * @author JayDi85
 */
public class XmageURLConnection {

    private static final MageVersion version = new MageVersion(XmageURLConnection.class);
    private static final Logger logger = Logger.getLogger(XmageURLConnection.class);

    private static final int CONNECTION_STARTING_TIMEOUT_MS = 10000;
    private static final int CONNECTION_READING_TIMEOUT_MS = 60000;

    final String url;
    Proxy proxy = null;
    HttpURLConnection connection = null;
    HttpLoggingType loggingType = HttpLoggingType.ERRORS;

    public XmageURLConnection(String url) {
        this.url = url;
    }

    // example: 404 Not Found xxx
    enum HttpLoggingType {
        NONE,
        ERRORS,
        ALL
    }

    /**
     * Add additional headers like non standard user agent, etc
     */
    public void setRequestHeaders(Map<String, String> additionalHeaders) {
        makeSureConnectionStarted();

        for (String key : additionalHeaders.keySet()) {
            this.connection.setRequestProperty(key, additionalHeaders.get(key));
        }
    }

    /**
     * Connect to server
     */
    public void startConnection() {
        initDefaultProxy();

        try {
            URL url = new URL(this.url);

            // proxy settings
            if (this.proxy != null) {
                this.connection = (HttpURLConnection) url.openConnection(this.proxy);
            } else {
                this.connection = (HttpURLConnection) url.openConnection();
            }

            // additional settings
            this.connection.setConnectTimeout(CONNECTION_STARTING_TIMEOUT_MS);
            this.connection.setReadTimeout(CONNECTION_READING_TIMEOUT_MS);

            initDefaultHeaders();
        } catch (IOException e) {
            this.connection = null;
        }
    }

    public void initDefaultProxy() {
        Connection.ProxyType configProxyType = Connection.ProxyType.valueByText(PreferencesDialog.getCachedValue(PreferencesDialog.KEY_PROXY_TYPE, "None"));
        Proxy.Type type;
        switch (configProxyType) {
            case HTTP:
                type = Proxy.Type.HTTP;
                break;
            case SOCKS:
                type = Proxy.Type.SOCKS;
                break;
            case NONE:
            default:
                type = Proxy.Type.DIRECT;
                break;
        }

        this.proxy = Proxy.NO_PROXY;
        if (type != Proxy.Type.DIRECT) {
            try {
                String address = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_PROXY_ADDRESS, "");
                int port = Integer.parseInt(PreferencesDialog.getCachedValue(PreferencesDialog.KEY_PROXY_PORT, "80"));
                this.proxy = new Proxy(type, new InetSocketAddress(address, port));
            } catch (Exception e) {
                throw new RuntimeException("Network: can't create proxy, check your settings or reset it - " + e, e);
            }
        }
    }

    private void initDefaultHeaders() {
        // warning, do not add Accept-Encoding - it processing inside URLConnection for http/https links (trying to use gzip by default)

        // user agent due standard notation User-Agent: <product> / <product-version> <comment>
        // warning, dot not add os, language and other details
        this.connection.setRequestProperty("User-Agent", String.format("XMage/%s build: %s",
                version.toString(false), version.getBuildTime()));
    }

    /**
     * Connect to server's resource
     */
    public void connect() throws IOException {
        makeSureConnectionStarted();

        this.connection.connect();
        printHttpResult();
    }

    public boolean isConnected() {
        return this.connection != null;
    }

    /**
     * Get http status code from a web server (200 for ok, -1 for not connect, 400 and other for errors)
     */
    public int getResponseCode() {
        makeSureConnectionStarted();

        try {
            return this.connection.getResponseCode();
        } catch (IOException ignore) {
            return -1;
        }
    }

    /**
     * Get total file size to download (call it after start connection)
     * -1 for unknown size
     * 0 for text or small files
     * <p>
     * Warning, result depends on Accept-Encoding, so use it for information only
     */
    public int getContentLength() {
        makeSureConnectionStarted();

        return this.connection.getContentLength();
    }

    /**
     * Get http status message from a web server like Not Found
     */
    public String getResponseMessage() {
        makeSureConnectionStarted();

        try {
            return this.connection.getResponseMessage();
        } catch (IOException ignore) {
            return "";
        }
    }

    /**
     * Get returned html from a web server
     */
    public String getErrorResponseAsString() {
        makeSureConnectionStarted();

        java.util.Scanner s = new java.util.Scanner(this.connection.getErrorStream()).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    /**
     * Get non-text data as stream, e.g. binary file content
     */
    public InputStream getGoodResponseAsStream() throws IOException {
        makeSureConnectionStarted();

        return this.connection.getInputStream();
    }

    /**
     * Get text data as string, e.g. html document
     */
    public String getGoodResponseAsString() {
        makeSureConnectionStarted();
        StringBuffer tmp = new StringBuffer();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(this.getGoodResponseAsStream()));
            String line;
            while ((line = in.readLine()) != null) {
                tmp.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Network: can't get text data from " + this.url + " - " + e, e);
        }

        return String.valueOf(tmp);
    }

    private void makeSureConnectionStarted() {
        if (!isConnected()) {
            throw new IllegalArgumentException("Wrong code usage: must call startConnection first", new Throwable());
        }
    }

    /**
     * Fast download of text data
     *
     * @return downloaded text on OK 200 response or empty on any other errors
     */
    public static String downloadText(String resourceUrl) {
        XmageURLConnection con = new XmageURLConnection(resourceUrl);
        con.startConnection();
        if (con.isConnected()) {
            try {
                con.connect();
                if (con.getResponseCode() == 200) {
                    return con.getGoodResponseAsString();
                }
            } catch (IOException e) {
                logger.error(e, e);
            }
        }
        return "";
    }

    /**
     * Fast download of binary data
     *
     * @return stream on OK 200 response or null on any other errors
     */
    public static InputStream downloadBinary(String resourceUrl) {
        XmageURLConnection con = new XmageURLConnection(resourceUrl);
        con.startConnection();
        if (con.isConnected()) {
            try {
                con.connect();
                if (con.getResponseCode() == 200) {
                    return con.getGoodResponseAsStream();
                }
            } catch (IOException e) {
                logger.error(e, e);
            }
        }
        return null;
    }

    private void printHttpResult() {
        if (this.connection == null) {
            return;
        }

        boolean needPrint;
        switch (this.loggingType) {
            case NONE:
                needPrint = false;
                break;
            case ERRORS:
                needPrint = getResponseCode() != HttpURLConnection.HTTP_OK;
                break;
            case ALL:
            default:
                needPrint = true;
        }

        if (needPrint) {
            logger.info(String.format("http request %d %s %s", this.getResponseCode(), this.getResponseMessage(), this.url));
        }
    }
}