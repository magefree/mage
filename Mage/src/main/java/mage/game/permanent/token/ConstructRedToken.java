package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class ConstructRedToken extends TokenImpl {

    public ConstructRedToken() {
        super("Construct Token", "3/1 red Construct artifact creature token with haste");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.CONSTRUCT);
        color.setRed(true);
        power = new MageInt(3);
        toughness = new MageInt(1);
        addAbility(HasteAbility.getInstance());
    }

    public ConstructRedToken(final ConstructRedToken token) {
        super(token);
    }

    public ConstructRedToken copy() {
        return new ConstructRedToken(this);
    }
}
