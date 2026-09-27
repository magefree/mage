package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.RemoveCounterTargetEffect;
import mage.abilities.keyword.SuspendAbility;
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
public final class ShivanSandMage extends CardImpl {

    private static final FilterPermanentOrSuspendedCard filter
            = new FilterPermanentOrSuspendedCard("permanent with a time counter on it or suspended card");

    static {
        filter.getPermanentFilter().add(CounterType.TIME.getPredicate());
    }

    public ShivanSandMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Shivan Sand-Mage enters the battlefield, choose one —
        // Remove two time counters from target permanent or suspended card.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new RemoveCounterTargetEffect(CounterType.TIME.createInstance(2)));
        ability.addTarget(new TargetPermanentOrSuspendedCard());

        // Put two time counters on target permanent with a time counter on it or suspended card.
        Mode mode = new Mode(new AddCountersTargetEffect(CounterType.TIME.createInstance(2)));
        mode.addTarget(new TargetPermanentOrSuspendedCard(filter, false));
        ability.addMode(mode);
        this.addAbility(ability);

        // Suspend 4-{R}
        this.addAbility(new SuspendAbility(4, new ManaCostsImpl<>("{R}"), this));
    }

    private ShivanSandMage(final ShivanSandMage card) {
        super(card);
    }

    @Override
    public ShivanSandMage copy() {
        return new ShivanSandMage(this);
    }
}
