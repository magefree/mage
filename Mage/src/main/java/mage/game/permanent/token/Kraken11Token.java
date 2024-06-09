package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class Kraken11Token extends TokenImpl {

    public Kraken11Token() {
        super("Kraken Token", "1/1 blue Kraken creature token with trample");
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.KRAKEN);
        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(TrampleAbility.getInstance());
    }

    private Kraken11Token(final Kraken11Token token) {
        super(token);
    }

    public Kraken11Token copy() {
        return new Kraken11Token(this);
    }
}
