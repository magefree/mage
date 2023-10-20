package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventDamageByTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.FixedTargets;
import mage.watchers.common.BlockingOrBlockedWatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author xenohedron
 */
public final class SewersOfEstark extends CardImpl {

    public SewersOfEstark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}{B}");

        // Choose target creature. If it's attacking, it can't be blocked this turn. If it's blocking, prevent all combat damage that would be dealt this combat by it and each creature it's blocking.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new SewersOfEstarkEffect());

    }

    private SewersOfEstark(final SewersOfEstark card) {
        super(card);
    }

    @Override
    public SewersOfEstark copy() {
        return new SewersOfEstark(this);
    }
}

class SewersOfEstarkEffect extends OneShotEffect {

    SewersOfEstarkEffect() {
        super(Outcome.PreventDamage);
        this.staticText = "Choose target creature. If it's attacking, it can't be blocked this turn. " +
                "If it's blocking, prevent all combat damage that would be dealt this combat by it and each creature it's blocking.";
    }

    private SewersOfEstarkEffect(final SewersOfEstarkEffect effect) {
        super(effect);
    }

    @Override
    public SewersOfEstarkEffect copy() {
        return new SewersOfEstarkEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (creature == null) {
            return false;
        }
        if (creature.isAttacking()) {
            ContinuousEffect effect = new CantBeBlockedTargetEffect().setTargetPointer(new FixedTarget(creature.getId(), game));
            game.addEffect(effect, source);
            return true;
        }
        if (creature.getBlocking() > 0) {
            List<Permanent> creatures = new ArrayList<>();
            creatures.add(creature);
            for (Permanent blockedByTarget : game.getBattlefield().getActivePermanents(
                    StaticFilters.FILTER_ATTACKING_CREATURE, source.getControllerId(), game)) {
                if (BlockingOrBlockedWatcher.check(blockedByTarget, creature, game)) {
                    creatures.add(blockedByTarget);
                }
            }
            ContinuousEffect effect = new PreventDamageByTargetEffect(Duration.EndOfCombat, true)
                    .setTargetPointer(new FixedTargets(creatures, game));
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}
