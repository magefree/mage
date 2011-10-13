/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.abilities;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Modes extends HashMap<UUID, Mode> {
	
	private UUID modeId;
	
	public Modes() {
		Mode mode = new Mode();
		this.put(mode.getId(), mode);
		this.modeId = mode.getId();
	}
	
	public Modes(Modes modes) {
		this.modeId = modes.modeId;
        for (Map.Entry<UUID, Mode> entry: modes.entrySet()) {
            this.put(entry.getKey(), entry.getValue().copy());
		}
	}
	
	public Modes copy() {
		return new Modes(this);
	}
	
	public Mode getMode() {
		return get(modeId);
	}
	
	public void setMode(Mode mode) {
		if (this.containsKey(mode.getId()))
			this.modeId = mode.getId();
	}
	
	public void addMode(Mode mode) {
		this.put(mode.getId(), mode);
	}
	
	public boolean choose(Game game, Ability source) {
		if (this.size() > 1) {
			Player player = game.getPlayer(source.getControllerId());
			Mode choice = player.chooseMode(this, source, game);
			if (choice == null)
				return false;
			setMode(choice);
			return true;
		}
		this.modeId = this.values().iterator().next().getId();
		return true;
	}
	
	public String getText() {
		StringBuilder sb = new StringBuilder();
		if (this.size() > 1)
			sb.append("Choose one - ");
		for (Mode mode: this.values()) {
			sb.append(mode.getEffects().getText(mode)).append("; or ");
		}
		sb.delete(sb.length() - 5, sb.length());
		return sb.toString();
	}

	public String getText(String sourceName) {
		StringBuilder sb = new StringBuilder();
		if (this.size() > 1)
			sb.append("Choose one - ");
		for (Mode mode: this.values()) {
			sb.append(mode.getEffects().getText(mode)).append("; or ");
		}
		sb.delete(sb.length() - 5, sb.length());
		String text = sb.toString();
		text = text.replace("{this}", sourceName);
		text = text.replace("{source}", sourceName);
		return text;
	}

}
