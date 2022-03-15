package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class PurphorossInterventionToken extends TokenImpl {

    public PurphorossInterventionToken() {
        this(0);
    }
    public PurphorossInterventionToken(int power) {
        super("Elemental Token", "X/1 red Elemental creature token with trample and haste");
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.ELEMENTAL);
        this.color.setRed(true);
        this.power = new MageInt(power);
        this.toughness = new MageInt(1);
        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
    }

    private PurphorossInterventionToken(final PurphorossInterventionToken token) {
        super(token);
    }

    public PurphorossInterventionToken copy() {
        return new PurphorossInterventionToken(this);
    }
}

