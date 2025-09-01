package mage.game;

import mage.game.match.MatchImpl;
import mage.game.match.MatchOptions;

/**
 * Fake match for tests and data check, do nothing.
 *
 * @author JayDi85
 */
public class FakeMatch extends MatchImpl {

    public FakeMatch() {
        super(new MatchOptions("fake match", "fake game type", false));
    }

    @Override
    public void startGame() throws GameException {
        throw new IllegalStateException("Can't start fake match");
    }
}
