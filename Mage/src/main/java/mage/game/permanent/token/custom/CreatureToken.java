package mage.game.permanent.token.custom;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TokenImpl;

import java.util.Arrays;

/**
 * @author JayDi85
 */
public final class CreatureToken extends TokenImpl {

    public CreatureToken() {
        this(0, 0);
    }

    public CreatureToken(int power, int toughness) {
        this(power, toughness, String.format("%d/%d creature", power, toughness));
    }

    public CreatureToken(int power, int toughness, String description) {
        this(power, toughness, description, null);
    }

    public CreatureToken(int power, int toughness, String description, SubType... extraSubTypes) {
        super("", description);
        this.cardType.add(CardType.CREATURE);
        this.power = new MageInt(power);
        this.toughness = new MageInt(toughness);

        if (extraSubTypes != null) {
            this.subtype.addAll(Arrays.asList(extraSubTypes));
        }
    }

    public CreatureToken withAbility(Ability ability) {
        this.addAbility(ability);
        return this;
    }

    public CreatureToken withColor(String extraColors) {
        ObjectColor extraColorsList = new ObjectColor(extraColors);
        this.getColor(null).addColor(extraColorsList);
        return this;
    }

    public CreatureToken withType(CardType extraType) {
        if (!this.cardType.contains(extraType)) {
            this.cardType.add(extraType);
        }
        return this;
    }

    public CreatureToken withSubType(SubType extraSubType) {
        if (!this.subtype.contains(extraSubType)) {
            this.subtype.add(extraSubType);
        }
        return this;
    }

    public CreatureToken(final CreatureToken token) {
        super(token);
    }

    public CreatureToken copy() {
        return new CreatureToken(this);
    }
}
