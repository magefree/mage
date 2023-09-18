package mage.game;

import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.game.match.MatchType;
import mage.game.mulligan.Mulligan;
import mage.game.turn.TurnMod;

import java.util.UUID;

public class TwoPlayerDuel extends GameImpl {

    public TwoPlayerDuel(MultiplayerAttackOption attackOption, RangeOfInfluence range, Mulligan mulligan, int startLife) {
        this(attackOption, range, mulligan, startLife, 60);
    }

    public TwoPlayerDuel(MultiplayerAttackOption attackOption, RangeOfInfluence range, Mulligan mulligan, int startingLife, int minimumDeckSize) {
        this(attackOption, range, mulligan, startingLife, minimumDeckSize, 7);
    }

    public TwoPlayerDuel(MultiplayerAttackOption attackOption, RangeOfInfluence range, Mulligan mulligan, int startingLife, int minimumDeckSize, int startingHandSize) {
        super(attackOption, range, mulligan, startingLife, minimumDeckSize, startingHandSize);
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
        state.getTurnMods().add(new TurnMod(startingPlayerId).withSkipStep(PhaseStep.DRAW));
    }

    @Override
    public TwoPlayerDuel copy() {
        return new TwoPlayerDuel(this);
    }

}
