
package mage.cards.s;

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
 * @author LoneFox
 */
public final class Stampede extends CardImpl {

    public Stampede(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}{G}");

        // Attacking creatures get +1/+0 and gain trample until end of turn.
        Effect effect = new BoostAllEffect(1, 0, Duration.EndOfTurn, StaticFilters.FILTER_ATTACKING_CREATURES, false);
        effect.setText("attacking creatures get +1/+0");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityAllEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_ATTACKING_CREATURES);
        effect.setText("and gain trample until end of turn");
        this.getSpellAbility().addEffect(effect);
    }

    private Stampede(final Stampede card) {
        super(card);
    }

    @Override
    public Stampede copy() {
        return new Stampede(this);
    }
}
