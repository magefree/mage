
package mage.cards.r;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.watchers.common.PlayerDamagedBySourceWatcher;

/**
 * @author LevelX2
 */
public final class Reciprocate extends CardImpl {

    public Reciprocate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Exile target creature that dealt damage to you this turn.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new ReciprocateTarget());
    }

    private Reciprocate(final Reciprocate card) {
        super(card);
    }

    @Override
    public Reciprocate copy() {
        return new Reciprocate(this);
    }

}

class ReciprocateTarget extends TargetPermanent {

    public ReciprocateTarget() {
        super(1, 1, StaticFilters.FILTER_PERMANENT_CREATURE, false);
        targetName = "creature that dealt damage to you this turn";
    }

    public ReciprocateTarget(final ReciprocateTarget target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        PlayerDamagedBySourceWatcher watcher = game.getState().getWatcher(PlayerDamagedBySourceWatcher.class, source.getControllerId());
        if (watcher != null && watcher.hasSourceDoneDamage(id, game)) {
            return super.canTarget(id, source, game);
        }
        return false;
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> availablePossibleTargets = super.possibleTargets(sourceControllerId, source, game);
        Set<UUID> possibleTargets = new HashSet<>();
        PlayerDamagedBySourceWatcher watcher = game.getState().getWatcher(PlayerDamagedBySourceWatcher.class, sourceControllerId);
        for (UUID targetId : availablePossibleTargets) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null && watcher != null && watcher.hasSourceDoneDamage(targetId, game)) {
                possibleTargets.add(targetId);
            }
        }
        return possibleTargets;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        int remainingTargets = this.minNumberOfTargets - targets.size();
        if (remainingTargets == 0) {
            return true;
        }
        int count = 0;
        MageObject targetSource = game.getObject(source);
        if(targetSource != null) {
            PlayerDamagedBySourceWatcher watcher = game.getState().getWatcher(PlayerDamagedBySourceWatcher.class, sourceControllerId);
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, sourceControllerId, source, game)) {
                if (!targets.containsKey(permanent.getId()) && permanent.canBeTargetedBy(targetSource, sourceControllerId, game)
                        && watcher != null && watcher.hasSourceDoneDamage(permanent.getId(), game)) {
                    count++;
                    if (count >= remainingTargets) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public ReciprocateTarget copy() {
        return new ReciprocateTarget(this);
    }
}
