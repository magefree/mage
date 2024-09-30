package org.mage.plugins.card.utils;

import mage.abilities.icon.CardIconColor;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * GUI: images source for GUI components like buttons. With theme supports.
 *
 * @author JayDi85
 */
public interface ImageManager {

    Image getAppImage();

    Image getAppSmallImage();

    Image getAppFlashedImage();

    Image getSicknessImage();

    Image getDayImage();

    Image getNightImage();

    Image getTokenIconImage();

    Image getTriggeredAbilityImage();

    Image getActivatedAbilityImage();

    Image getLookedAtImage();

    Image getRevealedImage();

    Image getExileImage();

    Image getCopyInformIconImage();

    Image getCounterImageViolet();

    Image getCounterImageRed();

    Image getCounterImageGreen();

    Image getCounterImageGrey();

    Image getDlgAcceptButtonImage();

    Image getDlgActiveAcceptButtonImage();

    Image getDlgCancelButtonImage();

    Image getDlgActiveCancelButtonImage();

    Image getDlgPrevButtonImage();

    Image getDlgActivePrevButtonImage();

    Image getDlgNextButtonImage();

    Image getDlgActiveNextButtonImage();

    Image getSwitchHandsButtonImage(int height);

    Image getStopWatchButtonImage(int height);

    Image getConcedeButtonImage(int height);

    Image getCancelSkipButtonImage(int height);

    Image getSkipNextTurnButtonImage(int height);

    Image getSkipEndTurnButtonImage(int height);

    Image getSkipMainButtonImage(int height);

    Image getSkipStackButtonImage(int height);

    Image getSkipEndStepBeforeYourTurnButtonImage(int height);

    Image getSkipYourNextTurnButtonImage(int height);

    Image getToggleRecordMacroButtonImage(int height);

    BufferedImage getCardIcon(String resourceName, int size, CardIconColor cardIconColor);

    Image getPhaseImage(String phase, int size);
}
