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

package mage.game.match;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import mage.Constants.MultiplayerAttackOption;
import mage.Constants.RangeOfInfluence;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class MatchOptions implements Serializable {

	protected String name;
	protected MultiplayerAttackOption attackOption;
	protected RangeOfInfluence range;
	protected int winsNeeded;
	protected String gameType;
	protected String deckType;
	protected List<String> playerTypes = new ArrayList<String>();

	public MatchOptions(String name, String gameType) {
		this.name = name;
		this.gameType = gameType;
	}

	public String getName() {
		return name;
	}

	public MultiplayerAttackOption getAttackOption() {
		return attackOption;
	}

	public void setAttackOption(MultiplayerAttackOption attackOption) {
		this.attackOption = attackOption;
	}

	public RangeOfInfluence getRange() {
		return range;
	}

	public void setRange(RangeOfInfluence range) {
		this.range = range;
	}

	public int getWinsNeeded() {
		return winsNeeded;
	}

	public void setWinsNeeded(int winsNeeded) {
		this.winsNeeded = winsNeeded;
	}

	public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	public String getDeckType() {
		return deckType;
	}

	public void setDeckType(String deckType) {
		this.deckType = deckType;
	}

	public List<String> getPlayerTypes() {
		return playerTypes;
	}

}
