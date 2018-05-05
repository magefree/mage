package mage.game.permanent.token.custom;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TokenImpl;
import mage.util.SubTypeList;

/**
 *
 * @author JayDi85
 */
public class CreatureToken extends TokenImpl {

    public CreatureToken() {
        this(0, 0);
    }

    public CreatureToken(int power, int toughness) {
        this(power, toughness, String.format("%d/%d creature", power, toughness));
    }

    public CreatureToken(int power, int toughness, String description) {
        this(power, toughness, description, (SubTypeList) null);
    }

    public CreatureToken(int power, int toughness, String description, SubType extraSubType) {
        this(power, toughness, description, new SubTypeList(extraSubType));
    }

    public CreatureToken(int power, int toughness, String description, SubTypeList extraSubTypes) {
        super("", description);
        this.cardType.add(CardType.CREATURE);
        this.power = new MageInt(power);
        this.toughness = new MageInt(toughness);

        if (extraSubTypes != null) {
            this.subtype.addAll(extraSubTypes);
        }
    }

    public CreatureToken(final CreatureToken token) {
        super(token);
    }

    public CreatureToken copy() {
        return new CreatureToken(this);
    }
}
