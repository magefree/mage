package mage.cards.p;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PrologueToPhyresis extends CardImpl {

    public PrologueToPhyresis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Each opponent gets a poison counter.
        this.getSpellAbility().addEffect(new AddCountersPlayersEffect(
                CounterType.POISON.createInstance(), TargetController.OPPONENT
        ));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private PrologueToPhyresis(final PrologueToPhyresis card) {
        super(card);
    }

    @Override
    public PrologueToPhyresis copy() {
        return new PrologueToPhyresis(this);
    }
}
