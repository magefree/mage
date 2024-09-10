package org.mage.plugins.card.images;

import java.awt.image.BufferedImage;

/**
 * @author JayDi85
 */
public class ImageCacheData {
    String path;
    BufferedImage image;

    public ImageCacheData(String path, BufferedImage image) {
        this.path = path;
        this.image = image;
    }

    public String getPath() {
        return path;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
