
package mage.cards.f;

import java.util.UUID;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author fearphage
 */
public final class FracturedPowerstone extends CardImpl {

    public FracturedPowerstone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
    }

    public FracturedPowerstone(final FracturedPowerstone card) {
        super(card);
    }

    @Override
    public FracturedPowerstone copy() {
        return new FracturedPowerstone(this);
    }
}
