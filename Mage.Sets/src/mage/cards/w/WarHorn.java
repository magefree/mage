
package mage.cards.w;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingCreature;

/**
 *
 * @author LoneFox

 */
public final class WarHorn extends CardImpl {

    public WarHorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // Attacking creatures you control get +1/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 0,
            Duration.WhileOnBattlefield, new FilterAttackingCreature(), false)));
    }

    private WarHorn(final WarHorn card) {
        super(card);
    }

    @Override
    public WarHorn copy() {
        return new WarHorn(this);
    }
}
