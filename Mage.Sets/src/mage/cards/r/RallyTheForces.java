package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author Loki
 */
public final class RallyTheForces extends CardImpl {

    public RallyTheForces (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}");

        // Attacking creatures get +1/+0 and gain first strike until end of turn.
        Effect effect = new BoostAllEffect(1, 0, Duration.EndOfTurn, StaticFilters.FILTER_ATTACKING_CREATURES, false);
        effect.setText("Attacking creatures get +1/+0");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityAllEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_ATTACKING_CREATURES, false);
        effect.setText("and gain first strike until end of turn");
        this.getSpellAbility().addEffect(effect);
    }

    public RallyTheForces (final RallyTheForces card) {
        super(card);
    }

    @Override
    public RallyTheForces copy() {
        return new RallyTheForces(this);
    }

}
