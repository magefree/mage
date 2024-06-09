package mage.cards.v;

import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VraskaJoinsUp extends CardImpl {

    public VraskaJoinsUp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);

        // When Vraska Joins Up enters the battlefield, put a deathtouch counter on each creature you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AddCountersAllEffect(
                CounterType.DEATHTOUCH.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE
        )));

        // Whenever a legendary creature you control deals combat damage to a player, draw a card.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new DrawCardSourceControllerEffect(1),
                StaticFilters.FILTER_CONTROLLED_CREATURE_LEGENDARY,
                false, SetTargetPointer.NONE, true
        ));
    }

    private VraskaJoinsUp(final VraskaJoinsUp card) {
        super(card);
    }

    @Override
    public VraskaJoinsUp copy() {
        return new VraskaJoinsUp(this);
    }
}
