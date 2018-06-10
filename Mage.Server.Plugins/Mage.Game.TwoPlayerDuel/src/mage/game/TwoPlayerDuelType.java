

package mage.game;

import mage.game.match.MatchType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TwoPlayerDuelType extends MatchType {

    public TwoPlayerDuelType() {
        this.name = "Two Player Duel";
        this.maxPlayers = 2;
        this.minPlayers = 2;
        this.numTeams = 0;
        this.useAttackOption = false;
        this.useRange = false;
        this.sideboardingAllowed = true;
    }

    protected TwoPlayerDuelType(final TwoPlayerDuelType matchType) {
        super(matchType);
    }

    @Override
    public TwoPlayerDuelType copy() {
        return new TwoPlayerDuelType(this);
    }

}
