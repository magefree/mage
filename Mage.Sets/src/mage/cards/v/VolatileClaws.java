package mage.cards.v;

import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VolatileClaws extends CardImpl {

    public VolatileClaws(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Until end of turn, creatures you control get +2/+0 and gain all creature types.
        this.getSpellAbility().addEffect(new BoostControlledEffect(
                2, 0, Duration.EndOfTurn
        ).setText("until end of turn, creatures you control get +2/+0"));
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                ChangelingAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("and gain all creature types"));
    }

    private VolatileClaws(final VolatileClaws card) {
        super(card);
    }

    @Override
    public VolatileClaws copy() {
        return new VolatileClaws(this);
    }
}
