package mage.target.common;

import mage.filter.FilterOpponent;
import mage.filter.predicate.other.PlayerIdPredicate;
import mage.game.Game;
import mage.target.TargetPlayer;

/**
 * @author Arketec
 */
public class TargetOpponentWhoseTurnItIs extends TargetPlayer {

    public TargetOpponentWhoseTurnItIs(Game game) {
        this(game,false);

    }

    public TargetOpponentWhoseTurnItIs(Game game, boolean notTarget) {
        super(1, 1, notTarget, new FilterOpponent());
        super.filter.add(new PlayerIdPredicate(game.getActivePlayerId()));
    }

    private TargetOpponentWhoseTurnItIs(final TargetOpponentWhoseTurnItIs target) {
        super(target);
    }

    @Override
    public TargetOpponentWhoseTurnItIs copy() {
        return new TargetOpponentWhoseTurnItIs(this);
    }
}
