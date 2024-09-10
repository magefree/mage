
package mage.cards.p;

import java.util.UUID;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfClashWonEffect;
import mage.abilities.effects.common.DontUntapInPlayersNextUntapStepAllEffect;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class PollenLullaby extends CardImpl {

    public PollenLullaby(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Prevent all combat damage that would be dealt this turn. Clash with an opponent. If you win, creatures that player controls don't untap during the player's next untap step.
        this.getSpellAbility().addEffect(new PreventAllDamageByAllPermanentsEffect(Duration.EndOfTurn, true));
        Effect effect = new DontUntapInPlayersNextUntapStepAllEffect(StaticFilters.FILTER_PERMANENT_CREATURE);
        effect.setText("creatures that player controls don't untap during the player's next untap step");
        this.getSpellAbility().addEffect(new DoIfClashWonEffect(effect, true));
    }

    private PollenLullaby(final PollenLullaby card) {
        super(card);
    }

    @Override
    public PollenLullaby copy() {
        return new PollenLullaby(this);
    }
}