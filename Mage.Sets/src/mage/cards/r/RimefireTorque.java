package mage.cards.r;

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.CopyNextSpellDelayedTriggeredAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ChosenSubtypePredicate;

import java.util.UUID;

/**
 * @author muz
 */
public final class RimefireTorque extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a permanent you control of the chosen type");

    static {
        filter.add(ChosenSubtypePredicate.TRUE);
    }

    public RimefireTorque(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{U}");

        // As this artifact enters, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.Benefit)));

        // Whenever a permanent you control of the chosen type enters, put a charge counter on this artifact.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), filter, false
        ));

        // {T}, Remove three charge counters from this artifact: When you next cast an instant or sorcery spell this turn, copy it. You may choose new targets for the copy.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(
                new CreateDelayedTriggeredAbilityEffect(new CopyNextSpellDelayedTriggeredAbility()),
                new TapSourceCost()
        );
        ability.addCost(new RemoveCountersSourceCost(CounterType.CHARGE.createInstance(3)));
        this.addAbility(ability);
    }

    private RimefireTorque(final RimefireTorque card) {
        super(card);
    }

    @Override
    public RimefireTorque copy() {
        return new RimefireTorque(this);
    }
}
