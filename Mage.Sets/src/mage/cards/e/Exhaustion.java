
package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DontUntapInPlayersNextUntapStepAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

/**
 *
 * @author emerald000
 */
public final class Exhaustion extends CardImpl {

    public Exhaustion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}");

        // Creatures and lands target opponent controls don't untap during their next untap step.
        Effect effect = new DontUntapInPlayersNextUntapStepAllEffect(StaticFilters.FILTER_PERMANENT_CREATURE_OR_LAND);
        effect.setText("creatures and lands target opponent controls don't untap during their next untap step.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private Exhaustion(final Exhaustion card) {
        super(card);
    }

    @Override
    public Exhaustion copy() {
        return new Exhaustion(this);
    }
}