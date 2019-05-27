package org.mage.plugins.card.dl;

import java.net.Proxy;

/**
 * @author JayDi85
 */
public interface DownloadServiceInfo {

    Proxy getProxy();

    boolean isNeedCancel();

    void incErrorCount();

    void updateMessage(String text);

    void showDownloadControls(boolean needToShow);
}
