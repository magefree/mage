package mage.filter.predicate.other;

import mage.filter.FilterPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.Target;
import mage.util.TargetAddress;

import java.util.UUID;

/**
 * @author TheElk801, Susucr
 */
public class HasOnlySingleTargetPermanentPredicate implements ObjectSourcePlayerPredicate<Spell> {

    private final FilterPermanent filter;

    public HasOnlySingleTargetPermanentPredicate(FilterPermanent filter) {
        this.filter = filter.copy();
    }

    @Override
    public boolean apply(ObjectSourcePlayer<Spell> input, Game game) {
        Spell spell = input.getObject();
        if (spell == null) {
            return false;
        }
        UUID singleTarget = null;
        for (TargetAddress addr : TargetAddress.walk(spell)) {
            Target targetInstance = addr.getTarget(spell);
            for (UUID targetId : targetInstance.getTargets()) {
                if (singleTarget == null) {
                    singleTarget = targetId;
                } else if (!singleTarget.equals(targetId)) {
                    // Ruling on Ivy, Gleeful Spellthief
                    // (2022-09-09) The second ability triggers whenever a player casts a spell that targets
                    // only one creature and no other object or player.
                    return false;
                }
            }
        }
        if (singleTarget == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(singleTarget);
        return filter.match(permanent, input.getPlayerId(), input.getSource(), game);
    }

    @Override
    public String toString() {
        return "that targets only a single " + filter.getMessage();
    }
}