package mage.watchers.common;

import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2
 */
public class DragonOnTheBattlefieldWhileSpellWasCastWatcher extends Watcher {

    private static final FilterPermanent filter = new FilterPermanent(SubType.DRAGON, "Dragons");

    private final Set<UUID> castWithDragonOnTheBattlefield = new HashSet<>();

    public DragonOnTheBattlefieldWhileSpellWasCastWatcher() {
        super(WatcherScope.GAME);
    }

    public DragonOnTheBattlefieldWhileSpellWasCastWatcher(final DragonOnTheBattlefieldWhileSpellWasCastWatcher watcher) {
        super(watcher);
        this.castWithDragonOnTheBattlefield.addAll(watcher.castWithDragonOnTheBattlefield);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            // targetId is the unique ID of the spell
            Spell spell = game.getState().getStack().getSpell(event.getTargetId());
            // revealed a Dragon card or controlled a Dragon as you cast the spell
            if (spell != null) {
                boolean revealedOrOnBattlefield = false;
                if (spell.getSpellAbility() != null) {
                    for (Cost cost : spell.getSpellAbility().getCosts()) {
                        if (cost instanceof RevealTargetFromHandCost) {
                            revealedOrOnBattlefield = ((RevealTargetFromHandCost) cost).getNumberRevealedCards() > 0;
                            break;
                        }
                    }
                }
                if (!revealedOrOnBattlefield) {
                    revealedOrOnBattlefield = game.getBattlefield().countAll(filter, spell.getControllerId(), game) > 0;
                }
                if (revealedOrOnBattlefield) {
                    castWithDragonOnTheBattlefield.add(spell.getId());
                }

            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        castWithDragonOnTheBattlefield.clear();
    }

    public boolean castWithConditionTrue(UUID spellId) {
        return castWithDragonOnTheBattlefield.contains(spellId);
    }

    @Override
    public DragonOnTheBattlefieldWhileSpellWasCastWatcher copy() {
        return new DragonOnTheBattlefieldWhileSpellWasCastWatcher(this);
    }
}
