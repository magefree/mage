package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.UnlockThisDoorTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ManifestDreadEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardSetInfo;
import mage.cards.RoomCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnderwaterTunnelSlimyAquarium extends RoomCard {
    public UnderwaterTunnelSlimyAquarium(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}", "{3}{U}");

        // Underwater Tunnel
        // When you unlock this door, surveil 2.
        this.getLeftHalfCard().addAbility(new UnlockThisDoorTriggeredAbility(new SurveilEffect(2), false, true));

        // Slimy Aquarium
        // When you unlock this door, manifest dread, then put a +1/+1 counter on that creature.
        this.getRightHalfCard().addAbility(new UnlockThisDoorTriggeredAbility(new SlimyAquariumEffect(), false, false));
    }

    private UnderwaterTunnelSlimyAquarium(final UnderwaterTunnelSlimyAquarium card) {
        super(card);
    }

    @Override
    public UnderwaterTunnelSlimyAquarium copy() {
        return new UnderwaterTunnelSlimyAquarium(this);
    }
}

class SlimyAquariumEffect extends OneShotEffect {

    SlimyAquariumEffect() {
        super(Outcome.Benefit);
        staticText = "manifest dread, then put a +1/+1 counter on that creature";
    }

    private SlimyAquariumEffect(final SlimyAquariumEffect effect) {
        super(effect);
    }

    @Override
    public SlimyAquariumEffect copy() {
        return new SlimyAquariumEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Permanent permanent = ManifestDreadEffect.doManifestDread(player, source, game);
        return permanent != null && permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
    }
}
