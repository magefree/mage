package mage.cards.h;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.replacement.ModifyCountersAddedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class HardenedScales extends CardImpl {

    public HardenedScales(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        // If one or more +1/+1 counters would be put on a creature you control, that many plus one +1/+1 counters are put on it instead.
        this.addAbility(new SimpleStaticAbility(new ModifyCountersAddedEffect(
                StaticFilters.FILTER_CONTROLLED_CREATURE, CounterType.P1P1
        )));

    }

    private HardenedScales(final HardenedScales card) {
        super(card);
    }

    @Override
    public HardenedScales copy() {
        return new HardenedScales(this);
    }
}
