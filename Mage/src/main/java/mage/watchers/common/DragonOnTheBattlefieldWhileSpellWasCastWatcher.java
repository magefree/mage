package mage.watchers.common;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.costs.common.RevealDragonFromHandCost;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;

/**
 * @author LevelX2
 */
public class DragonOnTheBattlefieldWhileSpellWasCastWatcher extends Watcher {

    private final Set<MageObjectReference> castWithDragonOnTheBattlefield = new HashSet<>();

    public DragonOnTheBattlefieldWhileSpellWasCastWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        // targetId is the unique ID of the spell
        Spell spell = game.getSpell(event.getTargetId());

        // If one of these spells is copied, the controller of the copy will get the "Dragon bonus" only if
        // a Dragon card was revealed as an additional cost. The copy wasn't cast, so whether you controlled
        // a Dragon won't matter. (2015-02-25)

        // TODO: Should only check if you controlled a Dragon as you cast, currently also does "if you revealed a Dragon card"
        if (spell != null
                && spell
                .getSpellAbility()
                .getCosts()
                .stream()
                .filter(RevealDragonFromHandCost.class::isInstance)
                .map(RevealDragonFromHandCost.class::cast)
                .anyMatch(RevealDragonFromHandCost::isRevealedOrControlled)) {
            castWithDragonOnTheBattlefield.add(new MageObjectReference(spell.getCard(), game, 0));
            castWithDragonOnTheBattlefield.add(new MageObjectReference(spell.getCard(), game, 1));
        }
    }

    @Override
    public void reset() {
        super.reset();
        castWithDragonOnTheBattlefield.clear();
    }

    public boolean checkCondition(Ability source, Game game) {
        return castWithDragonOnTheBattlefield
                .stream()
                .anyMatch(mor -> mor.refersTo(source.getSourceObject(game), game));
    }
}
