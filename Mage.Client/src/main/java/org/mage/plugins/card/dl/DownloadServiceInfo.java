package org.mage.plugins.card.dl;

import java.net.Proxy;

/**
 * @author JayDi85
 */
public interface DownloadServiceInfo {

    boolean isNeedCancel();

    void incErrorCount();

    /**
     * Stop the whole download at once on rate limit error from a server (HTTP 429 Too Many Requests),
     * any new requests will be blocked by the server anyway and can lead to a ban
     *
     * @param url        request url with the error
     * @param retryAfter server's Retry-After header (can be null)
     */
    void stopOnRateLimit(String url, String retryAfter);

    void updateGlobalMessage(String text);

    void updateProgressMessage(String text);
    
    void updateProgressMessage(String text, int progressCurrent, int progressNeed);

    Object getSync();
}
