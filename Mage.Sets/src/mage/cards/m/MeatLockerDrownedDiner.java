package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.UnlockThisDoorTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.RoomCard;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MeatLockerDrownedDiner extends RoomCard {
    public MeatLockerDrownedDiner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, "{2}{U}", "{3}{U}{U}");

        // Meat Locker
        // When you unlock this door, tap up to one target creature and put two stun counters on it.
        Ability ability = new UnlockThisDoorTriggeredAbility(new TapTargetEffect(), false, true);
        ability.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance(2))
                .setText("and put two stun counters on it"));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.getLeftHalfCard().addAbility(ability);

        // Drowned Diner
        // When you unlock this door, draw three cards, then discard a card.
        this.getRightHalfCard().addAbility(new UnlockThisDoorTriggeredAbility(
                new DrawDiscardControllerEffect(3, 1), false, false
        ));
    }

    private MeatLockerDrownedDiner(final MeatLockerDrownedDiner card) {
        super(card);
    }

    @Override
    public MeatLockerDrownedDiner copy() {
        return new MeatLockerDrownedDiner(this);
    }
}
