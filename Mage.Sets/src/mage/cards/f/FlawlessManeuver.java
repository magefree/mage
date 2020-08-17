package mage.cards.f;

import mage.abilities.condition.common.ControlACommanderCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.common.ControlACommanderHint;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlawlessManeuver extends CardImpl {

    public FlawlessManeuver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // If you control a commander, you may cast this spell without paying its mana cost.
        this.addAbility(new AlternativeCostSourceAbility(null, ControlACommanderCondition.instance)
                .addHint(ControlACommanderHint.instance)
        );

        // Creatures you control gain indestructible until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ));
    }

    private FlawlessManeuver(final FlawlessManeuver card) {
        super(card);
    }

    @Override
    public FlawlessManeuver copy() {
        return new FlawlessManeuver(this);
    }
}
// outstanding move!
