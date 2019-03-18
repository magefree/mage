

package mage.game;

import mage.game.match.MatchType;


/**
 *
 * @author LevelX2
 */
public class CommanderFreeForAllType extends MatchType {

    public CommanderFreeForAllType() {
        this.name = "Commander Free For All";
        this.maxPlayers = 10;
        this.minPlayers = 3;
        this.numTeams = 0;
        this.useAttackOption = true;
        this.useRange = true;
        this.sideboardingAllowed = false;
    }

    protected CommanderFreeForAllType(final CommanderFreeForAllType matchType) {
        super(matchType);
    }

    @Override
    public CommanderFreeForAllType copy() {
        return new CommanderFreeForAllType(this);
    }
}
