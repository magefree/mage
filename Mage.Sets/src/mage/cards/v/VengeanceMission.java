package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author muz
 */
public final class VengeanceMission extends CardImpl {

    public VengeanceMission(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // Whenever a creature dies, put a quest counter on this enchantment. Target opponent loses 1 life. That player loses 2 life instead if there are seven or more quest counters on this enchantment.
        Ability ability = new DiesCreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.QUEST.createInstance()), false);
        ability.addEffect(new ConditionalOneShotEffect(
            new LoseLifeTargetEffect(2), new LoseLifeTargetEffect(1), new SourceHasCounterCondition(CounterType.QUEST, 7),
            "target opponent loses 1 life. That player loses 2 life instead if there are seven or more quest counters on {this}"
        ));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private VengeanceMission(final VengeanceMission card) {
        super(card);
    }

    @Override
    public VengeanceMission copy() {
        return new VengeanceMission(this);
    }
}
