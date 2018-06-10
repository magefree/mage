
package mage.cards.m;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

/**
 *
 * @author TheElk801
 */
public final class MultanisPresence extends CardImpl {

    public MultanisPresence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        // Whenever a spell you've cast is countered, draw a card.
        this.addAbility(new MultanisPresenceTriggeredAbility(), new MultanisPresenceWatcher());
    }

    public MultanisPresence(final MultanisPresence card) {
        super(card);
    }

    @Override
    public MultanisPresence copy() {
        return new MultanisPresence(this);
    }
}

class MultanisPresenceTriggeredAbility extends TriggeredAbilityImpl {

    public MultanisPresenceTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), false);
    }

    public MultanisPresenceTriggeredAbility(final MultanisPresenceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MultanisPresenceTriggeredAbility copy() {
        return new MultanisPresenceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        MultanisPresenceWatcher watcher = (MultanisPresenceWatcher) game.getState().getWatchers().get(MultanisPresenceWatcher.class.getSimpleName());
        return (watcher.getSpellsCastThisTurn(controllerId).contains(event.getTargetId()));
    }

    @Override
    public String getRule() {
        return "Whenever a spell you've cast is countered, " + super.getRule();
    }
}

class MultanisPresenceWatcher extends Watcher {

    private final HashMap<UUID, List<UUID>> spellsCast = new HashMap<>();

    public MultanisPresenceWatcher() {
        super(MultanisPresenceWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public MultanisPresenceWatcher(final MultanisPresenceWatcher watcher) {
        super(watcher);
        this.spellsCast.putAll(watcher.spellsCast);
    }

    @Override
    public MultanisPresenceWatcher copy() {
        return new MultanisPresenceWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (GameEvent.EventType.SPELL_CAST == event.getType()) {
            Spell spell = game.getSpellOrLKIStack(event.getTargetId());
            if (spell != null) {
                List<UUID> spellIds;
                if (!spellsCast.containsKey(spell.getControllerId())) {
                    spellIds = new ArrayList<>();
                } else {
                    spellIds = spellsCast.get(spell.getControllerId());
                }
                spellIds.add(spell.getId());
                spellsCast.put(spell.getControllerId(), spellIds);
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        spellsCast.clear();
    }

    public List<UUID> getSpellsCastThisTurn(UUID playerId) {
        return spellsCast.get(playerId);
    }
}
