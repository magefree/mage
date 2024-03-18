package org.mage.plugins.card.images;

import mage.util.CardUtil;

import java.util.Objects;

/**
 * @author North
 */
public class CardDownloadData {

    private String name;
    private String downloadName;
    private String set;
    private final String collectorId;
    private final Integer imageNumber;
    private boolean isToken;
    private boolean isSecondSide;
    private boolean isFlippedSide;
    private boolean isSplitCard;
    private final boolean isUsesVariousArt;

    public CardDownloadData(String name, String setCode, String collectorId, boolean isUsesVariousArt, Integer imageNumber) {
        this.name = name;
        this.set = setCode;
        this.collectorId = collectorId;
        this.isUsesVariousArt = isUsesVariousArt;
        this.imageNumber = imageNumber;
    }

    public CardDownloadData(final CardDownloadData card) {
        this.name = card.name;
        this.downloadName = card.downloadName;
        this.set = card.set;
        this.collectorId = card.collectorId;
        this.imageNumber = card.imageNumber;
        this.isToken = card.isToken;
        this.isSecondSide = card.isSecondSide;
        this.isFlippedSide = card.isFlippedSide;
        this.isSplitCard = card.isSplitCard;
        this.isUsesVariousArt = card.isUsesVariousArt;
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
        if (this.isToken != other.isToken) {
            return false;
        }

        return this.isSecondSide == other.isSecondSide;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 47 * hash + (this.set != null ? this.set.hashCode() : 0);
        hash = 47 * hash + (this.collectorId != null ? this.collectorId.hashCode() : 0);
        hash = 47 * hash + (this.imageNumber != null ? this.imageNumber.hashCode() : 0);
        hash = 47 * hash + (this.isToken ? 1 : 0);
        hash = 47 * hash + (this.isSecondSide ? 1 : 0);
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

    public void setName(String name) {
        this.name = name;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public boolean isToken() {
        return isToken;
    }

    public void setToken(boolean token) {
        this.isToken = token;
    }

    public boolean isSecondSide() {
        return isSecondSide;
    }

    public void setSecondSide(boolean isSecondSide) {
        this.isSecondSide = isSecondSide;
    }

    public String getDownloadName() {
        return downloadName == null ? name : downloadName;
    }

    public void setDownloadName(String downloadName) {
        this.downloadName = downloadName;
    }

    public boolean isSplitCard() {
        return isSplitCard;
    }

    public void setSplitCard(boolean splitCard) {
        this.isSplitCard = splitCard;
    }

    public Integer getImageNumber() {
        return imageNumber;
    }

    public boolean getUsesVariousArt() {
        return isUsesVariousArt;
    }

    public boolean isFlippedSide() {
        return isFlippedSide;
    }

    public void setFlippedSide(boolean flippedSide) {
        this.isFlippedSide = flippedSide;
    }
}
