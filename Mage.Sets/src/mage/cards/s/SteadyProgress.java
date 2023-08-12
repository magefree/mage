

package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public final class SteadyProgress extends CardImpl {

    public SteadyProgress (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");

        // Proliferate. (You choose any number of permanents and/or players with counters on them, then give each another counter of a kind already there.)        
        this.getSpellAbility().addEffect(new ProliferateEffect());
        
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    public SteadyProgress (final SteadyProgress card) {
        super(card);
    }

    @Override
    public SteadyProgress copy() {
        return new SteadyProgress(this);
    }

}
