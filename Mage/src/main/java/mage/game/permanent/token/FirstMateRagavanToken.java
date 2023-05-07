package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 * @author TheElk801
 */
public final class FirstMateRagavanToken extends TokenImpl {

    public FirstMateRagavanToken() {
        super("First Mate Ragavan", "First Mate Ragavan, a legendary 2/1 red Monkey Pirate creature token");
        this.supertype.add(SuperType.LEGENDARY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.color.setRed(true);
        this.subtype.add(SubType.MONKEY);
        this.subtype.add(SubType.PIRATE);
        this.cardType.add(CardType.CREATURE);
    }

    public FirstMateRagavanToken(final FirstMateRagavanToken token) {
        super(token);
    }

    public FirstMateRagavanToken copy() {
        return new FirstMateRagavanToken(this);
    }
}
