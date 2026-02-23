package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.UnlockThisDoorTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.RoomCard;
import mage.counters.CounterType;
import mage.game.permanent.token.GlimmerToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrandEntrywayElegantRotunda extends RoomCard {

    public GrandEntrywayElegantRotunda(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, "{1}{W}", "{2}{W}");

        // Grand Entryway
        // When you unlock this door, create a 1/1 white Glimmer enchantment creature token.
        this.getLeftHalfCard().addAbility(new UnlockThisDoorTriggeredAbility(new CreateTokenEffect(new GlimmerToken()), false, true));

        // Elegant Rotunda
        // When you unlock this door, put a +1/+1 counter on each of up to two target creatures.
        Ability ability = new UnlockThisDoorTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false, false);
        ability.addTarget(new TargetCreaturePermanent(0, 2));
        this.getRightHalfCard().addAbility(ability);
    }

    private GrandEntrywayElegantRotunda(final GrandEntrywayElegantRotunda card) {
        super(card);
    }

    @Override
    public GrandEntrywayElegantRotunda copy() {
        return new GrandEntrywayElegantRotunda(this);
    }
}
