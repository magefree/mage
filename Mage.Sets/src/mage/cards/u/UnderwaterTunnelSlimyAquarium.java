package mage.cards.u;

import mage.abilities.common.UnlockThisDoorTriggeredAbility;
import mage.abilities.effects.keyword.ManifestDreadEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardSetInfo;
import mage.cards.RoomCard;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnderwaterTunnelSlimyAquarium extends RoomCard {

    public UnderwaterTunnelSlimyAquarium(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, "{U}", "{3}{U}");

        // Underwater Tunnel
        // When you unlock this door, surveil 2.
        this.getLeftHalfCard().addAbility(new UnlockThisDoorTriggeredAbility(new SurveilEffect(2), false, true));

        // Slimy Aquarium
        // When you unlock this door, manifest dread, then put a +1/+1 counter on that creature.
        this.getRightHalfCard().addAbility(new UnlockThisDoorTriggeredAbility(new ManifestDreadEffect(CounterType.P1P1.createInstance()), false, false));
    }

    private UnderwaterTunnelSlimyAquarium(final UnderwaterTunnelSlimyAquarium card) {
        super(card);
    }

    @Override
    public UnderwaterTunnelSlimyAquarium copy() {
        return new UnderwaterTunnelSlimyAquarium(this);
    }
}
