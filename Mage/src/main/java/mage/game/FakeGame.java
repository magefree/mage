package mage.game;

import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.game.match.MatchType;
import mage.game.mulligan.MulliganType;

/**
 * Fake game for tests and data check, do nothing.
 *
 * @author JayDi85
 */
public class FakeGame extends GameImpl {

    private int numPlayers;

    public FakeGame() {
        super(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 60, 20, 7);
    }

    public FakeGame(final FakeGame game) {
        super(game);
        this.numPlayers = game.numPlayers;
    }

    @Override
    public MatchType getGameType() {
        return new FakeGameType();
    }

    @Override
    public int getNumPlayers() {
        return numPlayers;
    }

    @Override
    public FakeGame copy() {
        return new FakeGame(this);
    }

}

class FakeGameType extends MatchType {

    public FakeGameType() {
        this.name = "Test Game Type";
        this.maxPlayers = 10;
        this.minPlayers = 3;
        this.numTeams = 0;
        this.useAttackOption = true;
        this.useRange = true;
        this.sideboardingAllowed = true;
    }

    protected FakeGameType(final FakeGameType matchType) {
        super(matchType);
    }

    @Override
    public FakeGameType copy() {
        return new FakeGameType(this);
    }
}