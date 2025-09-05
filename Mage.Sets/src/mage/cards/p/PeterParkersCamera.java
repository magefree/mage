package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterStackObject;
import mage.filter.common.FilterActivatedOrTriggeredAbility;
import mage.target.TargetStackObject;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PeterParkersCamera extends CardImpl {

    private static final FilterStackObject filter = new FilterActivatedOrTriggeredAbility("activated or triggered ability you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public PeterParkersCamera(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // This artifact enters with three film counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.FILM.createInstance(3)),
                "with three film counters on it"
        ));

        // {2}, {T}, Remove a film counter from this artifact: Copy target activated or triggered ability you control. You may choose new targets for the copy.
        Ability ability = new SimpleActivatedAbility(new CopyTargetStackObjectEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.FILM.createInstance()));
        ability.addTarget(new TargetStackObject(filter));
        this.addAbility(ability);
    }

    private PeterParkersCamera(final PeterParkersCamera card) {
        super(card);
    }

    @Override
    public PeterParkersCamera copy() {
        return new PeterParkersCamera(this);
    }
}
