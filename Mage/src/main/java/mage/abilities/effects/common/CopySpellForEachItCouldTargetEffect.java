package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.functions.SpellCopyApplier;

import java.util.Iterator;
import java.util.List;

/**
 * @author duncant
 */
public abstract class CopySpellForEachItCouldTargetEffect extends OneShotEffect {

    private static final class CopyApplier implements SpellCopyApplier {

        private final Iterator<MageObjectReferencePredicate> iterator;

        private CopyApplier(List<MageObjectReferencePredicate> predicates) {
            this.iterator = predicates.iterator();
        }

        @Override
        public void modifySpell(Spell spell, Game game) {
        }

        @Override
        public MageObjectReferencePredicate getNextPredicate() {
            if (iterator.hasNext()) {
                return iterator.next();
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

    protected abstract Spell getSpell(Game game, Ability source);

    protected abstract Player getPlayer(Game game, Ability source);

    protected abstract List<MageObjectReferencePredicate> getPossibleTargets(Spell spell, Player player, Ability source, Game game);

    @Override
    public boolean apply(Game game, Ability source) {
        Player actingPlayer = getPlayer(game, source);
        Spell spell = getSpell(game, source);
        if (actingPlayer == null || spell == null) {
            return false;
        }
        List<MageObjectReferencePredicate> predicates = getPossibleTargets(spell, actingPlayer, source, game);
        spell.createCopyOnStack(
                game, source, actingPlayer.getId(), false,
                predicates.size(), new CopyApplier(predicates)
        );
        return true;
    }
}
