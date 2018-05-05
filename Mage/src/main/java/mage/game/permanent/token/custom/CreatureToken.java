package mage.game.permanent.token.custom;

import mage.MageInt;
import mage.constants.CardType;
import mage.game.permanent.token.TokenImpl;

/**
 *
 * @author JayDi85
 */
public class CreatureToken  extends TokenImpl {

    public CreatureToken() {
        this(0, 0);
    }

    public CreatureToken(int power, int toughness) {
        super("", String.format("%d/%d creature", power, toughness));
        this.cardType.add(CardType.CREATURE);
        this.power = new MageInt(power);
        this.toughness = new MageInt(toughness);
    }

    public CreatureToken(final CreatureToken token) {
        super(token);
    }

    public CreatureToken copy() {
        return new CreatureToken(this);
    }
}
