package org.mage.plugins.card.dl.sources;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import mage.MageException;
import mage.client.util.CardLanguage;
import mage.util.JsonUtil;
import org.apache.log4j.Logger;
import org.mage.plugins.card.dl.DownloadServiceInfo;
import org.mage.plugins.card.images.CardDownloadData;
import org.mage.plugins.card.dl.sources.ScryfallImageSource;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * @author tiera3
 */
public final ScryfallImageSourceSmall extends ScryfallImageSource {

    private CardImageUrls innerModifyUrl(CardImageUrls cardUrls) {
        String baseUrl = null;
        String alternativeUrl = null;
		
		if (cardUrls.baseUrl != null && !cardUrls.baseUrl.isEmpty()) {
            baseUrl = cardUrls.baseUrl.replaceFirst("/large/","/small/").replaceFirst("format=image","format=image&version=small");
        }
        if (cardUrls.alternativeUrl != null
                && !cardUrls.alternativeUrl.isEmpty()) {
            alternativeUrl = cardUrls.alternativeUrl.replaceFirst("/large/","/small/").replaceFirst("format=image","format=image&version=small");
        }
        return new CardImageUrls(baseUrl, alternativeUrl);
	}

    @Override
    public CardImageUrls generateCardUrl(CardDownloadData card) throws Exception {
        return innerModifyUrl(super.innerGenerateURL(card, false));
    }

    @Override
    public CardImageUrls generateTokenUrl(CardDownloadData card) throws Exception {
        return innerModifyUrl(super.innerGenerateURL(card, true));
    }

    @Override
    public float getAverageSize() {
        return 13;
    }


}
