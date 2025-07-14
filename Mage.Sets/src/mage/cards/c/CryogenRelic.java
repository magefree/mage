package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrLeavesSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CryogenRelic extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("tapped creature");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public CryogenRelic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{U}");

        // When this artifact enters or leaves the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldOrLeavesSourceTriggeredAbility(new DrawCardSourceControllerEffect(1), false));

        // {1}{U}, Sacrifice this artifact: Put a stun counter on up to one target tapped creature.
        Ability ability = new SimpleActivatedAbility(new AddCountersTargetEffect(CounterType.STUN.createInstance()), new ManaCostsImpl<>("{1}{U}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);
    }

    private CryogenRelic(final CryogenRelic card) {
        super(card);
    }

    @Override
    public CryogenRelic copy() {
        return new CryogenRelic(this);
    }
}
