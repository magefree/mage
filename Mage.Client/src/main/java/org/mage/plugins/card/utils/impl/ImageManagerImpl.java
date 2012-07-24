package org.mage.plugins.card.utils.impl;

import mage.client.util.gui.BufferedImageBuilder;
import org.mage.plugins.card.utils.ImageManager;
import org.mage.plugins.card.utils.Transparency;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ImageManagerImpl implements ImageManager {

    private static ImageManagerImpl fInstance = new ImageManagerImpl();

    public static ImageManagerImpl getInstance() {
        return fInstance;
    }
    
    public ImageManagerImpl() {
        init();
    }

    public void init() {
        String[] phases = {"Untap", "Upkeep", "Draw", "Main1",
                "Combat_Start", "Combat_Attack", "Combat_Block", "Combat_Damage", "Combat_End",
                "Main2", "Cleanup", "Next_Turn"};
        phasesImages = new HashMap<String, Image>();
        for (String name : phases) {
            Image image = getImageFromResource("/phases/phase_" + name.toLowerCase() + ".png", new Rectangle(36, 36));
            phasesImages.put(name, image);
        }
    }

    @Override
    public Image getPhaseImage(String phase) {
        return phasesImages.get(phase);
    }

    @Override
    public Image getAppImage() {
        if (appImage == null) {
            Image image = getBufferedImageFromResource("/icon-mage.png");
            appImage = BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB);
        }
        return appImage;
    }

    @Override
    public Image getAppSmallImage() {
        if (appSmallImage == null) {
            Image image = getImageFromResourceTransparent("/icon-mage.png", Color.WHITE, new Rectangle(16, 16));
            appSmallImage = BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB);
        }
        return appSmallImage;
    }

    @Override
    public Image getAppFlashedImage() {
        if (appImageFlashed == null) {
            Image image = getImageFromResourceTransparent("/icon-mage-flashed.png", Color.WHITE, new Rectangle(16, 16));
            appImageFlashed = BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB);
        }
        return appImageFlashed;
    }

    @Override
    public BufferedImage getSicknessImage() {
        if (imageSickness == null) {
            Image image = getImageFromResourceTransparent("/sickness.png", Color.WHITE, new Rectangle(296, 265));
            Toolkit tk = Toolkit.getDefaultToolkit();
            image = tk.createImage(new FilteredImageSource(image.getSource(), new CropImageFilter(0, 0, 200, 285)));
            imageSickness = BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB);
        }
        return imageSickness;
    }

    @Override
    public BufferedImage getDayImage() {
        if (imageDay == null) {
            Image image = getImageFromResourceTransparent("/card/day.png", Color.WHITE, new Rectangle(20, 20));
            imageDay = BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB);
        }
        return imageDay;
    }

    @Override
    public BufferedImage getNightImage() {
        if (imageNight == null) {
            Image image = getImageFromResourceTransparent("/card/night.png", Color.WHITE, new Rectangle(20, 20));
            imageNight = BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB);
        }
        return imageNight;
    }

    @Override
    public Image getDlgCancelButtonImage() {
        if (imageDlgCancelButton == null) {
            imageDlgCancelButton = getBufferedImageFromResource("/dlg/dlg.cancel.png");
        }
        return imageDlgCancelButton;
    }

    @Override
    public Image getDlgActiveCancelButtonImage() {
        if (imageDlgActiveCancelButton == null) {
            imageDlgActiveCancelButton = getBufferedImageFromResource("/dlg/dlg.cancel.hover.png");
        }
        return imageDlgActiveCancelButton;
    }

    @Override
    public Image getDlgAcceptButtonImage() {
        if (imageDlgAcceptButton == null) {
            imageDlgAcceptButton = getBufferedImageFromResource("/dlg/dlg.ok.png");
        }
        return imageDlgAcceptButton;
    }

    @Override
    public Image getDlgActiveAcceptButtonImage() {
        if (imageDlgActiveAcceptButton == null) {
            imageDlgActiveAcceptButton = getBufferedImageFromResource("/dlg/dlg.ok.hover.png");
        }
        return imageDlgActiveAcceptButton;
    }

    @Override
    public Image getDlgPrevButtonImage() {
        if (imageDlgPrevButton == null) {
            imageDlgPrevButton = getBufferedImageFromResource("/dlg/dlg.prev.png");
        }
        return imageDlgPrevButton;
    }

    @Override
    public Image getDlgActivePrevButtonImage() {
        if (imageDlgActivePrevButton == null) {
            imageDlgActivePrevButton = getBufferedImageFromResource("/dlg/dlg.prev.hover.png");
        }
        return imageDlgActivePrevButton;
    }

    @Override
    public Image getDlgNextButtonImage() {
        if (imageDlgNextButton == null) {
            imageDlgNextButton = getBufferedImageFromResource("/dlg/dlg.next.png");
        }
        return imageDlgNextButton;
    }

    @Override
    public Image getDlgActiveNextButtonImage() {
        if (imageDlgActiveNextButton == null) {
            imageDlgActiveNextButton = getBufferedImageFromResource("/dlg/dlg.next.hover.png");
        }
        return imageDlgActiveNextButton;
    }

    protected static Image getImageFromResourceTransparent(String path, Color mask, Rectangle rec) {
        BufferedImage image = null;
        Image imageCardTransparent = null;
        Image resized = null;

        URL imageURL = ImageManager.class.getResource(path);

        try {
            image = ImageIO.read(imageURL);
            imageCardTransparent = Transparency.makeColorTransparent(image, mask);

            resized = imageCardTransparent.getScaledInstance(rec.width, rec.height, java.awt.Image.SCALE_SMOOTH);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resized;
    }

    protected static Image getImageFromResource(String path, Rectangle rec) {
        Image resized = null;

        URL imageURL = ImageManager.class.getResource(path);

        try {
            BufferedImage image = ImageIO.read(imageURL);
            resized = image.getScaledInstance(rec.width, rec.height, java.awt.Image.SCALE_SMOOTH);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resized;
    }

    protected static BufferedImage getBufferedImageFromResource(String path) {
        URL imageURL = ImageManager.class.getResource(path);
        BufferedImage image = null;

        try {
            image = ImageIO.read(imageURL);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return image;
    }

    private static BufferedImage appImage;
    private static BufferedImage appSmallImage;
    private static BufferedImage appImageFlashed;

    private static BufferedImage imageSickness;
    private static BufferedImage imageDay;
    private static BufferedImage imageNight;

    private static BufferedImage imageDlgAcceptButton;
    private static BufferedImage imageDlgActiveAcceptButton;
    private static BufferedImage imageDlgCancelButton;
    private static BufferedImage imageDlgActiveCancelButton;
    private static BufferedImage imageDlgPrevButton;
    private static BufferedImage imageDlgActivePrevButton;
    private static BufferedImage imageDlgNextButton;
    private static BufferedImage imageDlgActiveNextButton;

    private static Map<String, Image> phasesImages;
}
