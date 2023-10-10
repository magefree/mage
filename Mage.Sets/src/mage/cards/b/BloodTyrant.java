
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class BloodTyrant extends CardImpl {

    public BloodTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{B}{R}");
        this.subtype.add(SubType.VAMPIRE);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of your upkeep, each player loses 1 life. Put a +1/+1 counter on Blood Tyrant for each 1 life lost this way.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new BloodTyrantEffect(), TargetController.YOU, false));

        // Whenever a player loses the game, put five +1/+1 counters on Blood Tyrant.
        this.addAbility(new PlayerLosesTheGameTriggeredAbility());

    }

    private BloodTyrant(final BloodTyrant card) {
        super(card);
    }

    @Override
    public BloodTyrant copy() {
        return new BloodTyrant(this);
    }
}

class PlayerLosesTheGameTriggeredAbility extends TriggeredAbilityImpl {

    public PlayerLosesTheGameTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance(5)), false);
    }

    private PlayerLosesTheGameTriggeredAbility(final PlayerLosesTheGameTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PlayerLosesTheGameTriggeredAbility copy() {
        return new PlayerLosesTheGameTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOSES;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever a player loses the game, put five +1/+1 counters on {this}.";
    }
}

class BloodTyrantEffect extends OneShotEffect {

    public BloodTyrantEffect() {
        super(Outcome.Benefit);
        staticText = "each player loses 1 life. Put a +1/+1 counter on {this} for each 1 life lost this way";
    }

    private BloodTyrantEffect(final BloodTyrantEffect effect) {
        super(effect);
    }

    @Override
    public BloodTyrantEffect copy() {
        return new BloodTyrantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int counters = 0;
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    if (player.loseLife(1, game, source, false) > 0) {
                        counters++;
                    }
                }
            }
            Permanent bloodTyrant = game.getPermanent(source.getSourceId());
            if (bloodTyrant != null && counters > 0) {
                bloodTyrant.addCounters(CounterType.P1P1.createInstance(counters), source.getControllerId(), source, game);
            }
            return true;
        }
        return false;
    }
}
