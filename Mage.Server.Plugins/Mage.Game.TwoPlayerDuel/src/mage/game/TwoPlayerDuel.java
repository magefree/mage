
package mage.game;

import java.util.UUID;
import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.game.match.MatchType;
import mage.game.turn.TurnMod;

public class TwoPlayerDuel extends GameImpl {

    public TwoPlayerDuel(MultiplayerAttackOption attackOption, RangeOfInfluence range, int freeMulligans, int startLife) {
        super(attackOption, range, freeMulligans, startLife);
    }

    public TwoPlayerDuel(final TwoPlayerDuel game) {
        super(game);
    }

    @Override
    public MatchType getGameType() {
        return new TwoPlayerDuelType();
    }

    @Override
    public int getNumPlayers() {
        return 2;
    }

    @Override
    protected void init(UUID choosingPlayerId) {
        super.init(choosingPlayerId);
        state.getTurnMods().add(new TurnMod(startingPlayerId, PhaseStep.DRAW));
    }

    @Override
    public TwoPlayerDuel copy() {
        return new TwoPlayerDuel(this);
    }

}
