/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 * 
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 * 
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 * 
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 * 
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */

package mage.interfaces;

import java.io.Serializable;
import java.util.List;
import mage.utils.MageVersion;
import mage.view.GameTypeView;
import mage.view.TournamentTypeView;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ServerState implements Serializable {

    private List<GameTypeView> gameTypes;
    private List<TournamentTypeView> tournamentTypes;
    private String[] playerTypes;
    private String[] deckTypes;
    private boolean testMode;
    private MageVersion version;

    public ServerState(List<GameTypeView> gameTypes, List<TournamentTypeView> tournamentTypes, String[] playerTypes, String[] deckTypes, boolean testMode, MageVersion version) {
        this.gameTypes = gameTypes;
        this.tournamentTypes = tournamentTypes;
        this.playerTypes = playerTypes;
        this.deckTypes = deckTypes;
        this.testMode = testMode;
        this.version = version;
    }

    public List<GameTypeView> getGameTypes() {
        return gameTypes;
    }

    public List<TournamentTypeView> getTournamentTypes() {
        return tournamentTypes;
    }

    public String[] getPlayerTypes() {
        return playerTypes;
    }

    public String[] getDeckTypes() {
        return deckTypes;
    }

    public boolean isTestMode() {
        return testMode;
    }

    public MageVersion getVersion() {
        return version;
    }
}
