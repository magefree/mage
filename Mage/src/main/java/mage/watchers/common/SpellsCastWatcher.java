package mage.watchers.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.*;
import java.util.stream.Stream;

/**
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
                List<Spell> spells = spellsCast.computeIfAbsent(spell.getControllerId(), x -> new ArrayList<>());
                List<Spell> graveyardSpells = spellsCastFromGraveyard.computeIfAbsent(spell.getControllerId(), x -> new ArrayList<>());
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

    public Stream<Spell> getAllSpellsCastThisTurn() {
        return spellsCast.values().stream().flatMap(Collection::stream);
    }

    public List<Spell> getSpellsCastThisTurn(UUID playerId) {
        return spellsCast.computeIfAbsent(playerId, x -> new ArrayList<>());
    }

    public List<Spell> getSpellsCastFromGraveyardThisTurn(UUID playerId) {
        return spellsCastFromGraveyard.computeIfAbsent(playerId, x -> new ArrayList<>());
    }

    public int getNumberOfNonCreatureSpells() {
        return nonCreatureSpells;
    }

    public UUID getCasterId(Ability source, Game game) {
        for (Map.Entry<UUID, List<Spell>> entry : spellsCast.entrySet()) {
            if (entry.getValue()
                    .stream()
                    .map(Spell::getCard)
                    .map(Card::getMainCard)
                    .anyMatch(card -> card.getId().equals(source.getSourceId())
                            && card.getZoneChangeCounter(game) == source.getSourceObjectZoneChangeCounter())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
