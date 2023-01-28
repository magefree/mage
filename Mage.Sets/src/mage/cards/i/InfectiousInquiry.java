package mage.cards.i;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
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
public final class InfectiousInquiry extends CardImpl {

    public InfectiousInquiry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // You draw two cards and you lose 2 life. Each opponent gets a poison counter.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1, "you"));
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(2).concatBy("and"));
        this.getSpellAbility().addEffect(new AddCountersPlayersEffect(
                CounterType.POISON.createInstance(), TargetController.OPPONENT
        ));
    }

    private InfectiousInquiry(final InfectiousInquiry card) {
        super(card);
    }

    @Override
    public InfectiousInquiry copy() {
        return new InfectiousInquiry(this);
    }
}
