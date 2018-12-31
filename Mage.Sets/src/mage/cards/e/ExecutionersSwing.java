
package mage.cards.e;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.watchers.common.SourceDidDamageWatcher;

/**
 *
 * @author LevelX2
 */
public final class ExecutionersSwing extends CardImpl {

    public ExecutionersSwing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}{B}");

        // Target creature that dealt damage this turn gets -5/-5 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-5, -5, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanentThatDealtDamageThisTurn());

        this.getSpellAbility().addWatcher(new SourceDidDamageWatcher());
    }

    public ExecutionersSwing(final ExecutionersSwing card) {
        super(card);
    }

    @Override
    public ExecutionersSwing copy() {
        return new ExecutionersSwing(this);
    }
}

class TargetCreaturePermanentThatDealtDamageThisTurn extends TargetPermanent {

    public TargetCreaturePermanentThatDealtDamageThisTurn() {
        super(1, 1, StaticFilters.FILTER_PERMANENT_CREATURE, false);
        targetName = "creature that dealt damage this turn";
    }

    public TargetCreaturePermanentThatDealtDamageThisTurn(final TargetCreaturePermanentThatDealtDamageThisTurn target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        SourceDidDamageWatcher watcher = (SourceDidDamageWatcher) game.getState().getWatchers().get(SourceDidDamageWatcher.class.getSimpleName());
        if (watcher != null) {
            if (watcher.damageSources.contains(id)) {
                return super.canTarget(id, source, game);
            }
        }
        return false;
    }

    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        int remainingTargets = this.minNumberOfTargets - targets.size();
        if (remainingTargets <= 0) {
            return true;
        }
        int count = 0;
        MageObject targetSource = game.getObject(sourceId);
        SourceDidDamageWatcher watcher = (SourceDidDamageWatcher) game.getState().getWatchers().get(SourceDidDamageWatcher.class.getSimpleName());
        if (watcher != null && targetSource != null) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, sourceControllerId, sourceId, game)) {
                if (!targets.containsKey(permanent.getId()) && watcher.damageSources.contains(permanent.getId())) {
                    if (!notTarget || permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) {
                        count++;
                        if (count >= remainingTargets) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        Set<UUID> availablePossibleTargets = super.possibleTargets(sourceId, sourceControllerId, game);
        Set<UUID> possibleTargets = new HashSet<>();
        SourceDidDamageWatcher watcher = (SourceDidDamageWatcher) game.getState().getWatchers().get(SourceDidDamageWatcher.class.getSimpleName());
        if (watcher != null) {
            for (UUID targetId : availablePossibleTargets) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null && !targets.containsKey(permanent.getId()) && watcher.damageSources.contains(targetId)) {
                    possibleTargets.add(targetId);
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public TargetCreaturePermanentThatDealtDamageThisTurn copy() {
        return new TargetCreaturePermanentThatDealtDamageThisTurn(this);
    }
}
