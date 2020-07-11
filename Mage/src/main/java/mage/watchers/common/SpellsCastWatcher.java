/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.watchers.common;

import mage.MageObject;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class SpellsCastWatcher extends Watcher {

    private final Map<UUID, List<Spell>> spellsCast = new HashMap<>();
    private final Map<UUID, List<Spell>> spellsCastFromGraveyard = new HashMap<>();
    private int nonCreatureSpells;

    public SpellsCastWatcher() {
        super(WatcherScope.GAME);
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
                List<Spell> graveyardSpells;
                if (!spellsCast.containsKey(spell.getControllerId())) {
                    spells = new ArrayList<>();
                    spellsCast.put(spell.getControllerId(), spells);
                    graveyardSpells = new ArrayList<>();
                    spellsCastFromGraveyard.put(spell.getControllerId(), graveyardSpells);

                } else {
                    spells = spellsCast.get(spell.getControllerId());
                    graveyardSpells = spellsCastFromGraveyard.get(spell.getControllerId());
                }
                spells.add(spell.copy()); // copy needed because attributes like color could be changed later
                if (event.getZone() == Zone.GRAVEYARD) {
                    graveyardSpells.add(spell.copy());
                }
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
        spellsCastFromGraveyard.clear();
    }

    public List<Spell> getSpellsCastThisTurn(UUID playerId) {
        return spellsCast.get(playerId);
    }

    public List<Spell> getSpellsCastFromGraveyardThisTurn(UUID playerId) {
        return spellsCastFromGraveyard.get(playerId);
    }

    public int getNumberOfNonCreatureSpells() {
        return nonCreatureSpells;
    }
}
