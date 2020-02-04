package mage.game.permanent.token.custom;

import mage.MageInt;
import mage.ObjectColor;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TokenImpl;

/**
 *
 * @author JayDi85
 */
public final class ElementalCreatureToken extends TokenImpl {

    public ElementalCreatureToken() {
        this(0, 0);
    }

    public ElementalCreatureToken(int power, int toughness) {
        this(power, toughness, String.format("%d/%d Elemental creature", power, toughness));
    }

    public ElementalCreatureToken(int power, int toughness, String description) {
        this(power, toughness, description, (ObjectColor) null);
    }

    public ElementalCreatureToken(int power, int toughness, String description, ObjectColor color) {
        super("", description);
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(power);
        this.toughness = new MageInt(toughness);

        if (color != null) {
            this.color.addColor(color);
        }
    }

    public ElementalCreatureToken(final ElementalCreatureToken token) {
        super(token);
    }

    public ElementalCreatureToken copy() {
        return new ElementalCreatureToken(this);
    }
}
