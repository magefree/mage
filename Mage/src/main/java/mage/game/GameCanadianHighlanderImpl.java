
package mage.game;

import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.game.mulligan.Mulligan;
import mage.game.turn.TurnMod;

import java.util.UUID;

public abstract class GameCanadianHighlanderImpl extends GameImpl {

    public GameCanadianHighlanderImpl(MultiplayerAttackOption attackOption, RangeOfInfluence range, Mulligan mulligan, int startLife) {
        super(attackOption, range, mulligan, startLife, 100, 7);
    }

    public GameCanadianHighlanderImpl(final GameCanadianHighlanderImpl game) {
        super(game);
    }

    @Override
    protected void init(UUID choosingPlayerId) {
        super.init(choosingPlayerId);
        // 103.7a  In a two-player game, the player who plays first skips the draw step (see rule 504, "Draw Step")
        // of his or her first turn.
        state.getTurnMods().add(new TurnMod(startingPlayerId).withSkipStep(PhaseStep.DRAW));
    }

}
