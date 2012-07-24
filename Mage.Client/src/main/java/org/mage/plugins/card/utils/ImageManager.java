package org.mage.plugins.card.utils;

import java.awt.*;

public interface ImageManager {
    public Image getAppImage();
    public Image getAppSmallImage();
    public Image getAppFlashedImage();

    public Image getSicknessImage();
    public Image getDayImage();
    public Image getNightImage();

    public Image getDlgAcceptButtonImage();
    public Image getDlgActiveAcceptButtonImage();
    public Image getDlgCancelButtonImage();
    public Image getDlgActiveCancelButtonImage();
    public Image getDlgPrevButtonImage();
    public Image getDlgActivePrevButtonImage();
    public Image getDlgNextButtonImage();
    public Image getDlgActiveNextButtonImage();

    public Image getPhaseImage(String phase);
}
