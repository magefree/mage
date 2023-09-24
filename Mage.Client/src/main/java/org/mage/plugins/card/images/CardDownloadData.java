package org.mage.plugins.card.images;

import mage.util.CardUtil;

import java.util.Objects;

/**
 * @author North
 */
public class CardDownloadData {

    private String name;
    private String downloadName;
    private String fileName = "";
    private String set;
    private final String collectorId;
    private final Integer imageNumber;
    private boolean token;
    private final boolean twoFacedCard;
    private final boolean secondSide;
    private boolean flipCard;
    private boolean flippedSide;
    private boolean splitCard;
    private final boolean usesVariousArt;
    private String tokenClassName;

    public CardDownloadData(String name, String setCode, String collectorId, boolean usesVariousArt, Integer imageNumber) {
        this(name, setCode, collectorId, usesVariousArt, imageNumber, false, "");
    }

    public CardDownloadData(String name, String setCode, String collectorId, boolean usesVariousArt, Integer imageNumber, boolean token) {
        this(name, setCode, collectorId, usesVariousArt, imageNumber, token, false, false, "");
    }

    public CardDownloadData(String name, String setCode, String collectorId, boolean usesVariousArt, Integer imageNumber, boolean token, String fileName) {
        this(name, setCode, collectorId, usesVariousArt, imageNumber, token, false, false, "");
        this.fileName = fileName;
    }

    public CardDownloadData(String name, String setCode, String collectorId, boolean usesVariousArt, Integer imageNumber, boolean token, boolean twoFacedCard, boolean secondSide) {
        this(name, setCode, collectorId, usesVariousArt, imageNumber, token, twoFacedCard, secondSide, "");
    }

    public CardDownloadData(String name, String setCode, String collectorId, boolean usesVariousArt, Integer imageNumber, boolean token, boolean twoFacedCard, boolean secondSide, String tokenClassName) {
        this.name = name;
        this.set = setCode;
        this.collectorId = collectorId;
        this.usesVariousArt = usesVariousArt;
        this.imageNumber = imageNumber;
        this.token = token;
        this.twoFacedCard = twoFacedCard;
        this.secondSide = secondSide;
        this.tokenClassName = tokenClassName;
    }

    public CardDownloadData(final CardDownloadData card) {
        this.name = card.name;
        this.downloadName = card.downloadName;
        this.fileName = card.fileName;
        this.set = card.set;
        this.collectorId = card.collectorId;
        this.imageNumber = card.imageNumber;
        this.token = card.token;
        this.twoFacedCard = card.twoFacedCard;
        this.secondSide = card.secondSide;
        this.flipCard = card.flipCard;
        this.flippedSide = card.flippedSide;
        this.splitCard = card.splitCard;
        this.usesVariousArt = card.usesVariousArt;
        this.tokenClassName = card.tokenClassName;
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
        if (!Objects.equals(this.collectorId, other.collectorId)) {
            return false;
        }
        if (this.token != other.token) {
            return false;
        }
        if (this.twoFacedCard != other.twoFacedCard) {
            return false;
        }

        return this.secondSide == other.secondSide;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 47 * hash + (this.set != null ? this.set.hashCode() : 0);
        hash = 47 * hash + (this.collectorId != null ? this.collectorId.hashCode() : 0);
        hash = 47 * hash + (this.imageNumber != null ? this.imageNumber.hashCode() : 0);
        hash = 47 * hash + (this.token ? 1 : 0);
        hash = 47 * hash + (this.twoFacedCard ? 1 : 0);
        hash = 47 * hash + (this.secondSide ? 1 : 0);
        return hash;
    }

    @Override
    public String toString() {
        return String.format("%s - %s", this.getSet(), this.getName());
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

    public void setTokenClassName(String tokenClassName) {
        this.tokenClassName = tokenClassName;
    }

    public String getAffectedClassName() {
        return tokenClassName.isEmpty() ? name.replaceAll("[^a-zA-Z0-9]", "") : tokenClassName;
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

    public Integer getImageNumber() {
        return imageNumber;
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
}
