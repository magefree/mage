
package mage.target.common;

import java.util.UUID;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */
public class FilterCreatureAttackingYou extends FilterAttackingCreature {

    private final boolean orWalker;

    public FilterCreatureAttackingYou() {
        this(false);
    }

    public FilterCreatureAttackingYou(boolean orWalker) {
        this("creature that's attacking you" + (orWalker ? "or a planeswalker you control" : ""), orWalker);
    }

    public FilterCreatureAttackingYou(String name) {
        this(name, false);
    }

    public FilterCreatureAttackingYou(String name, boolean orWalker) {
        super(name);
        this.orWalker = orWalker;
    }

    public FilterCreatureAttackingYou(final FilterCreatureAttackingYou filter) {
        super(filter);
        this.orWalker = filter.orWalker;
    }

    @Override
    public FilterCreatureAttackingYou copy() {
        return new FilterCreatureAttackingYou(this);
    }

    @Override
    public boolean match(Permanent permanent, UUID sourceId, UUID playerId, Game game) {
        if (orWalker) {
            return super.match(permanent, sourceId, playerId, game)
                    && permanent.isAttacking() // to prevent unneccessary combat checking if not attacking
                    && playerId.equals(game.getCombat().getDefendingPlayerId(permanent.getId(), game));
        } else {
            return super.match(permanent, sourceId, playerId, game)
                    && permanent.isAttacking() // to prevent unneccessary combat checking if not attacking
                    && playerId.equals(game.getCombat().getDefenderId(permanent.getId()));
        }
    }
}
