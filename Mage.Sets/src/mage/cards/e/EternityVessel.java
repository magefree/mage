
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class EternityVessel extends CardImpl {

    public EternityVessel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{6}");

        // Eternity Vessel enters the battlefield with X charge counters on it, where X is your life total.
        this.addAbility(new EntersBattlefieldAbility(new EternityVesselEffect()));

        // Landfall - Whenever a land enters the battlefield under your control, you may have your life total become the number of charge counters on Eternity Vessel.
        this.addAbility(new LandfallAbility(Zone.BATTLEFIELD, new EternityVesselEffect2(), true));
    }

    private EternityVessel(final EternityVessel card) {
        super(card);
    }

    @Override
    public EternityVessel copy() {
        return new EternityVessel(this);
    }
}

class EternityVesselEffect extends OneShotEffect {

    public EternityVesselEffect() {
        super(Outcome.Benefit);
        staticText = "with X charge counters on it, where X is your life total";
    }

    private EternityVesselEffect(final EternityVesselEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent vessel = game.getPermanentEntering(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (vessel != null && controller != null) {
            int amount = controller.getLife();
            if (amount > 0) {
                vessel.addCounters(CounterType.CHARGE.createInstance(amount), source.getControllerId(), source, game);

            }
            return true;
        }
        return false;
    }

    @Override
    public EternityVesselEffect copy() {
        return new EternityVesselEffect(this);
    }
}

class EternityVesselEffect2 extends OneShotEffect {

    public EternityVesselEffect2() {
        super(Outcome.Benefit);
        staticText = "you may have your life total become the number of charge counters on {this}";
    }

    private EternityVesselEffect2(final EternityVesselEffect2 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent vessel = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (vessel != null && controller != null) {
            controller.setLife(vessel.getCounters(game).getCount(CounterType.CHARGE), game, source);
            return true;
        }
        return false;
    }

    @Override
    public EternityVesselEffect2 copy() {
        return new EternityVesselEffect2(this);
    }
}
