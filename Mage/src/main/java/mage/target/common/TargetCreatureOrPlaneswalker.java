
package mage.target.common;

import mage.abilities.Ability;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public class TargetCreatureOrPlaneswalker extends TargetPermanent {

    public TargetCreatureOrPlaneswalker() {
        this(1);
    }

    public TargetCreatureOrPlaneswalker(int numTargets) {
        this(numTargets, numTargets);
    }

    public TargetCreatureOrPlaneswalker(int minNumTargets, int maxNumTargets) {
        this(minNumTargets, maxNumTargets, new FilterCreatureOrPlaneswalkerPermanent(), false);
    }

    public TargetCreatureOrPlaneswalker(int minNumTargets, int maxNumTargets, FilterCreatureOrPlaneswalkerPermanent filter, boolean notTarget) {
        super(minNumTargets, maxNumTargets, filter, notTarget);
    }

    protected TargetCreatureOrPlaneswalker(final TargetCreatureOrPlaneswalker target) {
        super(target);
    }

    @Override
    public TargetCreatureOrPlaneswalker copy() {
        return new TargetCreatureOrPlaneswalker(this);
    }

    @Override
    public boolean isLegal(Ability source, Game game) {
        for (UUID playerId : targets.keySet()) {
            Player targetPlayer = game.getPlayer(playerId);
            if (targetPlayer != null) {
                // there seems to be no possibility to add more predicates for theplayer so return here true
                return true;
            }
        }
        return super.isLegal(source, game);
    }

}
