package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author lilacLunatic
 */
public final class FantasticarConstructToken extends TokenImpl {

    public FantasticarConstructToken() {
        super("Construct Token", "4/4 colorless Construct artifact creature token with flying and haste");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.CONSTRUCT);
        power = new MageInt(4);
        toughness = new MageInt(4);
        addAbility(HasteAbility.getInstance());
        addAbility(FlyingAbility.getInstance());
    }

    private FantasticarConstructToken(final FantasticarConstructToken token) {
        super(token);
    }

    public FantasticarConstructToken copy() {
        return new FantasticarConstructToken(this);
    }
}

