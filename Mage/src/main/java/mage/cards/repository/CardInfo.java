package mage.cards.repository;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import mage.ObjectColor;
import mage.abilities.SpellAbility;
import mage.cards.*;
import mage.cards.mock.MockCard;
import mage.cards.mock.MockSplitCard;
import mage.constants.*;
import mage.util.CardUtil;
import mage.util.SubTypes;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Auto-generated table on each release, so no need SQL-updates on structure changes.
 *
 * @author North
 */
@DatabaseTable(tableName = "card")
public class CardInfo {

    private static final int MAX_RULE_LENGTH = 750;

    private static final String SEPARATOR = "@@@";

    public static final String SPLIT_MANA_SEPARATOR_SHORT = "*";
    public static final String SPLIT_MANA_SEPARATOR_FULL = "{" + SPLIT_MANA_SEPARATOR_SHORT + "}";
    public static final String SPLIT_MANA_SEPARATOR_RENDER = " / ";

    @DatabaseField(indexName = "name_index")
    protected String name;
    @DatabaseField(indexName = "setCode_cardNumber_index")
    protected String setCode;
    @DatabaseField(indexName = "setCode_cardNumber_index")
    protected String cardNumber;
    /**
     * Fast access to numerical card number (number without prefix/postfix: 123b -> 123)
     */
    @DatabaseField(indexName = "cardNumberAsInt_index")
    protected int cardNumberAsInt;
    @DatabaseField(indexName = "className_index")
    protected String className;
    @DatabaseField
    protected String power;
    @DatabaseField
    protected String toughness;
    @DatabaseField
    protected String startingLoyalty;
    @DatabaseField
    protected int manaValue;
    @DatabaseField(dataType = DataType.ENUM_STRING)
    protected Rarity rarity;
    @DatabaseField
    protected String types;
    @DatabaseField
    protected String subtypes;
    @DatabaseField
    protected String supertypes;
    @DatabaseField
    protected String manaCosts;
    @DatabaseField(dataType = DataType.STRING, width = MAX_RULE_LENGTH)
    protected String rules;
    @DatabaseField
    protected boolean black;
    @DatabaseField
    protected boolean blue;
    @DatabaseField
    protected boolean green;
    @DatabaseField
    protected boolean red;
    @DatabaseField
    protected boolean white;
    @DatabaseField
    protected String frameColor;
    @DatabaseField
    protected String frameStyle;
    @DatabaseField
    protected boolean variousArt;
    @DatabaseField
    protected boolean splitCard;
    @DatabaseField
    protected boolean splitCardFuse;
    @DatabaseField
    protected boolean splitCardAftermath;
    @DatabaseField
    protected boolean splitCardHalf;
    @DatabaseField
    protected boolean flipCard;
    @DatabaseField
    protected boolean doubleFaced;
    @DatabaseField(indexName = "name_index")
    protected boolean nightCard;
    @DatabaseField
    protected String flipCardName;
    @DatabaseField
    protected String secondSideName;
    @DatabaseField
    protected boolean adventureCard;
    @DatabaseField
    protected String adventureSpellName;
    @DatabaseField
    protected boolean modalDoubleFacesCard;
    @DatabaseField
    protected String modalDoubleFacesSecondSideName;

    // if you add new field with card side name then update CardRepository.addNewNames too

    public enum ManaCostSide {
        LEFT, RIGHT, ALL
    }

    public CardInfo() {
    }

    public CardInfo(Card card) {
        this.name = card.getName();
        this.cardNumber = card.getCardNumber();
        this.cardNumberAsInt = CardUtil.parseCardNumberAsInt(card.getCardNumber());
        this.setCode = card.getExpansionSetCode();
        this.className = card.getClass().getCanonicalName();
        this.power = card.getPower().toString();
        this.toughness = card.getToughness().toString();
        this.manaValue = card.getManaValue();
        this.rarity = card.getRarity();
        this.splitCard = card instanceof SplitCard;
        this.splitCardFuse = card.getSpellAbility() != null && card.getSpellAbility().getSpellAbilityType() == SpellAbilityType.SPLIT_FUSED;
        this.splitCardAftermath = card.getSpellAbility() != null && card.getSpellAbility().getSpellAbilityType() == SpellAbilityType.SPLIT_AFTERMATH;

        this.flipCard = card.isFlipCard();
        this.flipCardName = card.getFlipCardName();

        this.doubleFaced = card.isTransformable() && card.getSecondCardFace() != null;
        this.nightCard = card.isNightCard();
        Card secondSide = card.getSecondCardFace();
        if (secondSide != null) {
            this.secondSideName = secondSide.getName();
        }

        if (card instanceof AdventureCard) {
            this.adventureCard = true;
            this.adventureSpellName = ((AdventureCard) card).getSpellCard().getName();
        }

        if (card instanceof ModalDoubleFacesCard) {
            this.modalDoubleFacesCard = true;
            this.modalDoubleFacesSecondSideName = ((ModalDoubleFacesCard) card).getRightHalfCard().getName();
        }

        this.frameStyle = card.getFrameStyle().toString();
        this.frameColor = card.getFrameColor(null).toString();
        this.variousArt = card.getUsesVariousArt();
        this.blue = card.getColor(null).isBlue();
        this.black = card.getColor(null).isBlack();
        this.green = card.getColor(null).isGreen();
        this.red = card.getColor(null).isRed();
        this.white = card.getColor(null).isWhite();

        this.setTypes(card.getCardType());
        this.setSubtypes(card.getSubtype().stream().map(SubType::toString).collect(Collectors.toList()));
        this.setSuperTypes(card.getSuperType());

        // mana cost can contains multiple cards (split left/right, modal double faces, card/adventure)
        if (card instanceof SplitCard) {
            List<String> manaCostLeft = ((SplitCard) card).getLeftHalfCard().getManaCostSymbols();
            List<String> manaCostRight = ((SplitCard) card).getRightHalfCard().getManaCostSymbols();
            this.setManaCosts(CardUtil.concatManaSymbols(SPLIT_MANA_SEPARATOR_FULL, manaCostLeft, manaCostRight));
        } else if (card instanceof ModalDoubleFacesCard) {
            List<String> manaCostLeft = ((ModalDoubleFacesCard) card).getLeftHalfCard().getManaCostSymbols();
            List<String> manaCostRight = ((ModalDoubleFacesCard) card).getRightHalfCard().getManaCostSymbols();
            this.setManaCosts(CardUtil.concatManaSymbols(SPLIT_MANA_SEPARATOR_FULL, manaCostLeft, manaCostRight));
        } else if (card instanceof AdventureCard) {
            List<String> manaCostLeft = ((AdventureCard) card).getSpellCard().getManaCostSymbols();
            List<String> manaCostRight = card.getManaCostSymbols();
            this.setManaCosts(CardUtil.concatManaSymbols(SPLIT_MANA_SEPARATOR_FULL, manaCostLeft, manaCostRight));
        } else {
            this.setManaCosts(card.getManaCostSymbols());
        }

        int length = 0;
        List<String> rulesList = new ArrayList<>();
        if (card instanceof SplitCard) {
            for (String rule : ((SplitCard) card).getLeftHalfCard().getRules()) {
                length += rule.length();
                rulesList.add(rule);
            }
            for (String rule : ((SplitCard) card).getRightHalfCard().getRules()) {
                length += rule.length();
                rulesList.add(rule);
            }
            for (String rule : card.getRules()) {
                length += rule.length();
                rulesList.add(rule);
            }
        } else if (card instanceof ModalDoubleFacesCard) {
            // mdf card return main side's rules only (GUI can toggle it to another side)
            for (String rule : card.getRules()) {
                length += rule.length();
                rulesList.add(rule);
            }
        } else {
            for (String rule : card.getRules()) {
                length += rule.length();
                rulesList.add(rule);
            }
        }
        if (length > MAX_RULE_LENGTH) {
            length = 0;
            List<String> shortRules = new ArrayList<>();
            for (String rule : rulesList) {
                if (length + rule.length() + 3 <= MAX_RULE_LENGTH) {
                    shortRules.add(rule);
                    length += rule.length() + 3;
                } else {
                    shortRules.add(rule.substring(0, MAX_RULE_LENGTH - (length + 3)));
                    break;
                }
            }
            Logger.getLogger(CardInfo.class).warn("Card rule text was cut - cardname: " + card.getName());
            this.setRules(shortRules);
        } else {
            this.setRules(rulesList);
        }

        SpellAbility spellAbility = card.getSpellAbility();
        if (spellAbility != null) {
            SpellAbilityType spellAbilityType = spellAbility.getSpellAbilityType();
            if (spellAbilityType == SpellAbilityType.SPLIT_LEFT || spellAbilityType == SpellAbilityType.SPLIT_RIGHT) {
                this.className = this.setCode + '.' + this.name;
                this.splitCardHalf = true;
            }
        }

        // Starting loyalty
        this.startingLoyalty = CardUtil.convertStartingLoyalty(card.getStartingLoyalty());
    }

    public Card getCard() {
        return CardImpl.createCard(className, new CardSetInfo(name, setCode, cardNumber, rarity, new CardGraphicInfo(FrameStyle.valueOf(frameStyle), variousArt)));
    }

    public Card getMockCard() {
        if (this.splitCard) {
            return new MockSplitCard(this);
        } else {
            return new MockCard(this);
        }
    }

    public boolean usesVariousArt() {
        return variousArt;
    }

    public ObjectColor getColor() {
        ObjectColor color = new ObjectColor();
        color.setBlack(black);
        color.setBlue(blue);
        color.setGreen(green);
        color.setRed(red);
        color.setWhite(white);
        return color;
    }

    public ObjectColor getFrameColor() {
        return new ObjectColor(frameColor);
    }

    public FrameStyle getFrameStyle() {
        return FrameStyle.valueOf(this.frameStyle);
    }

    private String joinList(List<String> items) {
        StringBuilder sb = new StringBuilder();
        for (Object item : items) {
            sb.append(item.toString()).append(SEPARATOR);
        }
        return sb.toString();
    }

    public static List<String> parseList(String list, ManaCostSide manaCostSide) {
        if (list.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> res = new ArrayList<>();
        boolean leftSide = true;
        for (String s : list.split(SEPARATOR)) {
            if (s.equals(SPLIT_MANA_SEPARATOR_FULL)) {
                leftSide = false;
                continue;
            }

            if (manaCostSide.equals(ManaCostSide.ALL)
                    || (manaCostSide.equals(ManaCostSide.LEFT) && leftSide)
                    || (manaCostSide.equals(ManaCostSide.RIGHT) && !leftSide)) {
                res.add(s);
            }
        }

        return res;
    }

    public final List<CardType> getTypes() {
        List<CardType> list = new ArrayList<>();
        for (String type : this.types.split(SEPARATOR)) {
            try {
                list.add(CardType.valueOf(type));
            } catch (IllegalArgumentException e) {
            }
        }
        return list;
    }

    public final void setTypes(List<CardType> types) {
        StringBuilder sb = new StringBuilder();
        for (CardType item : types) {
            sb.append(item.name()).append(SEPARATOR);
        }
        this.types = sb.toString();
    }

    public int getManaValue() {
        return manaValue;
    }

    public final List<String> getManaCosts(ManaCostSide manaCostSide) {
        return parseList(manaCosts, manaCostSide);
    }

    public final void setManaCosts(List<String> manaCosts) {
        this.manaCosts = joinList(manaCosts);
    }

    public String getName() {
        return name;
    }

    public String getPower() {
        return power;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public final List<String> getRules() {
        return parseList(rules, ManaCostSide.ALL);
    }

    public final void setRules(List<String> rules) {
        this.rules = joinList(rules);
    }

    public final SubTypes getSubTypes() {
        SubTypes sl = new SubTypes();
        if (subtypes.trim().isEmpty()) {
            return sl;
        }
        for (String s : subtypes.split(SEPARATOR)) {
            sl.add(SubType.fromString(s));
        }
        return sl;
    }

    public final void setSubtypes(List<String> subtypes) {
        this.subtypes = joinList(subtypes);
    }

    public final Set<SuperType> getSupertypes() {
        Set<SuperType> list = EnumSet.noneOf(SuperType.class);
        for (String type : this.supertypes.split(SEPARATOR)) {
            try {
                list.add(SuperType.valueOf(type));
            } catch (IllegalArgumentException e) {
            }
        }
        return list;
    }

    public final void setSuperTypes(Set<SuperType> superTypes) {
        StringBuilder sb = new StringBuilder();
        for (SuperType item : superTypes) {
            sb.append(item.name()).append(SEPARATOR);
        }
        this.supertypes = sb.toString();
    }

    public String getToughness() {
        return toughness;
    }

    public String getStartingLoyalty() {
        return startingLoyalty;
    }

    public String getSetCode() {
        return setCode;
    }

    public String getClassName() {
        return className;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public int getCardNumberAsInt() {
        return cardNumberAsInt;
    }

    public boolean isSplitCard() {
        return splitCard;
    }

    public boolean isSplitFuseCard() {
        return splitCardFuse;
    }

    public boolean isSplitAftermathCard() {
        return splitCardAftermath;
    }

    public boolean isSplitCardHalf() {
        return splitCardHalf;
    }

    public boolean isFlipCard() {
        return flipCard;
    }

    public String getFlipCardName() {
        return flipCardName;
    }

    public boolean isDoubleFaced() {
        return doubleFaced;
    }

    public boolean isNightCard() {
        return nightCard;
    }

    public String getSecondSideName() {
        return secondSideName;
    }

    public boolean isAdventureCard() {
        return adventureCard;
    }

    public String getAdventureSpellName() {
        return adventureSpellName;
    }

    public boolean isModalDoubleFacesCard() {
        return modalDoubleFacesCard;
    }

    public String getModalDoubleFacesSecondSideName() {
        return modalDoubleFacesSecondSideName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof CardInfo)) return false;
        CardInfo other = (CardInfo) o;
        return (this.name.equals(other.name)
                && this.setCode.equals(other.setCode)
                && this.cardNumber.equals(other.cardNumber));
    }

    @Override
    public String toString() {
        return String.format("%s (%s, %s)", getName(), getSetCode(), getCardNumber());
    }
}
