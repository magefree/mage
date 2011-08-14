package org.mage.plugins.card.images;

/**
 *
 * @author North
 */
public class CardInfo {

    private String name;
    private String set;
    private Integer collectorId;
    private boolean token;

    public CardInfo(String name, String set, Integer collectorId) {
        this.name = name;
        this.set = set;
        this.collectorId = collectorId;
        token = false;
    }

    public CardInfo(String name, String set, Integer collectorId, boolean token) {
        this.name = name;
        this.set = set;
        this.collectorId = collectorId;
        this.token = token;
    }

    public CardInfo(final CardInfo card) {
        this.name = card.name;
        this.set = card.set;
        this.collectorId = card.collectorId;
        this.token = card.token;
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
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 47 * hash + (this.set != null ? this.set.hashCode() : 0);
        hash = 47 * hash + (this.collectorId != null ? this.collectorId.hashCode() : 0);
        hash = 47 * hash + (this.token ? 1 : 0);
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
}
