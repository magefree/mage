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
import java.io.ObjectStreamException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * @author tiera3
 */
public class ScryfallImageSourceSmall extends ScryfallImageSource {

    private static final ScryfallImageSourceSmall instanceSmall = new ScryfallImageSourceSmall();

    private Object readResolve() throws ObjectStreamException {
        return instanceSmall;
    }
    
    public static ScryfallImageSource getInstance() {
        return instanceSmall;
    }

    private String innerModifyUrlString(String oneUrl) {
        return oneUrl.replaceFirst("/large/","/small/").replaceFirst("format=image","format=image&version=small");
    }

    private CardImageUrls innerModifyUrl(CardImageUrls cardUrls) {
        List<String> downloadUrls = cardUrls.getDownloadList();

        if (downloadUrls.size() > 2) {   
            return new CardImageUrls(innerModifyUrlString(downloadUrls.get(0)), innerModifyUrlString(downloadUrls.get(1)), innerModifyUrlString(downloadUrls.get(2)));
        } 
        if (downloadUrls.size() > 1) {
            return new CardImageUrls(innerModifyUrlString(downloadUrls.get(0)), innerModifyUrlString(downloadUrls.get(1)));
        } 
        if (downloadUrls.size() == 1) {
            return new CardImageUrls(innerModifyUrlString(downloadUrls.get(0)));
        } 
        return cardUrls;
    }

    @Override
    public CardImageUrls generateCardUrl(CardDownloadData card) throws Exception {
        return innerModifyUrl(super.generateCardUrl(card));
    }

    @Override
    public CardImageUrls generateTokenUrl(CardDownloadData card) throws Exception {
        return innerModifyUrl(super.generateTokenUrl(card));
    }

    @Override
    public float getAverageSize() {
        return 13; // initial estimate - TODO calculate a more accurate number
    }

}
