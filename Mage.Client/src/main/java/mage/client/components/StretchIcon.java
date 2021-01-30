package mage.client.components;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.net.URL;

/**
 * An <CODE>Icon</CODE> that scales its image to fill the component area, excluding any border or insets, optionally maintaining the image's
 * aspect ratio by padding and centering the scaled image horizontally or vertically.
 * <p>
 * The class is a drop-in replacement for <CODE>ImageIcon</CODE>, except that the no-argument constructor is not supported.
 * <p>
 * As the size of the Icon is determined by the size of the component in which it is displayed, <CODE>StretchIcon</CODE> must only be used
 * in conjunction with a component and layout that does not depend on the size of the component's Icon.
 * <p>
 * Source: https://stackoverflow.com/a/34514866/1276632
 *
 * @author Darryl
 * @version 1.1 01/15/2016
 */
public class StretchIcon extends ImageIcon {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * Determines whether the aspect ratio of the image is maintained. Set to <code>false</code> to allow th image to distort to fill the
     * component.
     */
    protected boolean proportionate = true;

    /**
     * Creates a <CODE>StretchIcon</CODE> from an array of bytes.
     *
     * @param imageData an array of pixels in an image format supported by the AWT Toolkit, such as GIF, JPEG, or (as of 1.3) PNG
     * @see ImageIcon#ImageIcon(byte[])
     */
    public StretchIcon(byte[] imageData) {
        super(imageData);
    }

    /**
     * Creates a <CODE>StretchIcon</CODE> from an array of bytes with the specified behavior.
     *
     * @param imageData     an array of pixels in an image format supported by the AWT Toolkit, such as GIF, JPEG, or (as of 1.3) PNG
     * @param proportionate <code>true</code> to retain the image's aspect ratio, <code>false</code> to allow distortion of the image to
     *                      fill the component.
     * @see ImageIcon#ImageIcon(byte[])
     */
    public StretchIcon(byte[] imageData, boolean proportionate) {
        super(imageData);
        this.proportionate = proportionate;
    }

    /**
     * Creates a <CODE>StretchIcon</CODE> from an array of bytes.
     *
     * @param imageData   an array of pixels in an image format supported by the AWT Toolkit, such as GIF, JPEG, or (as of 1.3) PNG
     * @param description a brief textual description of the image
     * @see ImageIcon#ImageIcon(byte[], String)
     */
    public StretchIcon(byte[] imageData, String description) {
        super(imageData, description);
    }

    /**
     * Creates a <CODE>StretchIcon</CODE> from an array of bytes with the specified behavior.
     *
     * @param imageData     an array of pixels in an image format supported by the AWT Toolkit, such as GIF, JPEG, or (as of 1.3) PNG
     * @param description   a brief textual description of the image
     * @param proportionate <code>true</code> to retain the image's aspect ratio, <code>false</code> to allow distortion of the image to
     *                      fill the component.
     * @see ImageIcon#ImageIcon(byte[])
     * @see ImageIcon#ImageIcon(byte[], String)
     */
    public StretchIcon(byte[] imageData, String description, boolean proportionate) {
        super(imageData, description);
        this.proportionate = proportionate;
    }

    /**
     * Creates a <CODE>StretchIcon</CODE> from the image.
     *
     * @param image the image
     * @see ImageIcon#ImageIcon(Image)
     */
    public StretchIcon(Image image) {
        super(image);
    }

    /**
     * Creates a <CODE>StretchIcon</CODE> from the image with the specified behavior.
     *
     * @param image         the image
     * @param proportionate <code>true</code> to retain the image's aspect ratio, <code>false</code> to allow distortion of the image to
     *                      fill the component.
     * @see ImageIcon#ImageIcon(Image)
     */
    public StretchIcon(Image image, boolean proportionate) {
        super(image);
        this.proportionate = proportionate;
    }

    /**
     * Creates a <CODE>StretchIcon</CODE> from the image.
     *
     * @param image       the image
     * @param description a brief textual description of the image
     * @see ImageIcon#ImageIcon(Image, String)
     */
    public StretchIcon(Image image, String description) {
        super(image, description);
    }

    /**
     * Creates a <CODE>StretchIcon</CODE> from the image with the specified behavior.
     *
     * @param image         the image
     * @param description   a brief textual description of the image
     * @param proportionate <code>true</code> to retain the image's aspect ratio, <code>false</code> to allow distortion of the image to
     *                      fill the component.
     * @see ImageIcon#ImageIcon(Image, String)
     */
    public StretchIcon(Image image, String description, boolean proportionate) {
        super(image, description);
        this.proportionate = proportionate;
    }

    /**
     * Creates a <CODE>StretchIcon</CODE> from the specified file.
     *
     * @param filename a String specifying a filename or path
     * @see ImageIcon#ImageIcon(String)
     */
    public StretchIcon(String filename) {
        super(filename);
    }

    /**
     * Creates a <CODE>StretchIcon</CODE> from the specified file with the specified behavior.
     *
     * @param filename      a String specifying a filename or path
     * @param proportionate <code>true</code> to retain the image's aspect ratio, <code>false</code> to allow distortion of the image to
     *                      fill the component.
     * @see ImageIcon#ImageIcon(String)
     */
    public StretchIcon(String filename, boolean proportionate) {
        super(filename);
        this.proportionate = proportionate;
    }

    /**
     * Creates a <CODE>StretchIcon</CODE> from the specified file.
     *
     * @param filename    a String specifying a filename or path
     * @param description a brief textual description of the image
     * @see ImageIcon#ImageIcon(String, String)
     */
    public StretchIcon(String filename, String description) {
        super(filename, description);
    }

    /**
     * Creates a <CODE>StretchIcon</CODE> from the specified file with the specified behavior.
     *
     * @param filename      a String specifying a filename or path
     * @param description   a brief textual description of the image
     * @param proportionate <code>true</code> to retain the image's aspect ratio, <code>false</code> to allow distortion of the image to
     *                      fill the component.
     * @see ImageIcon#ImageIcon(Image, String)
     */
    public StretchIcon(String filename, String description, boolean proportionate) {
        super(filename, description);
        this.proportionate = proportionate;
    }

    /**
     * Creates a <CODE>StretchIcon</CODE> from the specified URL.
     *
     * @param location the URL for the image
     * @see ImageIcon#ImageIcon(URL)
     */
    public StretchIcon(URL location) {
        super(location);
    }

    /**
     * Creates a <CODE>StretchIcon</CODE> from the specified URL with the specified behavior.
     *
     * @param location      the URL for the image
     * @param proportionate <code>true</code> to retain the image's aspect ratio, <code>false</code> to allow distortion of the image to
     *                      fill the component.
     * @see ImageIcon#ImageIcon(URL)
     */
    public StretchIcon(URL location, boolean proportionate) {
        super(location);
        this.proportionate = proportionate;
    }

    /**
     * Creates a <CODE>StretchIcon</CODE> from the specified URL.
     *
     * @param location    the URL for the image
     * @param description a brief textual description of the image
     * @see ImageIcon#ImageIcon(URL, String)
     */
    public StretchIcon(URL location, String description) {
        super(location, description);
    }

    /**
     * Creates a <CODE>StretchIcon</CODE> from the specified URL with the specified behavior.
     *
     * @param location      the URL for the image
     * @param description   a brief textual description of the image
     * @param proportionate <code>true</code> to retain the image's aspect ratio, <code>false</code> to allow distortion of the image to
     *                      fill the component.
     * @see ImageIcon#ImageIcon(URL, String)
     */
    public StretchIcon(URL location, String description, boolean proportionate) {
        super(location, description);
        this.proportionate = proportionate;
    }

    /**
     * Paints the icon. The image is reduced or magnified to fit the component to which it is painted.
     * <p>
     * If the proportion has not been specified, or has been specified as <code>true</code>, the aspect ratio of the image will be preserved
     * by padding and centering the image horizontally or vertically. Otherwise the image may be distorted to fill the component it is
     * painted to.
     * <p>
     * If this icon has no image observer,this method uses the <code>c</code> component as the observer.
     *
     * @param c the component to which the Icon is painted. This is used as the observer if this icon has no image observer
     * @param g the graphics context
     * @param x not used.
     * @param y not used.
     * @see ImageIcon#paintIcon(Component, Graphics, int, int)
     */
    @Override
    public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
        Image image = getImage();
        if (image == null) {
            return;
        }
        Insets insets = ((Container) c).getInsets();
        x = insets.left;
        y = insets.top;

        int w = c.getWidth() - x - insets.right;
        int h = c.getHeight() - y - insets.bottom;

        if (proportionate) {
            int iw = image.getWidth(c);
            int ih = image.getHeight(c);

            if ((iw * h) < (ih * w)) {
                iw = (h * iw) / ih;
                x += (w - iw) / 2;
                w = iw;
            } else {
                ih = (w * ih) / iw;
                y += (h - ih) / 2;
                h = ih;
            }
        }
        ImageObserver io = getImageObserver();

        /*
         * Added this code to generate nicer looking results when scaling. - bspkrs
         * BEGIN CHANGES
         */
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d = bi.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(image, 0, 0, w, h, io == null ? c : io);
        g2d.dispose();
        /*
         * END CHANGES
         */

        g.drawImage(bi, x, y, w, h, io == null ? c : io);
    }

    /**
     * Overridden to return 0. The size of this Icon is determined by the size of the component.
     *
     * @return 0
     */
    @Override
    public int getIconWidth() {
        return 0;
    }

    /**
     * Overridden to return 0. The size of this Icon is determined by the size of the component.
     *
     * @return 0
     */
    @Override
    public int getIconHeight() {
        return 0;
    }
}