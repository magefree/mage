/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.watchers.common;

import java.util.*;

import mage.MageObject;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public class SpellsCastWatcher extends Watcher {

    private final Map<UUID, List<Spell>> spellsCast = new HashMap<>();
    private int nonCreatureSpells;

    public SpellsCastWatcher() {
        super(WatcherScope.GAME);
    }

    public SpellsCastWatcher(final SpellsCastWatcher watcher) {
        super(watcher);
        this.spellsCast.putAll(watcher.spellsCast);
    }

    @Override
    public SpellsCastWatcher copy() {
        return new SpellsCastWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (EventType.SPELL_CAST == event.getType()) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell == null) {
                MageObject mageObject = game.getLastKnownInformation(event.getTargetId(), Zone.STACK);
                if (mageObject instanceof Spell) {
                    spell = (Spell) mageObject;
                }
            }
            if (spell != null) {
                List<Spell> spells;
                if (!spellsCast.containsKey(spell.getControllerId())) {
                    spells = new ArrayList<>();
                    spellsCast.put(spell.getControllerId(), spells);
                } else {
                    spells = spellsCast.get(spell.getControllerId());
                }
                spells.add(spell.copy()); // copy needed because attributes like color could be changed later
                if (StaticFilters.FILTER_SPELL_NON_CREATURE.match(spell, game)) {
                    nonCreatureSpells++;
                }
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        nonCreatureSpells = 0;
        spellsCast.clear();
    }

    public List<Spell> getSpellsCastThisTurn(UUID playerId) {
        return spellsCast.get(playerId);
    }

    public int getNumberOfNonCreatureSpells() {
        return nonCreatureSpells;
    }
}
