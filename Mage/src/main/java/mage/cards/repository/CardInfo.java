/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.cards.repository;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.FrameStyle;
import mage.cards.mock.MockCard;
import mage.cards.mock.MockSplitCard;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.SpellAbilityType;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author North
 */
@DatabaseTable(tableName = "card")
public class CardInfo {

    private static final int MAX_RULE_LENGTH = 700;

    private static final String SEPARATOR = "@@@";
    @DatabaseField(indexName = "name_index")
    protected String name;
    @DatabaseField(indexName = "setCode_cardNumber_index")
    protected String cardNumber;
    @DatabaseField(indexName = "setCode_cardNumber_index")
    protected String setCode;
    @DatabaseField(indexName = "className_index")
    protected String className;
    @DatabaseField
    protected String power;
    @DatabaseField
    protected String toughness;
    @DatabaseField
    protected String startingLoyalty;
    @DatabaseField
    protected int convertedManaCost;
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

    public CardInfo() {
    }

    public CardInfo(Card card) {
        this.name = card.getName();
        this.cardNumber = card.getCardNumber();
        this.setCode = card.getExpansionSetCode();
        this.className = card.getClass().getCanonicalName();
        this.power = card.getPower().toString();
        this.toughness = card.getToughness().toString();
        this.convertedManaCost = card.getConvertedManaCost();
        this.rarity = card.getRarity();
        this.splitCard = card.isSplitCard();

        this.flipCard = card.isFlipCard();
        this.flipCardName = card.getFlipCardName();

        this.doubleFaced = card.isTransformable() && card.getSecondCardFace() != null;
        this.nightCard = card.isNightCard();
        Card secondSide = card.getSecondCardFace();
        if (secondSide != null) {
            this.secondSideName = secondSide.getName();
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
        this.setSubtypes(card.getSubtype(null));
        this.setSuperTypes(card.getSupertype());
        this.setManaCosts(card.getManaCost().getSymbols());

        int length = 0;
        for (String rule : card.getRules()) {
            length += rule.length();
        }
        if (length > MAX_RULE_LENGTH) {
            length = 0;
            ArrayList<String> shortRules = new ArrayList<>();
            for (String rule : card.getRules()) {
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
            this.setRules(card.getRules());
        }

        SpellAbility spellAbility = card.getSpellAbility();
        if (spellAbility != null) {
            SpellAbilityType spellAbilityType = spellAbility.getSpellAbilityType();
            if (spellAbilityType == SpellAbilityType.SPLIT_LEFT || spellAbilityType == SpellAbilityType.SPLIT_RIGHT) {
                this.className = this.setCode + "." + this.name;
                this.splitCardHalf = true;
            }
        }

        // Starting loyalty
        if (card.getCardType().contains(CardType.PLANESWALKER)) {
            for (Ability ab : card.getAbilities()) {
                if (ab instanceof PlanswalkerEntersWithLoyalityCountersAbility) {
                    this.startingLoyalty = "" + ((PlanswalkerEntersWithLoyalityCountersAbility) ab).getStartingLoyalty();
                }
            }
            if (this.startingLoyalty == null) {
                //Logger.getLogger(CardInfo.class).warn("Planeswalker `" + card.getName() + "` missing starting loyalty");
                this.startingLoyalty = "";
            }
        } else {
            this.startingLoyalty = "";
        }
    }

    public Card getCard() {
        return CardImpl.createCard(className, new CardSetInfo(name, setCode, cardNumber, rarity));
    }

    public Card getMockCard() {
        if (this.splitCard) {
            return new MockSplitCard(this);
        } else {
            return new MockCard(this);
        }
    }

    public boolean usesVariousArt() { return variousArt; }

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

    private List<String> parseList(String list) {
        if (list.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.asList(list.split(SEPARATOR));
    }

    public final List<CardType> getTypes() {
        ArrayList<CardType> list = new ArrayList<>();
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

    public int getConvertedManaCost() {
        return convertedManaCost;
    }

    public final List<String> getManaCosts() {
        return parseList(manaCosts);
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
        return parseList(rules);
    }

    public final void setRules(List<String> rules) {
        this.rules = joinList(rules);
    }

    public final List<String> getSubTypes() {
        return parseList(subtypes);
    }

    public final void setSubtypes(List<String> subtypes) {
        this.subtypes = joinList(subtypes);
    }

    public final List<String> getSupertypes() {
        return parseList(supertypes);
    }

    public final void setSuperTypes(List<String> superTypes) {
        this.supertypes = joinList(superTypes);
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

    public boolean isSplitCard() {
        return splitCard;
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
}
