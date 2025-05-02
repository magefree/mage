package org.mage.card.arcane;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ModernCardResourceLoader {
    private static final Logger LOGGER = Logger.getLogger(ModernCardResourceLoader.class);

    ///////////////////////////////////////////////////////////////////////////
    // Textures for modern frame cards
    protected static TexturePaint loadBackgroundTexture(String name) {
        URL url = ModernCardResourceLoader.class.getResource("/cardrender/background_texture_" + name + ".png");
        ImageIcon icon = new ImageIcon(url);
        BufferedImage img = CardRendererUtils.toBufferedImage(icon.getImage());
        return new TexturePaint(img, new Rectangle(0, 0, img.getWidth(), img.getHeight()));
    }

    protected static BufferedImage loadBackgroundImage(String name) {
        URL url = ModernCardResourceLoader.class.getResource("/cardrender/background_texture_" + name + ".png");
        ImageIcon icon = new ImageIcon(url);
        BufferedImage img = CardRendererUtils.toBufferedImage(icon.getImage());
        return img;
    }

    protected static BufferedImage loadFramePart(String name) {
        URL url = ModernCardResourceLoader.class.getResource("/cardrender/" + name + ".png");
        ImageIcon icon = new ImageIcon(url);
        return CardRendererUtils.toBufferedImage(icon.getImage());
    }

    protected static Font loadFont(String name) {
        try (InputStream in = ModernCardResourceLoader.class.getResourceAsStream("/cardrender/" + name + ".ttf")) {
            return Font.createFont(
                    Font.TRUETYPE_FONT, in);
        } catch (IOException e) {
            LOGGER.info("Failed to load font `" + name + "`, couldn't find resource.");
        } catch (FontFormatException e) {
            LOGGER.info("Failed to load font `" + name + "`, bad format.");
        }
        return new Font("Arial", Font.PLAIN, 1);
    }
}
