package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class ShipwreckPatrol extends CardImpl {

    public ShipwreckPatrol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When this creature enters, tap target creature an opponent controls and put a stun counter on it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect());
        ability.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance()).setText("and put a stun counter on it"));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private ShipwreckPatrol(final ShipwreckPatrol card) {
        super(card);
    }

    @Override
    public ShipwreckPatrol copy() {
        return new ShipwreckPatrol(this);
    }
}
