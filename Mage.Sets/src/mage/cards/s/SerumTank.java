package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author CountAndromalius
 */
public final class SerumTank extends CardImpl {

    public SerumTank(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Whenever {this} or another artifact comes into play, put a charge counter on {this}.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), StaticFilters.FILTER_PERMANENT_ARTIFACT, false, false
        ));

        // {3}, {tap}, Remove a charge counter from {this}: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new GenericManaCost(3)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.CHARGE.createInstance()));
        this.addAbility(ability);
    }

    private SerumTank(final SerumTank card) {
        super(card);
    }

    @Override
    public SerumTank copy() {
        return new SerumTank(this);
    }
}
