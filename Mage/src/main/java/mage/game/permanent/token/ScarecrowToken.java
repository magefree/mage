package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class ScarecrowToken extends TokenImpl {

    public ScarecrowToken() {
        super("Scarecrow", "2/2 colorless Scarecrow artifact creature token named Scarecrow");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.SCARECROW);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    private ScarecrowToken(final ScarecrowToken token) {
        super(token);
    }

    public ScarecrowToken copy() {
        return new ScarecrowToken(this);
    }
}
