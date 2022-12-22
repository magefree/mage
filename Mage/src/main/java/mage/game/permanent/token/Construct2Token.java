package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author weirddan455
 */
public class Construct2Token extends TokenImpl {

    public Construct2Token() {
        super("Construct Token", "2/2 colorless Construct artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.CONSTRUCT);
        power = new MageInt(2);
        toughness = new MageInt(2);

        this.setExpansionSetCodeForImage("BRO");
        this.setTokenType(2);
    }

    private Construct2Token(final Construct2Token token) {
        super(token);
    }

    @Override
    public Construct2Token copy() {
        return new Construct2Token(this);
    }
}
