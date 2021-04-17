package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldOrAttacksAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CommanderPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TomeOfLegends extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("your commander");

    static {
        filter.add(CommanderPredicate.instance);
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    public TomeOfLegends(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Tome of Legends enters the battlefield with a page counter on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.PAGE.createInstance()
        ), "with a page counter on it"));

        // Whenever your commander enters the battlefield or attacks, put a page counter on Tome of Legends.
        this.addAbility(new EntersBattlefieldOrAttacksAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.PAGE.createInstance()), filter
        ));

        // {1}, {T}, Remove a page counter from Tome of Legends: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.PAGE.createInstance()));
        this.addAbility(ability);
    }

    private TomeOfLegends(final TomeOfLegends card) {
        super(card);
    }

    @Override
    public TomeOfLegends copy() {
        return new TomeOfLegends(this);
    }
}
