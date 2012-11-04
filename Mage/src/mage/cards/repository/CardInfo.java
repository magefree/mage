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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.ObjectColor;
import mage.cards.Card;
import mage.cards.CardImpl;

/**
 *
 * @author North
 */
@DatabaseTable(tableName = "card")
public class CardInfo {

    private static final String SEPARATOR = "@@@";
    @DatabaseField
    protected String name;
    @DatabaseField
    protected int cardNumber;
    @DatabaseField
    protected String setCode;
    @DatabaseField
    protected String className;
    @DatabaseField
    protected String power;
    @DatabaseField
    protected String toughness;
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
    @DatabaseField
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
    protected boolean doubleFaced;

    public CardInfo() {
    }

    public CardInfo(Card card) {
        this.name = card.getName();
        this.cardNumber = card.getCardNumber();
        this.setCode = card.getExpansionSetCode();
        this.className = card.getClass().getCanonicalName();
        this.power = card.getPower().toString();
        this.toughness = card.getToughness().toString();
        this.convertedManaCost = card.getManaCost().convertedManaCost();
        this.rarity = card.getRarity();
        this.doubleFaced = card.canTransform();

        this.blue = card.getColor().isBlue();
        this.black = card.getColor().isBlack();
        this.green = card.getColor().isGreen();
        this.red = card.getColor().isRed();
        this.white = card.getColor().isWhite();

        this.setTypes(card.getCardType());
        this.setSubtypes(card.getSubtype());
        this.setSuperTypes(card.getSupertype());
        this.setManaCosts(card.getManaCost().getSymbols());
        this.setRules(card.getRules());
    }

    public Card getCard() {
        return CardImpl.createCard(className);
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

    private String joinList(List items) {
        StringBuilder sb = new StringBuilder();
        for (Object item : items) {
            sb.append(item.toString()).append(SEPARATOR);
        }
        return sb.toString();
    }

    private List<String> parseList(String list) {
        return Arrays.asList(list.split(SEPARATOR));
    }

    public final List<CardType> getTypes() {
        ArrayList<CardType> list = new ArrayList<CardType>();
        for (String type : this.types.split(SEPARATOR)) {
            try {
                list.add(CardType.valueOf(type));
            } catch (Exception e) {
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

    public String getSetCode() {
        return setCode;
    }

    public String getClassName() {
        return className;
    }
}
