package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.RemoveAllCountersSourceEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.permanent.token.EdgarMarkovsCoffinVampireToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EdgarMarkovsCoffin extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.BLOODLINE, 3);

    public EdgarMarkovsCoffin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.color.setWhite(true);
        this.color.setBlack(true);
        this.nightCard = true;

        // At the beginning of your upkeep, create a 1/1 white and black Vampire creature token with lifelink and put a bloodline counter on Edgar Markov's Coffin. Then if there are three or more bloodline counters on it, remove those counters and transform it.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new EdgarMarkovsCoffinVampireToken()));
        ability.addEffect(new AddCountersSourceEffect(CounterType.BLOODLINE.createInstance()).concatBy("and"));
        ability.addEffect(new ConditionalOneShotEffect(
                new RemoveAllCountersSourceEffect(CounterType.BLOODLINE), condition,
                "Then if there are three or more bloodline counters on it, remove those counters and transform it"
        ).addEffect(new TransformSourceEffect()));
        this.addAbility(ability);
    }

    private EdgarMarkovsCoffin(final EdgarMarkovsCoffin card) {
        super(card);
    }

    @Override
    public EdgarMarkovsCoffin copy() {
        return new EdgarMarkovsCoffin(this);
    }
}
