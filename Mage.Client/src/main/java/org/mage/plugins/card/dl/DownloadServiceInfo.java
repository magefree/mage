package org.mage.plugins.card.dl;

import java.net.Proxy;

/**
 * @author JayDi85
 */
public interface DownloadServiceInfo {

    Proxy getProxy();

    boolean isNeedCancel();

    void incErrorCount();

    void updateGlobalMessage(String text);

    void updateProgressMessage(String text);
    
    void updateProgressMessage(String text, int progressCurrent, int progressNeed);

    void showDownloadControls(boolean needToShow);

    Object getSync();
}
