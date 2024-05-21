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
public enum ScryfallImageSourceSmall implements CardImageSource {
    instance;
	
    private CardImageSource baseSource = ScryfallImageSource.instance;
    private static final Logger logger = Logger.getLogger(ScryfallImageSourceSmall.class);

    ScryfallImageSourceSmall() {
    }

    private CardImageUrls innerModifyUrl(CardImageUrls cardUrls) {
        String baseUrl = null;
        String alternativeUrl = null;
		
		if (cardUrls.baseUrl != null && !cardUrls.baseUrl.isEmpty()) {
            baseUrl = cardUrls.baseUrl.replaceFirst("/large/","/small/").replaceFirst("format=image","format=image&version=small");
        }
        if (cardUrls.alternativeUrls != null
                && !cardUrls.alternativeUrls.isEmpty()) {
            alternativeUrl = cardUrls.alternativeUrls.get(0).replaceFirst("/large/","/small/").replaceFirst("format=image","format=image&version=small");
        }
        return new CardImageUrls(baseUrl, alternativeUrl);
	}

    @Override
    public boolean prepareDownloadList(DownloadServiceInfo downloadServiceInfo, List<CardDownloadData> downloadList) {
		return baseSource.prepareDownloadList(downloadServiceInfo, downloadList);
	}	

    @Override
    public CardImageUrls generateCardUrl(CardDownloadData card) throws Exception {
        return innerModifyUrl(baseSource.generateCardUrl(card));
    }

    @Override
    public CardImageUrls generateTokenUrl(CardDownloadData card) throws Exception {
        return innerModifyUrl(baseSource.generateTokenUrl(card));
    }

    @Override
    public String getNextHttpImageUrl() {
        return baseSource.getNextHttpImageUrl();
    }

    @Override
    public String getFileForHttpImage(String httpImageUrl) {
        return baseSource.getFileForHttpImage(httpImageUrl);
    }

    @Override
    public String getSourceName() {
        return baseSource.getSourceName();
    }

    @Override
    public float getAverageSize() {
        return 13; // initial estimate - TODO calculate a more accurate number
    }

    @Override
    public int getTotalImages() {
        return baseSource.getTotalImages();
    }

    @Override
    public boolean isTokenSource() {
        return baseSource.isTokenSource();
    }

    @Override
    public boolean isCardSource() {
        return baseSource.isCardSource();
    }

    @Override
    public boolean isLanguagesSupport() {
        return baseSource.isLanguagesSupport();
    }

    @Override
    public void setCurrentLanguage(CardLanguage cardLanguage) {
        baseSource.currentLanguage = cardLanguage;
    }

    @Override
    public CardLanguage getCurrentLanguage() {
        return baseSource.getCurrentLanguage();
    }

    @Override
    public void doPause(String httpImageUrl) {
        baseSource.doPause(httpImageUrl);
    }

    @Override
    public List<String> getSupportedSets() {
        return baseSource.getSupportedSets();
    }

    @Override
    public boolean isCardImageProvided(String setCode, String cardName) {
        return baseSource.isCardImageProvided(setCode, cardName);
    }

    @Override
    public boolean isTokenImageProvided(String setCode, String cardName, Integer tokenNumber) {
        return baseSource.isTokenImageProvided(setCode, cardName, tokenNumber);
    }

}
