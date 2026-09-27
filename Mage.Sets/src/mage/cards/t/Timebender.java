package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.RemoveCounterTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterPermanentOrSuspendedCard;
import mage.target.common.TargetPermanentOrSuspendedCard;

import java.util.UUID;

/**
 * @author L_J
 */
public final class Timebender extends CardImpl {

    private static final FilterPermanentOrSuspendedCard filter
            = new FilterPermanentOrSuspendedCard("permanent with a time counter on it or suspended card");

    static {
        filter.getPermanentFilter().add(CounterType.TIME.getPredicate());
    }

    public Timebender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Morph {U}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl<>("{U}")));

        // When Timebender is turned face up, choose one —
        // Remove two time counters from target permanent or suspended card.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(
                new RemoveCounterTargetEffect(CounterType.TIME.createInstance(2)));
        ability.addTarget(new TargetPermanentOrSuspendedCard());

        // Put two time counters on target permanent with a time counter on it or suspended card.
        Mode mode = new Mode(new AddCountersTargetEffect(CounterType.TIME.createInstance(2)));
        mode.addTarget(new TargetPermanentOrSuspendedCard(filter, false));
        ability.addMode(mode);
        this.addAbility(ability);

    }

    private Timebender(final Timebender card) {
        super(card);
    }

    @Override
    public Timebender copy() {
        return new Timebender(this);
    }
}
