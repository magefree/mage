package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
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

        // Trigon of Thought enters the battlefield with three charge counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.CHARGE.createInstance(3)), "with three charge counters on it"));

        // {U}{U}, {T}: Put a charge counter on Trigon of Thought.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), new TapSourceCost());
        ability2.addCost(new ManaCostsImpl<>("{U}{U}"));
        this.addAbility(ability2);

        // {2}, {T}, Remove a charge counter from Trigon of Thought: Draw a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.CHARGE.createInstance()));
        this.addAbility(ability);
    }

    private TrigonOfThought(final TrigonOfThought card) {
        super(card);
    }

    @Override
    public TrigonOfThought copy() {
        return new TrigonOfThought(this);
    }

}
