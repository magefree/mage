
package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.DontUntapInPlayersNextUntapStepAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;

/**
 *
 * @author ciaccona007
 */
public final class ManaVapors extends CardImpl {

    public ManaVapors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Lands target player controls don't untap during their next untap step.
        getSpellAbility().addEffect(new DontUntapInPlayersNextUntapStepAllEffect(StaticFilters.FILTER_LANDS));
        getSpellAbility().addTarget(new TargetPlayer());
    }

    private ManaVapors(final ManaVapors card) {
        super(card);
    }

    @Override
    public ManaVapors copy() {
        return new ManaVapors(this);
    }
}