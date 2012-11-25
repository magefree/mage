package org.mage.plugins.card.images;

/**
 *
 * @author North
 */
public class CardInfo {

    private String name;
    private String downloadName;
    private String set;
    private Integer collectorId;
    private Integer type;
    private boolean token;
    private boolean twoFacedCard;
    private boolean secondSide;
    private boolean flipCard;
    private boolean useCollectorId; // for building the image name (different images for the same card)

    public CardInfo(String name, String set, Integer collectorId, boolean useCollectorId, Integer type) {
        this(name, set, collectorId, useCollectorId, type, false);
    }

    public CardInfo(String name, String set, Integer collectorId, boolean useCollectorId, Integer type, boolean token) {
        this(name, set, collectorId, useCollectorId, type, token, false, false);
    }

    public CardInfo(String name, String set, Integer collectorId, boolean useCollectorId, Integer type, boolean token, boolean twoFacedCard, boolean secondSide) {
        this.name = name;
        this.set = set;
        this.collectorId = collectorId;
        this.useCollectorId = useCollectorId;
        this.type = type;
        this.token = token;
        this.twoFacedCard = twoFacedCard;
        this.secondSide = secondSide;
    }

    public CardInfo(final CardInfo card) {
        this.name = card.name;
        this.set = card.set;
        this.collectorId = card.collectorId;
        this.token = card.token;
        this.twoFacedCard = card.twoFacedCard;
        this.secondSide = card.secondSide;
        this.type = card.type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CardInfo other = (CardInfo) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.set == null) ? (other.set != null) : !this.set.equals(other.set)) {
            return false;
        }
        if (this.collectorId != other.collectorId && (this.collectorId == null || !this.collectorId.equals(other.collectorId))) {
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
        return hash;
    }

    public Integer getCollectorId() {
        return collectorId;
    }

    public void setCollectorId(Integer collectorId) {
        this.collectorId = collectorId;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public boolean useCollectorId() {
        return useCollectorId;
    }

}
