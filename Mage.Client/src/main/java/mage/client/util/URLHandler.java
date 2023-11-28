package mage.client.util;

import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.swing.*;

/**
 *
 * @author Dahny
 */
public class URLHandler {

    private static MouseAdapter currentMouseAdapter;

    /**
     * This method makes a URL in a message click-able and converts the message
     * into HTML.
     *
     * @param message: The selected message
     * @param label: The message of the day label
     */
    public static void handleMessage(String message, JLabel label) {
        String url = detectURL(message);

        if (!url.equals("")) {
            label.addMouseListener(createMouseAdapter(url));
        }

        label.setText(convertToHTML(message));
    }

    public static void RemoveMouseAdapter(JLabel label) {
        label.removeMouseListener(currentMouseAdapter);
        currentMouseAdapter = null;
    }

    private static MouseAdapter createMouseAdapter(String url) {
        currentMouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!SwingUtilities.isLeftMouseButton(e)) {
                    return;
                }
                if (e.getClickCount() > 0) {
                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            URI uri = new URI(url);
                            desktop.browse(uri);
                        } catch (IOException | URISyntaxException ex) {
                            // do nothing
                        }
                    }
                }
            }
        };

        return currentMouseAdapter;
    }

    public static String convertToHTML(String input) {
        String s = input;
        String output = "<html>";
        // separate the input by spaces
        String[] parts = s.split("\\s+");

        for (String item : parts) {
            try {
                URL url = new URL(item);
                // The item is already a valid URL
                output = output + "<a href=\"" + url + "\">" + url + "</a> ";

            } catch (MalformedURLException e) {
                //The item might still be a URL
                if (item.startsWith("www.")) {
                    output = output + "<a href=\"" + item + "\">" + item + "</a> ";
                } else {
                    output = output + item + " ";
                }

            }
        }

        output = output + "</html>";
        return output;
    }

    public static String detectURL(String input) {
        String s = input;
        String output = "";
        // separate the input by spaces
        String[] parts = s.split("\\s+");

        for (String item : parts) {
            try {
                URL url = new URL(item);
                // The item is already a valid URL
                output = url.toString();
            } catch (MalformedURLException e) {
                //The item might still be a URL
                if (item.startsWith("www.")) {
                    output = "http://" + item;
                }
            }
        }

        return output;
    }

}
