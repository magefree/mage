package north.gatherercrawler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author North
 */
public class Card implements Comparable<Card> {

    private Integer multiverseId;
    private String name;
    private List<String> manaCost;
    private Integer convertedManaCost;
    private String types;
    private List<String> cardText;
    private List<String> flavorText;
    private String powerToughness;
    private String expansion;
    private String rarity;
    private String cardNumber;
    private String artist;
    private Card otherSide;

    public Card(Integer multiverseId) {
        this.multiverseId = multiverseId;
    }

    public Card(String card) {
        String[] split = card.split("\\|", 13);
        if (split[0].length() > 0) {
            multiverseId = Integer.parseInt(split[0]);
        }
        if (split[1].length() > 0) {
            name = split[1];
        }
        manaCost = new ArrayList<String>();
        manaCost.addAll(Arrays.asList(split[2].split("\\$")));
        if (split[3].length() > 0) {
            convertedManaCost = Integer.parseInt(split[3]);
        }
        if (split[4].length() > 0) {
            types = split[4];
        }
        cardText = new ArrayList<String>();
        cardText.addAll(Arrays.asList(split[5].split("\\$")));
        flavorText = new ArrayList<String>();
        flavorText.addAll(Arrays.asList(split[6].split("\\$")));
        if (split[7].length() > 0) {
            powerToughness = split[7];
        }
        if (split[8].length() > 0) {
            expansion = split[8];
        }
        if (split[9].length() > 0) {
            rarity = split[9];
        }
        if (split[10].length() > 0) {
            cardNumber = split[10];
        }
        if (split[11].length() > 0) {
            artist = split[11];
        }
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public List<String> getCardText() {
        return cardText;
    }

    public void setCardText(List<String> cardText) {
        this.cardText = cardText;
    }

    public Integer getConvertedManaCost() {
        return convertedManaCost;
    }

    public void setConvertedManaCost(Integer convertedManaCost) {
        this.convertedManaCost = convertedManaCost;
    }

    public String getExpansion() {
        return expansion;
    }

    public void setExpansion(String expansion) {
        this.expansion = expansion;
    }

    public List<String> getFlavorText() {
        return flavorText;
    }

    public void setFlavorText(List<String> flavorText) {
        this.flavorText = flavorText;
    }

    public List<String> getManaCost() {
        return manaCost;
    }

    public void setManaCost(List<String> manaCost) {
        this.manaCost = manaCost;
    }

    public Integer getMultiverseId() {
        return multiverseId;
    }

    public void setMultiverseId(Integer multiverseId) {
        this.multiverseId = multiverseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPowerToughness() {
        return powerToughness;
    }

    public void setPowerToughness(String powerToughness) {
        this.powerToughness = powerToughness;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public Card getOtherSide() {
        return otherSide;
    }

    public void setOtherSide(Card otherSide) {
        this.otherSide = otherSide;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(multiverseId).append("|");
        sb.append(name).append("|");
        for (int i = 0; i < manaCost.size(); i++) {
            sb.append(manaCost.get(i));
            if (i < manaCost.size() - 1) {
                sb.append("$");
            }
        }
        sb.append("|");
        sb.append(convertedManaCost != null ? convertedManaCost : "").append("|");
        sb.append(types).append("|");
        for (int i = 0; i < cardText.size(); i++) {
            sb.append(cardText.get(i));
            if (i < cardText.size() - 1) {
                sb.append("$");
            }
        }
        sb.append("|");
        for (int i = 0; i < flavorText.size(); i++) {
            sb.append(flavorText.get(i));
            if (i < flavorText.size() - 1) {
                sb.append("$");
            }
        }
        sb.append("|");
        sb.append(powerToughness != null ? powerToughness : "").append("|");
        sb.append(expansion).append("|");
        sb.append(rarity != null ? rarity : "").append("|");
        sb.append(cardNumber != null ? cardNumber : "").append("|");
        sb.append(artist != null ? artist : "");

        if (otherSide != null) {
            sb.append("\n").append(otherSide.toString());
        }
        return sb.toString();
    }

    public int compareTo(Card o) {
        int idCompareResult = this.multiverseId.compareTo(o.getMultiverseId());
        return idCompareResult == 0 ? this.cardNumber.compareTo(o.getCardNumber()) : idCompareResult;
    }
}
