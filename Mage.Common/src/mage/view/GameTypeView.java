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

package mage.view;

import java.io.Serializable;
import mage.game.match.MatchType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GameTypeView implements Serializable {
    private static final long serialVersionUID = 1L;

	private String name;
	private int minPlayers;
	private int maxPlayers;
	private int numTeams;
	private int playersPerTeam;
	private boolean useRange;
	private boolean useAttackOption;

	public GameTypeView(MatchType gameType) {
		this.name = gameType.getName();
		this.minPlayers = gameType.getMinPlayers();
		this.maxPlayers = gameType.getMaxPlayers();
		this.numTeams = gameType.getNumTeams();
		this.playersPerTeam = gameType.getPlayersPerTeam();
		this.useAttackOption = gameType.isUseAttackOption();
		this.useRange = gameType.isUseRange();
	}

	@Override
	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}

	public int getMinPlayers() {
		return minPlayers;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public int getNumTeams() {
		return numTeams;
	}

	public int getPlayersPerTeam() {
		return playersPerTeam;
	}

	public boolean isUseRange() {
		return useRange;
	}

	public boolean isUseAttackOption() {
		return useAttackOption;
	}


}
