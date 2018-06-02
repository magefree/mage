
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class KavuPredator extends CardImpl {

    public KavuPredator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.KAVU);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Whenever an opponent gains life, put that many +1/+1 counters on Kavu Predator.
        this.addAbility(new KavuPredatorTriggeredAbility());
    }

    public KavuPredator(final KavuPredator card) {
        super(card);
    }

    @Override
    public KavuPredator copy() {
        return new KavuPredator(this);
    }
}

class KavuPredatorTriggeredAbility extends TriggeredAbilityImpl {

    public KavuPredatorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new KavuPredatorEffect());
    }

    public KavuPredatorTriggeredAbility(final KavuPredatorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public KavuPredatorTriggeredAbility copy() {
        return new KavuPredatorTriggeredAbility(this);
    }


    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.GAINED_LIFE;
    }
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(this.controllerId).contains(event.getPlayerId())) {
            this.getEffects().get(0).setValue("gainedLife", new Integer(event.getAmount()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent gains life, " + super.getRule();
    }
}

class KavuPredatorEffect extends OneShotEffect {

    public KavuPredatorEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "put that many +1/+1 counters on {this}";
    }

    public KavuPredatorEffect(final KavuPredatorEffect effect) {
        super(effect);
    }

    @Override
    public KavuPredatorEffect copy() {
        return new KavuPredatorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            Integer gainedLife  = (Integer) this.getValue("gainedLife");
            if (gainedLife != null) {
                permanent.addCounters(CounterType.P1P1.createInstance(gainedLife.intValue()), source, game);
                Player player = game.getPlayer(source.getControllerId());
                if (player != null) {
                    game.informPlayers(new StringBuilder(player.getLogName()).append(" puts ").append(gainedLife).append(" +1/+1 counter on ").append(permanent.getName()).toString());
                }
            }
            return true;
        }
        return false;
    }
}
