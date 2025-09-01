
package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class GemstoneMine extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.MINING, ComparisonType.EQUAL_TO, 0);

    public GemstoneMine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Gemstone Mine enters the battlefield with three mining counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.MINING.createInstance(3)),
                "with three mining counters on it"));

        // {T}, Remove a mining counter from Gemstone Mine: Add one mana of any color. If there are no mining counters on Gemstone Mine, sacrifice it.
        Ability ability = new AnyColorManaAbility();
        ability.addCost(new RemoveCountersSourceCost(CounterType.MINING.createInstance(1)));
        ability.addEffect(new ConditionalOneShotEffect(
                new SacrificeSourceEffect(), condition,
                "If there are no mining counters on {this}, sacrifice it"
        ));
        this.addAbility(ability);
    }

    private GemstoneMine(final GemstoneMine card) {
        super(card);
    }

    @Override
    public GemstoneMine copy() {
        return new GemstoneMine(this);
    }
}
