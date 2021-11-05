package mage.cards.s;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SengirTheDarkBaron extends CardImpl {

    public SengirTheDarkBaron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever another creature dies, put two +1/+1 counters on Sengir, the Dark Baron.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), false, true
        ));

        // Whenever another player loses the game, you gain life equal to that player's life total as the turn began.
        this.addAbility(new SengirTheDarkBaronTriggeredAbility());

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private SengirTheDarkBaron(final SengirTheDarkBaron card) {
        super(card);
    }

    @Override
    public SengirTheDarkBaron copy() {
        return new SengirTheDarkBaron(this);
    }
}

class SengirTheDarkBaronTriggeredAbility extends TriggeredAbilityImpl {

    SengirTheDarkBaronTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
        this.addWatcher(new SengirTheDarkBaronWatcher());
    }

    private SengirTheDarkBaronTriggeredAbility(final SengirTheDarkBaronTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SengirTheDarkBaronTriggeredAbility copy() {
        return new SengirTheDarkBaronTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        SengirTheDarkBaronWatcher watcher = game.getState().getWatcher(SengirTheDarkBaronWatcher.class);
        if (watcher == null) {
            return false;
        }
        this.getEffects().clear();
        this.addEffect(new GainLifeEffect(watcher.getLife(event.getPlayerId())));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever another player loses the game, " +
                "you gain life equal to that player's life total as the turn began.";
    }
}

class SengirTheDarkBaronWatcher extends Watcher {

    private final Map<UUID, Integer> playerMap = new HashMap<>();

    SengirTheDarkBaronWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BEGINNING_PHASE_PRE) {
            game.getPlayers()
                    .values()
                    .stream()
                    .forEach(player -> playerMap.put(player.getId(), player.getLife()));
        }
    }

    @Override
    public void reset() {
        playerMap.clear();
        super.reset();
    }

    int getLife(UUID playerId) {
        return Math.max(0, playerMap.getOrDefault(playerId, 0));
    }
}
