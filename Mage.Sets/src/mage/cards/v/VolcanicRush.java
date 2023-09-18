package mage.cards.v;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class VolcanicRush extends CardImpl {

    public VolcanicRush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{R}");

        // Attacking creatures get +2/+0 and gain trample until end of turn.
        Effect effect = new BoostAllEffect(2, 0, Duration.EndOfTurn, StaticFilters.FILTER_ATTACKING_CREATURES, false);
        effect.setText("Attacking creatures get +2/+0");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityAllEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_ATTACKING_CREATURES);
        effect.setText("and gain trample until end of turn");
        this.getSpellAbility().addEffect(effect);
    }

    private VolcanicRush(final VolcanicRush card) {
        super(card);
    }

    @Override
    public VolcanicRush copy() {
        return new VolcanicRush(this);
    }
}
