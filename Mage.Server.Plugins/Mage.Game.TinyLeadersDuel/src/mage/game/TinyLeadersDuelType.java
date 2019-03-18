

package mage.game;

import mage.game.match.MatchType;

/**
 *
 * @author JRHerlehy
 */
public class TinyLeadersDuelType extends MatchType {

    public TinyLeadersDuelType() {
        this.name = "Tiny Leaders Two Player Duel";
        this.maxPlayers = 2;
        this.minPlayers = 2;
        this.numTeams = 0;
        this.useAttackOption = false;
        this.useRange = false;
        this.sideboardingAllowed = true;
    }
    
    protected TinyLeadersDuelType(final TinyLeadersDuelType matchType){
        super(matchType);
    }

    @Override
    public TinyLeadersDuelType copy() {
        return new TinyLeadersDuelType(this);
    }
   
}
