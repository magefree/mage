package mage.game.permanent.token;

import mage.abilities.mana.AnyColorManaAbility;
import mage.constants.CardType;
import mage.constants.SuperType;

/**
 * @author TheElk801
 */
public final class IcyManalithToken extends TokenImpl {

    public IcyManalithToken() {
        super("Icy Manalith", "colorless snow artifact token named Icy Manalith with \"{T}: Add one mana of any color.\"");
        this.supertype.add(SuperType.SNOW);
        this.cardType.add(CardType.ARTIFACT);

        this.addAbility(new AnyColorManaAbility());
    }

    private IcyManalithToken(final IcyManalithToken token) {
        super(token);
    }

    public IcyManalithToken copy() {
        return new IcyManalithToken(this);
    }
}
