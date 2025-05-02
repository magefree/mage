package mage.cards.w;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

/**
 *
 * @author TheElk801
 */
public final class WhisperingSnitch extends CardImpl {

    public WhisperingSnitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When you surveil for the first time in a turn, Whispering Spy deals 1 damage to each opponent and you gain 1 life.
        this.addAbility(new WhisperingSnitchTriggeredAbility());
    }

    private WhisperingSnitch(final WhisperingSnitch card) {
        super(card);
    }

    @Override
    public WhisperingSnitch copy() {
        return new WhisperingSnitch(this);
    }
}

class WhisperingSnitchTriggeredAbility extends TriggeredAbilityImpl {

    public WhisperingSnitchTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamagePlayersEffect(1, TargetController.OPPONENT), false);
        this.addEffect(new GainLifeEffect(1));
        this.addWatcher(new WhisperingSnitchWatcher());
    }

    private WhisperingSnitchTriggeredAbility(final WhisperingSnitchTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SURVEILED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(getControllerId())) {
            WhisperingSnitchWatcher watcher = game.getState().getWatcher(WhisperingSnitchWatcher.class);
            return watcher != null && watcher.getTimesSurveiled(getControllerId()) == 1;
        }
        return false;
    }

    @Override
    public WhisperingSnitchTriggeredAbility copy() {
        return new WhisperingSnitchTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you surveil for the first time each turn, "
                + "{this} deals 1 damage to each opponent and you gain 1 life.";
    }
}

class WhisperingSnitchWatcher extends Watcher {

    private final Map<UUID, Integer> timesSurveiled = new HashMap<>();

    public WhisperingSnitchWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SURVEILED) {
            timesSurveiled.put(event.getPlayerId(), getTimesSurveiled(event.getPlayerId()) + 1);
        }
    }

    @Override
    public void reset() {
        super.reset();
        timesSurveiled.clear();
    }

    public int getTimesSurveiled(UUID playerId) {
        return timesSurveiled.getOrDefault(playerId, 0);
    }
}
