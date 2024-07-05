package org.mage.plugins.card.utils.impl;

import mage.abilities.icon.CardIconColor;
import mage.client.dialog.PreferencesDialog;
import mage.client.util.ImageCaches;
import mage.client.util.SoftValuesLoadingCache;
import mage.client.util.gui.BufferedImageBuilder;
import org.apache.log4j.Logger;
import org.mage.card.arcane.SvgUtils;
import org.mage.plugins.card.utils.ImageManager;
import org.mage.plugins.card.utils.Transparency;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * GUI plugin: images and icons with theme support
 *
 * @author JayDi85
 */
public enum ImageManagerImpl implements ImageManager {
    instance;

    private static final Logger logger = Logger.getLogger(ImageManagerImpl.class);

    private static BufferedImage appImage;
    private static BufferedImage appSmallImage;
    private static BufferedImage appImageFlashed;

    private static BufferedImage imageSickness;
    private static BufferedImage imageDay;
    private static BufferedImage imageNight;

    private static BufferedImage imageTokenIcon;
    private static BufferedImage triggeredAbilityIcon;
    private static BufferedImage activatedAbilityIcon;
    private static BufferedImage lookedAtIcon;
    private static BufferedImage revealedIcon;
    private static BufferedImage exileIcon;
    private static BufferedImage imageCopyIcon;
    private static BufferedImage imageCounterGreen;
    private static BufferedImage imageCounterGrey;
    private static BufferedImage imageCounterRed;
    private static BufferedImage imageCounterViolet;

    private static BufferedImage imageDlgAcceptButton;
    private static BufferedImage imageDlgActiveAcceptButton;
    private static BufferedImage imageDlgCancelButton;
    private static BufferedImage imageDlgActiveCancelButton;
    private static BufferedImage imageDlgPrevButton;
    private static BufferedImage imageDlgActivePrevButton;
    private static BufferedImage imageDlgNextButton;
    private static BufferedImage imageDlgActiveNextButton;

    private static final SoftValuesLoadingCache<Key, BufferedImage> THEME_BUTTON_IMAGES_CACHE;
    private static final SoftValuesLoadingCache<Key, Image> PHASE_THEME_BUTTON_IMAGES_CACHE;
    static {
        THEME_BUTTON_IMAGES_CACHE = ImageCaches.register(SoftValuesLoadingCache.from(ImageManagerImpl::createThemeButtonImage));
        PHASE_THEME_BUTTON_IMAGES_CACHE = ImageCaches.register(SoftValuesLoadingCache.from(ImageManagerImpl::createPhaseThemeButtonImage));
    }

    private static final class Key {

        final String resourceName;

        public Key(String resourceName) {
            this.resourceName = resourceName;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 53 * hash + Objects.hashCode(this.resourceName);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final ImageManagerImpl.Key other = (ImageManagerImpl.Key) obj;
            if (!this.resourceName.equals(other.resourceName)) {
                return false;
            }
            return true;
        }
    }

    private static BufferedImage createThemeButtonImage(ImageManagerImpl.Key key) {
        return getBufferedImageFromResource(PreferencesDialog.getCurrentTheme().getButtonPath(key.resourceName));
    }

    private static Image createPhaseThemeButtonImage(ImageManagerImpl.Key key) {
        return getImageFromResource(
                PreferencesDialog.getCurrentTheme().getPhasePath("phase_" + key.resourceName.toLowerCase(Locale.ENGLISH) + ".png"),
                new Rectangle(36, 36)
        );
    }

    ImageManagerImpl() {
    }

    public BufferedImage getThemeButton(String resourceName) {
        return THEME_BUTTON_IMAGES_CACHE.getOrThrow(new ImageManagerImpl.Key(resourceName));
    }

    @Override
    public Image getPhaseImage(String phaseName) {
        return PHASE_THEME_BUTTON_IMAGES_CACHE.getOrThrow(new ImageManagerImpl.Key(phaseName));
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
    public BufferedImage getTokenIconImage() {
        if (imageTokenIcon == null) {
            Image image = getImageFromResourceTransparent("/card/token.png", Color.WHITE, new Rectangle(20, 20));
            imageTokenIcon = BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB);
        }
        return imageTokenIcon;
    }

    @Override
    public Image getLookedAtImage() {
        if (lookedAtIcon == null) {
            Image image = getImageFromResourceTransparent("/game/looked_at.png", Color.WHITE, new Rectangle(20, 20));
            lookedAtIcon = BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB);
        }
        return lookedAtIcon;
    }

    @Override
    public Image getRevealedImage() {
        if (revealedIcon == null) {
            Image image = getImageFromResourceTransparent("/game/revealed.png", Color.WHITE, new Rectangle(20, 20));
            revealedIcon = BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB);
        }
        return revealedIcon;
    }

    @Override
    public Image getExileImage() {
        if (exileIcon == null) {
            Image image = getImageFromResourceTransparent("/info/exile.png", Color.WHITE, new Rectangle(20, 20));
            exileIcon = BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB);
        }
        return exileIcon;
    }

    @Override
    public BufferedImage getTriggeredAbilityImage() {
        if (triggeredAbilityIcon == null) {
            Image image = getImageFromResourceTransparent("/card/triggered_ability.png", Color.WHITE, new Rectangle(20, 20));
            triggeredAbilityIcon = BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB);
        }
        return triggeredAbilityIcon;
    }

    @Override
    public BufferedImage getActivatedAbilityImage() {
        if (activatedAbilityIcon == null) {
            Image image = getImageFromResourceTransparent("/card/activated_ability.png", Color.WHITE, new Rectangle(20, 20));
            activatedAbilityIcon = BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB);
        }
        return activatedAbilityIcon;
    }

    @Override
    public BufferedImage getCopyInformIconImage() {
        if (imageCopyIcon == null) {
            Image image = getImageFromResourceTransparent("/card/copy.png", Color.WHITE, new Rectangle(20, 20));
            imageCopyIcon = BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB);
        }
        return imageCopyIcon;
    }

    @Override
    public BufferedImage getCounterImageViolet() {
        if (imageCounterViolet == null) {
            Image image = getImageFromResourceTransparent("/card/counter_violet.png", Color.WHITE, new Rectangle(32, 32));
            imageCounterViolet = BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB);
        }
        return imageCounterViolet;
    }

    @Override
    public BufferedImage getCounterImageGreen() {
        if (imageCounterGreen == null) {
            Image image = getImageFromResourceTransparent("/card/counter_green.png", Color.WHITE, new Rectangle(32, 32));
            imageCounterGreen = BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB);
        }
        return imageCounterGreen;
    }

    @Override
    public BufferedImage getCounterImageRed() {
        if (imageCounterRed == null) {
            Image image = getImageFromResourceTransparent("/card/counter_red.png", Color.WHITE, new Rectangle(32, 32));
            imageCounterRed = BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB);
        }
        return imageCounterRed;
    }

    @Override
    public BufferedImage getCounterImageGrey() {
        if (imageCounterGrey == null) {
            Image image = getImageFromResourceTransparent("/card/counter_grey.png", Color.WHITE, new Rectangle(32, 32));
            imageCounterGrey = BufferedImageBuilder.bufferImage(image, BufferedImage.TYPE_INT_ARGB);
        }
        return imageCounterGrey;
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

    @Override
    public Image getConcedeButtonImage() {
        return getThemeButton("concede.png");
    }

    @Override
    public Image getSwitchHandsButtonImage() {
        return getThemeButton("switch_hands.png");
    }

    @Override
    public Image getStopWatchButtonImage() {
        return getThemeButton("stop_watching.png");
    }

    @Override
    public Image getCancelSkipButtonImage() {
        return getThemeButton("cancel_skip.png");
    }

    @Override
    public Image getSkipNextTurnButtonImage() {
        return getThemeButton("skip_turn.png");
    }

    @Override
    public Image getSkipEndTurnButtonImage() {
        return getThemeButton("skip_to_end.png");
    }

    @Override
    public Image getSkipMainButtonImage() {
        return getThemeButton("skip_to_main.png");
    }

    @Override
    public Image getSkipStackButtonImage() {
        return getThemeButton("skip_stack.png");
    }

    @Override
    public Image getSkipEndStepBeforeYourTurnButtonImage() {
        return getThemeButton("skip_to_previous_end.png");
    }

    @Override
    public Image getSkipYourNextTurnButtonImage() {
        return getThemeButton("skip_all.png");
    }

    @Override
    public Image getToggleRecordMacroButtonImage() {
        return getThemeButton("toggle_macro.png");
    }

    @Override
    public BufferedImage getCardIcon(String resourceName, int size, CardIconColor cardIconColor) {
        // icon must be same, but color can be changed by themes
        InputStream data = ImageManager.class.getResourceAsStream(PreferencesDialog.getCurrentTheme().getCardIconsResourcePath(resourceName));
        try {
            // no need to resize svg (lib already do it on load)
            return SvgUtils.loadSVG(data, "card icon = " + resourceName + "; " + cardIconColor.toString(),
                    PreferencesDialog.getCurrentTheme().getCardIconsCssFile(cardIconColor),
                    PreferencesDialog.getCurrentTheme().getCardIconsCssSettings(cardIconColor),
                    size, size, false);
        } catch (Exception e) {
            logger.error("Can't load card icon: " + resourceName + " , reason: " + e.getMessage(), e);
            return null;
        }
    }

    protected static Image getImageFromResourceTransparent(String path, Color mask, Rectangle rec) {
        BufferedImage image;
        Image imageCardTransparent;
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

    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}
