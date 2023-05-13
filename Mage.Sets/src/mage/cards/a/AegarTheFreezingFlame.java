package mage.cards.a;

import mage.MageInt;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public final class AegarTheFreezingFlame extends CardImpl {

    public AegarTheFreezingFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a creature or planeswalker an opponent controls is dealt excess damage, if a Giant, Wizard, or spell you controlled dealt damage to it this turn, draw a card.
        this.addAbility(new AegarTheFreezingFlameTriggeredAbility());
    }

    private AegarTheFreezingFlame(final AegarTheFreezingFlame card) {
        super(card);
    }

    @Override
    public AegarTheFreezingFlame copy() {
        return new AegarTheFreezingFlame(this);
    }
}

class AegarTheFreezingFlameTriggeredAbility extends TriggeredAbilityImpl {

    AegarTheFreezingFlameTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
        this.addWatcher(new AegarTheFreezingFlameWatcher());
    }

    private AegarTheFreezingFlameTriggeredAbility(final AegarTheFreezingFlameTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedEvent dEvent = (DamagedEvent) event;
        if (dEvent.getExcess() < 1
                || !game.getOpponents(getControllerId()).contains(game.getControllerId(event.getTargetId()))) {
            return false;
        }
        AegarTheFreezingFlameWatcher watcher = game.getState().getWatcher(AegarTheFreezingFlameWatcher.class);
        return watcher != null && watcher.checkDamage(getControllerId(), event.getTargetId(), game);
    }

    @Override
    public AegarTheFreezingFlameTriggeredAbility copy() {
        return new AegarTheFreezingFlameTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a creature or planeswalker an opponent controls is dealt excess damage, " +
                "if a Giant, Wizard, or spell you controlled dealt damage to it this turn, draw a card.";
    }
}

class AegarTheFreezingFlameWatcher extends Watcher {

    private final Map<UUID, Set<MageObjectReference>> playerMap = new HashMap<>();
    private static final Set<MageObjectReference> emptySet = new HashSet<>();

    AegarTheFreezingFlameWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.DAMAGED_PERMANENT) {
            return;
        }
        DamagedEvent dEvent = (DamagedEvent) event;
        MageObject sourceObject = game.getObject(event.getSourceId());
        if (sourceObject == null) {
            return;
        }
        if (game.getSpellOrLKIStack(event.getSourceId()) == null
                && !sourceObject.hasSubtype(SubType.GIANT, game)
                && !sourceObject.hasSubtype(SubType.WIZARD, game)) {
            return;
        }
        playerMap
                .computeIfAbsent(game.getControllerId(event.getSourceId()), x -> new HashSet<>())
                .add(new MageObjectReference(event.getTargetId(), game));
    }

    boolean checkDamage(UUID playerId, UUID targetId, Game game) {
        return playerMap.getOrDefault(playerId, emptySet).contains(new MageObjectReference(targetId, game));
    }

    @Override
    public void reset() {
        playerMap.clear();
        super.reset();
    }
}
