package org.mage.plugins.card.images;

import java.util.Objects;

/**
 *
 * @author North
 */
public class CardDownloadData {

    private String name;
    private String downloadName;
    private String set;
    private String tokenSetCode;
    private String tokenDescriptor;
    private String collectorId;
    private Integer type;
    private boolean token;
    private boolean twoFacedCard;
    private boolean secondSide;
    private boolean flipCard;
    private boolean flippedSide;
    private boolean splitCard;
    private boolean usesVariousArt;
    private boolean isType2;

    public CardDownloadData(String name, String set, String collectorId, boolean usesVariousArt, Integer type, String tokenSetCode, String tokenDescriptor) {
        this(name, set, collectorId, usesVariousArt, type, tokenSetCode, tokenDescriptor, false);
    }

    public CardDownloadData(String name, String set, String collectorId, boolean usesVariousArt, Integer type, String tokenSetCode, String tokenDescriptor, boolean token) {
        this(name, set, collectorId, usesVariousArt, type, tokenSetCode, tokenDescriptor, token, false, false);
    }

    public CardDownloadData(String name, String set, String collectorId, boolean usesVariousArt, Integer type, String tokenSetCode, String tokenDescriptor, boolean token, boolean twoFacedCard, boolean secondSide) {
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
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CardDownloadData other = (CardDownloadData) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.set == null) ? (other.set != null) : !this.set.equals(other.set)) {
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
        if (this.isType2 != other.isType2) {
            return false;
        }
        return true;
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

    public String getTokenSetCode() {
        return tokenSetCode;
    }

    public void setTokenSetCode(String tokenSetCode) {
        this.tokenSetCode = tokenSetCode;
    }

    public String getTokenDescriptor() {
        return tokenDescriptor;
    }

    public void setTokenDescriptor(String tokenDescriptor) {
        this.tokenDescriptor = tokenDescriptor;
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
