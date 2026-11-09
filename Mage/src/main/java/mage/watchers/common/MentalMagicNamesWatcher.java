package mage.watchers.common;

import mage.MageObject;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.*;

/**
 * Default watcher of Mental Magic games, no need to add it with abilities
 * <p>
 * Each card name can be played only once per game, by any player. A name is taken when a spell
 * with that name is cast, or when a card is turned into it (then it's reserved for that card).
 *
 * @author xico7
 */
public class MentalMagicNamesWatcher extends Watcher {

    private final Set<String> castNames = new HashSet<>();
    private final Map<String, UUID> reservedNames = new HashMap<>(); // name -> main card id

    public MentalMagicNamesWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        MageObject spell = game.getStack().getSpell(event.getTargetId());
        if (spell == null) {
            spell = game.getLastKnownInformation(event.getTargetId(), Zone.STACK);
        }
        if (spell == null || (spell instanceof Spell && ((Spell) spell).isFaceDown(game))) {
            return;
        }
        if (!spell.getName().isEmpty()) {
            castNames.add(spell.getName());
        }
    }

    /**
     * Name can't be chosen for a card anymore
     */
    public boolean isTaken(String name) {
        return castNames.contains(name) || reservedNames.containsKey(name);
    }

    /**
     * Name was not cast yet and is not reserved for another card
     */
    public boolean canCast(String name, UUID mainCardId) {
        if (castNames.contains(name)) {
            return false;
        }
        UUID reservedFor = reservedNames.get(name);
        return reservedFor == null || reservedFor.equals(mainCardId);
    }

    public void reserve(String name, UUID mainCardId) {
        reservedNames.put(name, mainCardId);
    }
}
