
package mage.game;

import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.game.mulligan.Mulligan;
import mage.game.turn.TurnMod;

import java.util.UUID;

public abstract class GameCanadianHighlanderImpl extends GameImpl {

    public GameCanadianHighlanderImpl(MultiplayerAttackOption attackOption, RangeOfInfluence range, Mulligan mulligan, int startLife) {
        super(attackOption, range, mulligan, startLife, 100);
    }

    public GameCanadianHighlanderImpl(final GameCanadianHighlanderImpl game) {
        super(game);
    }

    @Override
    protected void init(UUID choosingPlayerId) {
        super.init(choosingPlayerId);
        state.getTurnMods().add(new TurnMod(startingPlayerId, PhaseStep.DRAW));
    }

}
