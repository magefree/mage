package org.mage.plugins.card.utils;

import mage.abilities.icon.CardIconColor;

import java.awt.*;
import java.awt.image.BufferedImage;

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

    Image getSwitchHandsButtonImage();

    Image getStopWatchButtonImage();

    Image getConcedeButtonImage();

    Image getCancelSkipButtonImage();

    Image getSkipNextTurnButtonImage();

    Image getSkipEndTurnButtonImage();

    Image getSkipMainButtonImage();

    Image getSkipStackButtonImage();

    Image getSkipEndStepBeforeYourTurnButtonImage();

    Image getSkipYourNextTurnButtonImage();

    Image getToggleRecordMacroButtonImage();

    BufferedImage getCardIcon(String resourceName, int size, CardIconColor cardIconColor);

    Image getPhaseImage(String phase);
}
