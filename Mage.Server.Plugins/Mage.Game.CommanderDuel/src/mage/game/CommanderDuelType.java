

package mage.game;

import mage.game.match.MatchType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CommanderDuelType extends MatchType {

    public CommanderDuelType() {
        this.name = "Commander Two Player Duel";
        this.maxPlayers = 2;
        this.minPlayers = 2;
        this.numTeams = 0;
        this.useAttackOption = false;
        this.useRange = false;
        this.sideboardingAllowed = false;
    }

    protected CommanderDuelType(final CommanderDuelType matchType) {
        super(matchType);
    }

    @Override
    public CommanderDuelType copy() {
        return new CommanderDuelType(this);
    }

}
