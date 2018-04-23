package org.mage.plugins.card.utils.impl;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.WritableRaster;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.imageio.ImageIO;
import mage.client.util.gui.BufferedImageBuilder;
import org.mage.plugins.card.utils.ImageManager;
import org.mage.plugins.card.utils.Transparency;

public enum ImageManagerImpl implements ImageManager {
    instance;

    ImageManagerImpl() {
        init();
    }

    public void init() {
        String[] phases = {"Untap", "Upkeep", "Draw", "Main1",
            "Combat_Start", "Combat_Attack", "Combat_Block", "Combat_Damage", "Combat_End",
            "Main2", "Cleanup", "Next_Turn"};
        phasesImages = new HashMap<>();
        for (String name : phases) {
            Image image = getImageFromResource("/phases/phase_" + name.toLowerCase(Locale.ENGLISH) + ".png", new Rectangle(36, 36));
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
        if (imageConcedeButton == null) {
            imageConcedeButton = getBufferedImageFromResource("/buttons/concede.png");
        }
        return imageConcedeButton;
    }

    @Override
    public Image getSwitchHandsButtonImage() {
        if (imageSwitchHandsButton == null) {
            imageSwitchHandsButton = getBufferedImageFromResource("/buttons/switch_hands.png");
        }
        return imageSwitchHandsButton;
    }

    @Override
    public Image getStopWatchButtonImage() {
        if (imageStopWatchingButton == null) {
            imageStopWatchingButton = getBufferedImageFromResource("/buttons/stop_watching.png");
        }
        return imageStopWatchingButton;
    }

    @Override
    public Image getCancelSkipButtonImage() {
        if (imageCancelSkipButton == null) {
            imageCancelSkipButton = getBufferedImageFromResource("/buttons/cancel_skip.png");
        }
        return imageCancelSkipButton;
    }

    @Override
    public Image getSkipNextTurnButtonImage() {
        if (imageSkipNextTurnButton == null) {
            imageSkipNextTurnButton = getBufferedImageFromResource("/buttons/skip_turn.png");
        }
        return imageSkipNextTurnButton;
    }

    @Override
    public Image getSkipEndTurnButtonImage() {
        if (imageSkipToEndTurnButton == null) {
            imageSkipToEndTurnButton = getBufferedImageFromResource("/buttons/skip_to_end.png");
        }
        return imageSkipToEndTurnButton;
    }

    @Override
    public Image getSkipMainButtonImage() {
        if (imageSkipToMainButton == null) {
            imageSkipToMainButton = getBufferedImageFromResource("/buttons/skip_to_main.png");
        }
        return imageSkipToMainButton;
    }

    @Override
    public Image getSkipStackButtonImage() {
        if (imageSkipStackButton == null) {
            imageSkipStackButton = getBufferedImageFromResource("/buttons/skip_stack.png");
        }
        return imageSkipStackButton;
    }

    @Override
    public Image getSkipEndStepBeforeYourTurnButtonImage() {
        if (imageSkipUntilEndStepBeforeYourTurnButton == null) {
            imageSkipUntilEndStepBeforeYourTurnButton = getBufferedImageFromResource("/buttons/skip_to_previous_end.png");
        }
        return imageSkipUntilEndStepBeforeYourTurnButton;
    }

    @Override
    public Image getSkipYourNextTurnButtonImage() {
        if (imageSkipYourNextTurnButton == null) {
            imageSkipYourNextTurnButton = getBufferedImageFromResource("/buttons/skip_all.png");
        }
        return imageSkipYourNextTurnButton;
    }

    @Override
    public Image getToggleRecordMacroButtonImage() {
        if (imageToggleRecordMacroButton == null) {
            imageToggleRecordMacroButton = getBufferedImageFromResource("/buttons/toggle_macro.png");
        }
        return imageToggleRecordMacroButton;
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

    private static BufferedImage imageCancelSkipButton;
    private static BufferedImage imageSwitchHandsButton;
    private static BufferedImage imageStopWatchingButton;
    private static BufferedImage imageConcedeButton;
    private static BufferedImage imageSkipNextTurnButton;
    private static BufferedImage imageSkipToEndTurnButton;
    private static BufferedImage imageSkipToMainButton;
    private static BufferedImage imageSkipStackButton;
    private static BufferedImage imageSkipUntilEndStepBeforeYourTurnButton;
    private static BufferedImage imageSkipYourNextTurnButton;
    private static BufferedImage imageToggleRecordMacroButton;

    private static Map<String, Image> phasesImages;
}
