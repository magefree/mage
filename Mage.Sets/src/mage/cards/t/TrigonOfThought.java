

package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 * @author nantuko
 */
public final class TrigonOfThought extends CardImpl {

    public TrigonOfThought(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.CHARGE.createInstance(3)), ""));

        Costs costs = new CostsImpl();
        costs.add(new RemoveCountersSourceCost(CounterType.CHARGE.createInstance()));
        costs.add(new TapSourceCost());
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), costs);
        ability.addManaCost(new GenericManaCost(2));
        this.addAbility(ability);

        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), new TapSourceCost());
        ability2.addManaCost(new ManaCostsImpl<>("{U}{U}"));
        this.addAbility(ability2);
    }

    private TrigonOfThought(final TrigonOfThought card) {
        super(card);
    }

    @Override
    public TrigonOfThought copy() {
        return new TrigonOfThought(this);
    }

}
