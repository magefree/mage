package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.util.functions.StackObjectCopyApplier;

import java.util.Iterator;
import java.util.List;

/**
 * @author duncant
 */
public abstract class CopySpellForEachItCouldTargetEffect extends OneShotEffect {

    private static final class ForEachCopyApplier implements StackObjectCopyApplier {

        private final Iterator<MageObjectReferencePredicate> newTargetTypes;

        private ForEachCopyApplier(List<MageObjectReferencePredicate> copiesWithTargets) {
            this.newTargetTypes = copiesWithTargets.iterator();
        }

        @Override
        public void modifySpell(StackObject stackObject, Game game) {
        }

        @Override
        public MageObjectReferencePredicate getNextNewTargetType() {
            if (newTargetTypes.hasNext()) {
                return newTargetTypes.next();
            }
            return null;
        }
    }

    protected CopySpellForEachItCouldTargetEffect() {
        super(Outcome.Copy);
    }

    protected CopySpellForEachItCouldTargetEffect(final CopySpellForEachItCouldTargetEffect effect) {
        super(effect);
    }

    protected abstract StackObject getStackObject(Game game, Ability source);

    protected abstract Player getPlayer(Game game, Ability source);

    /**
     * Prepare copies list. Each item must contain filter for new target (target type).
     *
     * @param stackObject
     * @param player
     * @param source
     * @param game
     * @return
     */
    protected abstract List<MageObjectReferencePredicate> prepareCopiesWithTargets(StackObject stackObject, Player player, Ability source, Game game);

    @Override
    public boolean apply(Game game, Ability source) {
        Player actingPlayer = getPlayer(game, source);
        StackObject stackObject = getStackObject(game, source);
        if (actingPlayer == null || stackObject == null) {
            return false;
        }
        List<MageObjectReferencePredicate> copies = prepareCopiesWithTargets(stackObject, actingPlayer, source, game);
        stackObject.createCopyOnStack(
                game, source, actingPlayer.getId(), false,
                copies.size(), new ForEachCopyApplier(copies)
        );
        return true;
    }
}
