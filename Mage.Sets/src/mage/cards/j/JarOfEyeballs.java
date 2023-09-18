package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveAllCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;

import java.util.UUID;

/**
 * @author awjackson
 */
public final class JarOfEyeballs extends CardImpl {

    public JarOfEyeballs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Whenever a creature you control dies, put two eyeball counters on Jar of Eyeballs.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new AddCountersSourceEffect(CounterType.EYEBALL.createInstance(2)),
                false, StaticFilters.FILTER_CONTROLLED_A_CREATURE
        ));

        // {3}, {tap}, Remove all eyeball counters from Jar of Eyeballs:
        // Look at the top X cards of your library, where X is the number of eyeball counters removed this way.
        // Put one of them into your hand and the rest on the bottom of your library in any order.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(
                new LookLibraryAndPickControllerEffect(JarOfEyeballsValue.instance, 1, PutCards.HAND, PutCards.BOTTOM_ANY),
                new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveAllCountersSourceCost(CounterType.EYEBALL));
        this.addAbility(ability);
    }

    private JarOfEyeballs(final JarOfEyeballs card) {
        super(card);
    }

    @Override
    public JarOfEyeballs copy() {
        return new JarOfEyeballs(this);
    }
}

enum JarOfEyeballsValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int countersRemoved = 0;
        for (Cost cost : sourceAbility.getCosts()) {
            if (cost instanceof RemoveAllCountersSourceCost) {
                countersRemoved = ((RemoveAllCountersSourceCost) cost).getRemovedCounters();
            }
        }
        return countersRemoved;
    }

    @Override
    public JarOfEyeballsValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "the number of eyeball counters removed this way";
    }
}
