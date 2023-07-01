

package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Susucr
 */
public final class BallisticBoulder extends TokenImpl {

    public BallisticBoulder() {
        super("Ballistic Boulder", "2/1 colorless Construct artifact creature token with flying named Ballistic Boulder");
        cardType.add(CardType.CREATURE);
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.CONSTRUCT);
        power = new MageInt(2);
        toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());
    }

    public BallisticBoulder(final BallisticBoulder token) {
        super(token);
    }

    public BallisticBoulder copy() {
        return new BallisticBoulder(this);
    }
}
