

package mage.game;

import mage.game.match.MatchType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class FreeForAllType extends MatchType {

    public FreeForAllType() {
        this.name = "Free For All";
        this.maxPlayers = 10;
        this.minPlayers = 3;
        this.numTeams = 0;
        this.useAttackOption = true;
        this.useRange = true;
        this.sideboardingAllowed = true;
    }

    protected FreeForAllType(final FreeForAllType matchType) {
        super(matchType);
    }

    @Override
    public FreeForAllType copy() {
        return new FreeForAllType(this);
    }
}
