package mage.cards.q;

import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToACreatureAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class QuestForTheGemblades extends CardImpl {

    public QuestForTheGemblades(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // Whenever a creature you control deals combat damage to a creature, you may put a quest counter on Quest for the Gemblades.
        this.addAbility(new DealsDamageToACreatureAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.QUEST.createInstance()), true,
                StaticFilters.FILTER_CONTROLLED_A_CREATURE, SetTargetPointer.PERMANENT, true
        ));

        // Remove a quest counter from Quest for the Gemblades and sacrifice it: Put four +1/+1 counters on target creature.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(4)),
                new CompositeCost(
                        new RemoveCountersSourceCost(CounterType.QUEST.createInstance()),
                        new SacrificeSourceCost(),
                        "Remove a quest counter from {this} and sacrifice it"
                ));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private QuestForTheGemblades(final QuestForTheGemblades card) {
        super(card);
    }

    @Override
    public QuestForTheGemblades copy() {
        return new QuestForTheGemblades(this);
    }
}
