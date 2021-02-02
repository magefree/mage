
package mage.cards.q;

import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.game.permanent.token.QuestForTheGravelordZombieToken;

import java.util.UUID;

/**
 * @author North
 */
public final class QuestForTheGravelord extends CardImpl {

    public QuestForTheGravelord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");

        // Whenever a creature dies, you may put a quest counter on Quest for the Gravelord.
        this.addAbility(new DiesCreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.QUEST.createInstance()), true));

        // Remove three quest counters from Quest for the Gravelord and sacrifice it: Create a 5/5 black Zombie Giant creature token.
        this.addAbility(new SimpleActivatedAbility(
                new CreateTokenEffect(new QuestForTheGravelordZombieToken()),
                new CompositeCost(
                        new RemoveCountersSourceCost(CounterType.QUEST.createInstance(3)),
                        new SacrificeSourceCost(),
                        "Remove three quest counters from {this} and sacrifice it"
                )
        ));
    }

    private QuestForTheGravelord(final QuestForTheGravelord card) {
        super(card);
    }

    @Override
    public QuestForTheGravelord copy() {
        return new QuestForTheGravelord(this);
    }
}
