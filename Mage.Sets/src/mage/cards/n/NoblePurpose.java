package mage.cards.n;

import mage.abilities.common.DealsDamageToAnyTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Quercitron
 */
public final class NoblePurpose extends CardImpl {

    public NoblePurpose(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{W}");

        // Whenever a creature you control deals combat damage, you gain that much life.
        this.addAbility(new DealsDamageToAnyTriggeredAbility(Zone.BATTLEFIELD,
                new GainLifeEffect(SavedDamageValue.MUCH),
                StaticFilters.FILTER_CONTROLLED_A_CREATURE,
                SetTargetPointer.NONE, true, false
        ));
    }

    private NoblePurpose(final NoblePurpose card) {
        super(card);
    }

    @Override
    public NoblePurpose copy() {
        return new NoblePurpose(this);
    }
}
