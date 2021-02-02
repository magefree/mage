
package mage.cards.o;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author North
 */
public final class OrcishOriflamme extends CardImpl {

    public OrcishOriflamme(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{R}");


        // Attacking creatures you control get +1/+0.
        BoostControlledEffect boostEffect = new BoostControlledEffect(1, 0, Duration.WhileOnBattlefield, StaticFilters.FILTER_ATTACKING_CREATURES, false);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, boostEffect));
    }

    private OrcishOriflamme(final OrcishOriflamme card) {
        super(card);
    }

    @Override
    public OrcishOriflamme copy() {
        return new OrcishOriflamme(this);
    }
}
