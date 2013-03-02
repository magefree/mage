package org.mage.card.arcane;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.ViewportLayout;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.ImageView;

/**
 * UI utility functions.
 */
public class UI {
    private static ConcurrentMap<URI, Image> imageCache = new ConcurrentHashMap<URI, Image>();

    public static JToggleButton getToggleButton () {
        JToggleButton button = new JToggleButton();
        button.setMargin(new Insets(2, 4, 2, 4));
        return button;
    }

    public static JButton getButton () {
        JButton button = new JButton();
        button.setMargin(new Insets(2, 4, 2, 4));
        return button;
    }

    public static void setTitle (JPanel panel, String title) {
        Border border = panel.getBorder();
        if (border instanceof TitledBorder) {
            ((TitledBorder) panel.getBorder()).setTitle(title);
            panel.repaint();
        } else {
            panel.setBorder(BorderFactory.createTitledBorder(title));
        }
    }

    public static ImageIcon getImageIcon (String path) {
        try {
            InputStream stream;
            stream = UI.class.getResourceAsStream(path);
            if (stream == null && new File(path).exists()) {
                stream = new FileInputStream(path);
            }
            if (stream == null) {
                throw new RuntimeException("Image not found: " + path);
            }
            byte[] data = new byte[stream.available()];
            stream.read(data);
            return new ImageIcon(data);
        } catch (IOException ex) {
            throw new RuntimeException("Error reading image: " + path);
        }
    }

    public static void setHTMLEditorKit (JEditorPane editorPane) {
        editorPane.getDocument().putProperty("imageCache", imageCache); // Read internally by ImageView, but never written.

        editorPane.setEditorKit(new HTMLEditorKit() {
            private static final long serialVersionUID = -54602188235105448L;

            @Override
            public ViewFactory getViewFactory () {
                return new HTMLFactory() {
                    @Override
                    public View create(Element elem) {
                        Object o = elem.getAttributes().getAttribute(StyleConstants.NameAttribute);
                        if (o instanceof HTML.Tag) {
                            HTML.Tag kind = (HTML.Tag) o;
                            if (kind == HTML.Tag.IMG) {
                                return new ImageView(elem) {
                                    @Override
                                    public URL getImageURL() {
                                        URL url = super.getImageURL();
                                        URI uri = null;
                                        try {
                                            uri = url.toURI();
                                        } catch (URISyntaxException ex) {
                                        }
                                        // Put an image into the cache to be read by other ImageView methods.
                                        if (uri != null && imageCache.get(uri) == null) {
                                            imageCache.put(uri, Toolkit.getDefaultToolkit().createImage(url));
                                        }
                                        return url;
                                    }
                                };
                            }
                        }
                        return super.create(elem);
                    }
                };
            }
        });
    }

    public static void setVerticalScrollingView (JScrollPane scrollPane, final Component view) {
        final JViewport viewport = new JViewport();
        viewport.setLayout(new ViewportLayout() {
            private static final long serialVersionUID = 7701568740313788935L;
            @Override
            public void layoutContainer (Container parent) {
                viewport.setViewPosition(new Point(0, 0));
                Dimension viewportSize = viewport.getSize();
                int width = viewportSize.width;
                int height = Math.max(view.getPreferredSize().height, viewportSize.height);
                viewport.setViewSize(new Dimension(width, height));
            }
        });
        viewport.setView(view);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setViewport(viewport);
    }

    public static String getDisplayManaCost (String manaCost) {
        manaCost = manaCost.replace("/", "");
        // A pipe in the cost means "process left of the pipe as the card color, but display right of the pipe as the cost".
        int pipePosition = manaCost.indexOf("{|}");
        if (pipePosition != -1) {
            manaCost = manaCost.substring(pipePosition + 3);
        }
        return manaCost;
    }

    public static void invokeLater (Runnable runnable) {
        EventQueue.invokeLater(runnable);
    }

    public static void invokeAndWait (Runnable runnable) {
        if (EventQueue.isDispatchThread()) {
            runnable.run();
            return;
        }
        try {
            EventQueue.invokeAndWait(runnable);
        } catch (InterruptedException ex) {
        } catch (InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void setSystemLookAndFeel () {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.err.println("Error setting look and feel:");
            ex.printStackTrace();
        }
    }

    public static void setDefaultFont (Font font) {
        for (Object key : Collections.list(UIManager.getDefaults().keys())) {
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(key, font);
            }
        }
    }
}
