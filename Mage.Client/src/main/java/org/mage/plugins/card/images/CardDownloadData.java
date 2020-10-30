package org.mage.plugins.card.images;

import mage.util.CardUtil;

import java.util.Locale;
import java.util.Objects;

/**
 * @author North
 */
public class CardDownloadData {

    private String name;
    private String downloadName;
    private String fileName = "";
    private String set;
    private String tokenSetCode;
    private String tokenDescriptor;
    private final String collectorId;
    private final Integer type;
    private boolean token;
    private final boolean twoFacedCard;
    private final boolean secondSide;
    private boolean flipCard;
    private boolean flippedSide;
    private boolean splitCard;
    private final boolean usesVariousArt;
    private String tokenClassName;
    private boolean isType2;

    public CardDownloadData(String name, String set, String collectorId, boolean usesVariousArt, Integer type, String tokenSetCode, String tokenDescriptor) {
        this(name, set, collectorId, usesVariousArt, type, tokenSetCode, tokenDescriptor, false, "");
    }

    public CardDownloadData(String name, String set, String collectorId, boolean usesVariousArt, Integer type, String tokenSetCode, String tokenDescriptor, boolean token) {
        this(name, set, collectorId, usesVariousArt, type, tokenSetCode, tokenDescriptor, token, false, false, "");
    }

    public CardDownloadData(String name, String set, String collectorId, boolean usesVariousArt, Integer type, String tokenSetCode, String tokenDescriptor, boolean token, String fileName) {
        this(name, set, collectorId, usesVariousArt, type, tokenSetCode, tokenDescriptor, token, false, false, "");
        this.fileName = fileName;
    }

    public CardDownloadData(String name, String set, String collectorId, boolean usesVariousArt, Integer type, String tokenSetCode, String tokenDescriptor, boolean token, boolean twoFacedCard, boolean secondSide) {
        this(name, set, collectorId, usesVariousArt, type, tokenSetCode, tokenDescriptor, token, twoFacedCard, secondSide, "");
    }

    public CardDownloadData(String name, String set, String collectorId, boolean usesVariousArt, Integer type, String tokenSetCode, String tokenDescriptor, boolean token, boolean twoFacedCard, boolean secondSide, String tokenClassName) {
        this.name = name;
        this.set = set;
        this.collectorId = collectorId;
        this.usesVariousArt = usesVariousArt;
        this.type = type;
        this.token = token;
        this.twoFacedCard = twoFacedCard;
        this.secondSide = secondSide;
        this.tokenSetCode = tokenSetCode;
        this.tokenDescriptor = tokenDescriptor;
        this.tokenClassName = tokenClassName;

        if (this.tokenDescriptor == null || this.tokenDescriptor.equalsIgnoreCase("")) {
            this.tokenDescriptor = lastDitchTokenDescriptor();
        }
    }

    public CardDownloadData(final CardDownloadData card) {
        this.name = card.name;
        this.set = card.set;
        this.collectorId = card.collectorId;
        this.token = card.token;
        this.twoFacedCard = card.twoFacedCard;
        this.secondSide = card.secondSide;
        this.type = card.type;
        this.usesVariousArt = card.usesVariousArt;
        this.tokenSetCode = card.tokenSetCode;
        this.tokenDescriptor = card.tokenDescriptor;
        this.tokenClassName = card.tokenClassName;
        this.fileName = card.fileName;

    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!getClass().equals(obj.getClass())) {
            return false;
        }
        final CardDownloadData other = (CardDownloadData) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.set, other.set)) {
            return false;
        }
        if (!Objects.equals(this.collectorId, other.collectorId) && (this.collectorId == null || !this.collectorId.equals(other.collectorId))) {
            return false;
        }
        if (this.token != other.token) {
            return false;
        }
        if (this.twoFacedCard != other.twoFacedCard) {
            return false;
        }
        if (this.secondSide != other.secondSide) {
            return false;
        }
        return this.isType2 == other.isType2;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 47 * hash + (this.set != null ? this.set.hashCode() : 0);
        hash = 47 * hash + (this.collectorId != null ? this.collectorId.hashCode() : 0);
        hash = 47 * hash + (this.type != null ? this.type.hashCode() : 0);
        hash = 47 * hash + (this.token ? 1 : 0);
        hash = 47 * hash + (this.twoFacedCard ? 1 : 0);
        hash = 47 * hash + (this.secondSide ? 1 : 0);
        hash = 47 * hash + (this.isType2 ? 1 : 0);
        return hash;
    }

    public String getCollectorId() {
        return collectorId;
    }

    public Integer getCollectorIdAsInt() {
        return CardUtil.parseCardNumberAsInt(collectorId);
    }

    public String getCollectorIdAsFileName() {
        // return file names compatible card number (e.g. replace special symbols)
        return collectorId
                .replace("*", "star")
                .replace("★", "star");
    }

    public String getCollectorIdPostfix() {
        return getCollectorId().replaceAll(getCollectorIdAsInt().toString(), "");
    }

    public boolean isCollectorIdWithStr() {
        // card have special numbers like "103a", "180b" (scryfall style)
        return !getCollectorId().equals(getCollectorIdAsInt().toString());
    }

    public String getName() {
        return name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getTokenSetCode() {
        return tokenSetCode;
    }

    public void setTokenSetCode(String tokenSetCode) {
        this.tokenSetCode = tokenSetCode;
    }

    public String getTokenDescriptor() {
        return tokenDescriptor;
    }

    public void setTokenClassName(String tokenClassName) {
        this.tokenClassName = tokenClassName;
    }

    public String getTokenClassName() {
        return tokenClassName;
    }

    public void setTokenDescriptor(String tokenDescriptor) {
        this.tokenDescriptor = tokenDescriptor;
    }

    private String lastDitchTokenDescriptor() {
        String tmpName = this.name.replaceAll("[^a-zA-Z0-9]", "");
        String descriptor = tmpName + "....";
        descriptor = descriptor.toUpperCase(Locale.ENGLISH);
        return descriptor;
    }

    public boolean isToken() {
        return token;
    }

    public void setToken(boolean token) {
        this.token = token;
    }

    public boolean isTwoFacedCard() {
        return twoFacedCard;
    }

    public boolean isSecondSide() {
        return secondSide;
    }

    public String getDownloadName() {
        return downloadName == null ? name : downloadName;
    }

    public void setDownloadName(String downloadName) {
        this.downloadName = downloadName;
    }

    public boolean isFlipCard() {
        return flipCard;
    }

    public void setFlipCard(boolean flipCard) {
        this.flipCard = flipCard;
    }

    public boolean isSplitCard() {
        return splitCard;
    }

    public void setSplitCard(boolean splitCard) {
        this.splitCard = splitCard;
    }

    public Integer getType() {
        return type;
    }

    public boolean getUsesVariousArt() {
        return usesVariousArt;
    }

    public boolean isFlippedSide() {
        return flippedSide;
    }

    public void setFlippedSide(boolean flippedSide) {
        this.flippedSide = flippedSide;
    }

    public boolean isType2() {
        return isType2;
    }

    public void setType2(boolean type2) {
        isType2 = type2;
    }
}
