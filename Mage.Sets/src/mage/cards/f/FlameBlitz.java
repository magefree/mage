package mage.cards.f;

import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlameBlitz extends CardImpl {

    public FlameBlitz(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}");

        // At the beginning of your end step, Flame Blitz deals 5 damage to each planeswalker.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new DamageAllEffect(
                5, StaticFilters.FILTER_PERMANENT_PLANESWALKER
        ), TargetController.YOU, false));

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private FlameBlitz(final FlameBlitz card) {
        super(card);
    }

    @Override
    public FlameBlitz copy() {
        return new FlameBlitz(this);
    }
}
