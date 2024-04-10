package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.CommittedCrimeTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BanditsHaul extends CardImpl {

    public BanditsHaul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Whenever you commit a crime, put a loot counter on Bandit's Haul. This ability triggers only once each turn.
        this.addAbility(new CommittedCrimeTriggeredAbility(
                new AddCountersSourceEffect(CounterType.LOOT.createInstance())
        ).setTriggersOnceEachTurn(true));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {2}, {T}, Remove two loot counters from Bandit's Haul: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.LOOT.createInstance(2)));
        this.addAbility(ability);
    }

    private BanditsHaul(final BanditsHaul card) {
        super(card);
    }

    @Override
    public BanditsHaul copy() {
        return new BanditsHaul(this);
    }
}
