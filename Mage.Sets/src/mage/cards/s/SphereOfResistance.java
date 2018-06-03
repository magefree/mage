
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostIncreasementAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author Plopman
 */
public final class SphereOfResistance extends CardImpl {

    public SphereOfResistance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // Spells cost {1} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostIncreasementAllEffect(1)));
    }

    public SphereOfResistance(final SphereOfResistance card) {
        super(card);
    }

    @Override
    public SphereOfResistance copy() {
        return new SphereOfResistance(this);
    }
}
