
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
 * @author djbrez
 */
public final class Misstep extends CardImpl {
    
    public Misstep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{U}");

        // Creatures target player controls don't untap during that player's next untap step.
        this.getSpellAbility().addEffect(new DontUntapInPlayersNextUntapStepAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES)
                .setText("creatures target player controls don't untap during that player's next untap step"));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private Misstep(final Misstep card) {
        super(card);
    }

    @Override
    public Misstep copy() {
        return new Misstep(this);
    }
}
