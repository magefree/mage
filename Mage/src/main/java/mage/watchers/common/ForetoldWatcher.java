/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.watchers.common;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.util.CardUtil;
import mage.watchers.Watcher;

/**
 *
 * @author jeffwadsworth
 */
public class ForetoldWatcher extends Watcher {
    
    // If a card was Foretold during a turn, this list stores it.  Cleared at the end of the turn.

    private final Set<UUID> foretoldCardsThisTurn = new HashSet<>();

    public ForetoldWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST
                && event.getZone() == Zone.EXILED) {
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (spell != null) {
                UUID exileId = CardUtil.getExileZoneId(spell.getSourceId().toString() + "foretellAbility", game);
                if (exileId != null) {
                    foretoldCardsThisTurn.add(spell.getSourceId());
                }
            }
        }
    }

    public boolean foretoldSpellWasCast(UUID sourceId) {
        return foretoldCardsThisTurn.contains(sourceId);
    }

    @Override
    public void reset() {
        super.reset();
        foretoldCardsThisTurn.clear();
    }
}
