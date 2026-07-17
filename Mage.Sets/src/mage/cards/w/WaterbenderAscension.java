package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.WaterbendCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WaterbenderAscension extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.QUEST, 4);

    public WaterbenderAscension(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        // Whenever a creature you control deals combat damage to a player, put a quest counter on this enchantment. Then if it has four or more quest counters on it, draw a card.
        Ability ability = new DealsDamageToAPlayerAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.QUEST.createInstance()),
                StaticFilters.FILTER_CONTROLLED_CREATURE, false, SetTargetPointer.NONE, true
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1), condition,
                "Then if it has four or more quest counters on it, draw a card"
        ));
        this.addAbility(ability);

        // Waterbend {4}: Target creature can't be blocked this turn.
        ability = new SimpleActivatedAbility(new CantBeBlockedTargetEffect(), new WaterbendCost(4));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private WaterbenderAscension(final WaterbenderAscension card) {
        super(card);
    }

    @Override
    public WaterbenderAscension copy() {
        return new WaterbenderAscension(this);
    }
}
