
package mage.game;

import java.util.*;
import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.game.mulligan.CanadianHighlanderMulligan;
import mage.game.mulligan.Mulligan;
import mage.game.turn.TurnMod;
import mage.players.Player;

public abstract class GameCanadianHighlanderImpl extends GameImpl {

    public GameCanadianHighlanderImpl(MultiplayerAttackOption attackOption, RangeOfInfluence range, int freeMulligans, int startLife) {
        super(attackOption, range, new CanadianHighlanderMulligan(freeMulligans), startLife);
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
