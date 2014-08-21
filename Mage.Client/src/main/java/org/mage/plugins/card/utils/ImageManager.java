package org.mage.plugins.card.utils;

import java.awt.*;

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
    Image getCopyInformIconImage();
    Image getCounterImage();

    Image getDlgAcceptButtonImage();
    Image getDlgActiveAcceptButtonImage();
    Image getDlgCancelButtonImage();
    Image getDlgActiveCancelButtonImage();
    Image getDlgPrevButtonImage();
    Image getDlgActivePrevButtonImage();
    Image getDlgNextButtonImage();
    Image getDlgActiveNextButtonImage();

    Image getPhaseImage(String phase);
}
