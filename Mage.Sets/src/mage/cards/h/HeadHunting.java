
package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class HeadHunting extends CardImpl {

    public HeadHunting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Put a bounty counter on target creature an opponent controls.
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.BOUNTY.createInstance()));

        // Each opponent loses 1 life.
        this.getSpellAbility().addEffect(new LoseLifeOpponentsEffect(1));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    private HeadHunting(final HeadHunting card) {
        super(card);
    }

    @Override
    public HeadHunting copy() {
        return new HeadHunting(this);
    }
}
