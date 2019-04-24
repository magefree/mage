
package mage.cards.a;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.watchers.common.SourceDidDamageWatcher;

/**
 *
 * @author jeffwadsworth
 */
public final class AvengingArrow extends CardImpl {

    public AvengingArrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Destroy target creature that dealt damage this turn.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new AvengingArrowTarget());
        this.getSpellAbility().addWatcher(new SourceDidDamageWatcher());
    }

    public AvengingArrow(final AvengingArrow card) {
        super(card);
    }

    @Override
    public AvengingArrow copy() {
        return new AvengingArrow(this);
    }
}

class AvengingArrowTarget extends TargetPermanent {

    public AvengingArrowTarget() {
        super(1, 1, StaticFilters.FILTER_PERMANENT_CREATURE, false);
        targetName = "creature that dealt damage this turn";
    }

    public AvengingArrowTarget(final AvengingArrowTarget target) {
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
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        Set<UUID> availablePossibleTargets = super.possibleTargets(sourceId, sourceControllerId, game);
        Set<UUID> possibleTargets = new HashSet<>();
        SourceDidDamageWatcher watcher = (SourceDidDamageWatcher) game.getState().getWatchers().get(SourceDidDamageWatcher.class.getSimpleName());
        if (watcher != null) {
            for (UUID targetId : availablePossibleTargets) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null && watcher.damageSources.contains(targetId)) {
                    possibleTargets.add(targetId);
                }
            }
        }
        return possibleTargets;
    }

    @Override
    public AvengingArrowTarget copy() {
        return new AvengingArrowTarget(this);
    }
}
