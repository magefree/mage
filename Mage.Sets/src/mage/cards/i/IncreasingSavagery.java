package mage.cards.i;

import mage.abilities.condition.common.CastFromGraveyardSourceCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class IncreasingSavagery extends CardImpl {

    public IncreasingSavagery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}{G}");

        // Put five +1/+1 counters on target creature. If this spell was cast from a graveyard, put ten +1/+1 counters on that creature instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(10)),
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(5)),
                CastFromGraveyardSourceCondition.instance, "put five +1/+1 counters on target creature. " +
                "If this spell was cast from a graveyard, put ten +1/+1 counters on that creature instead"
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Flashback {5}{G}{G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{5}{G}{G}")));
    }

    private IncreasingSavagery(final IncreasingSavagery card) {
        super(card);
    }

    @Override
    public IncreasingSavagery copy() {
        return new IncreasingSavagery(this);
    }
}
