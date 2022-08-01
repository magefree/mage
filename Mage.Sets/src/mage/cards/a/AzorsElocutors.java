
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
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
 * @author LevelX2
 */
public final class AzorsElocutors extends CardImpl {

    public AzorsElocutors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W/U}{W/U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // At the beginning of your upkeep, put a filibuster counter on Azor's Elocutors. Then if Azor's Elocutors has five or more filibuster counters on it, you win the game.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new AzorsElocutorsEffect(), TargetController.YOU, false));

        // Whenever a source deals damage to you, remove a filibuster counter from Azor's Elocutors.
        this.addAbility(new AzorsElocutorsTriggeredAbility());
    }

    private AzorsElocutors(final AzorsElocutors card) {
        super(card);
    }

    @Override
    public AzorsElocutors copy() {
        return new AzorsElocutors(this);
    }
}

class AzorsElocutorsTriggeredAbility extends TriggeredAbilityImpl {

    public AzorsElocutorsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new RemoveCounterSourceEffect(CounterType.FILIBUSTER.createInstance()), false);
        setTriggerPhrase("Whenever a source deals damage to you, ");
    }

    public AzorsElocutorsTriggeredAbility(final AzorsElocutorsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AzorsElocutorsTriggeredAbility copy() {
        return new AzorsElocutorsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(this.controllerId);
    }
}

class AzorsElocutorsEffect extends OneShotEffect {

    public AzorsElocutorsEffect() {
        super(Outcome.Benefit);
        staticText = "put a filibuster counter on Azor's Elocutors. Then if Azor's Elocutors has five or more filibuster counters on it, you win the game";
    }

    public AzorsElocutorsEffect(final AzorsElocutorsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            permanent.addCounters(CounterType.FILIBUSTER.createInstance(), source.getControllerId(), source, game);
            if (permanent.getCounters(game).getCount(CounterType.FILIBUSTER) > 4) {
                Player player = game.getPlayer(permanent.getControllerId());
                if (player != null) {
                    player.won(game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public AzorsElocutorsEffect copy() {
        return new AzorsElocutorsEffect(this);
    }
}
