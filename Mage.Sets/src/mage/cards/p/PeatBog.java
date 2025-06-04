
package mage.cards.p;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class PeatBog extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.DEPLETION, ComparisonType.EQUAL_TO, 0);

    public PeatBog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Peat Bog enters the battlefield tapped with two depletion counters on it.
        Ability etbAbility = new EntersBattlefieldAbility(
                new TapSourceEffect(true), "tapped with two depletion counters on it"
        );
        etbAbility.addEffect(new AddCountersSourceEffect(CounterType.DEPLETION.createInstance(2)));
        this.addAbility(etbAbility);

        // {T}, Remove a depletion counter from Peat Bog: Add {B}{B}. If there are no depletion counters on Peat Bog, sacrifice it.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlackMana(2), new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.DEPLETION.createInstance(1)));
        ability.addEffect(new ConditionalOneShotEffect(
                new SacrificeSourceEffect(), condition,
                "If there are no depletion counters on {this}, sacrifice it"
        ));
        this.addAbility(ability);
    }

    private PeatBog(final PeatBog card) {
        super(card);
    }

    @Override
    public PeatBog copy() {
        return new PeatBog(this);
    }
}
