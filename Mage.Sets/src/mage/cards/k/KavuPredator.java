
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

    private KavuPredator(final KavuPredator card) {
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
        setTriggerPhrase("Whenever an opponent gains life, ");
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
        return event.getType() == GameEvent.EventType.GAINED_LIFE;
    }
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(this.controllerId).contains(event.getPlayerId())) {
            this.getEffects().get(0).setValue("gainedLife", event.getAmount());
            return true;
        }
        return false;
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
                permanent.addCounters(CounterType.P1P1.createInstance(gainedLife), source.getControllerId(), source, game);
                Player player = game.getPlayer(source.getControllerId());
                if (player != null) {
                    game.informPlayers(player.getLogName() + " puts " + gainedLife + " +1/+1 counter on " + permanent.getName());
                }
            }
            return true;
        }
        return false;
    }
}
