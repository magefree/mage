
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CanBlockOnlyFlyingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author L_J
 */
public final class Novellamental extends CardImpl {

    public Novellamental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Novellamental can block only creatures with flying.
        this.addAbility(new CanBlockOnlyFlyingAbility());
    }

    private Novellamental(final Novellamental card) {
        super(card);
    }

    @Override
    public Novellamental copy() {
        return new Novellamental(this);
    }
}
