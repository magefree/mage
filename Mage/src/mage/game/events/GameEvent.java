/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.game.events;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GameEvent {

	private EventType type;
	private UUID targetId;
	private UUID sourceId;
	private UUID playerId;
	private int amount;
	private boolean flag;
	private String data;

	public enum EventType {

		//Game events
		BEGINNING,
		PREVENT_DAMAGE, PREVENTED_DAMAGE,

		//Turn-based events
		CHANGE_PHASE, PHASE_CHANGED,
		CHANGE_STEP, STEP_CHANGED,
		BEGINNING_PHASE, BEGINNING_PHASE_PRE, BEGINNING_PHASE_POST,
		UNTAP_STEP_PRE, UNTAP_STEP, UNTAP_STEP_POST,
		UPKEEP_STEP_PRE, UPKEEP_STEP, UPKEEP_STEP_POST,
		DRAW_STEP_PRE, DRAW_STEP, DRAW_STEP_POST,
		PRECOMBAT_MAIN_PHASE, PRECOMBAT_MAIN_PHASE_PRE, PRECOMBAT_MAIN_PHASE_POST,
		PRECOMBAT_MAIN_STEP_PRE, PRECOMBAT_MAIN_STEP, PRECOMBAT_MAIN_STEP_POST,
		COMBAT_PHASE, COMBAT_PHASE_PRE, COMBAT_PHASE_POST,
		BEGIN_COMBAT_STEP_PRE, BEGIN_COMBAT_STEP, BEGIN_COMBAT_STEP_POST,
		DECLARE_ATTACKERS_STEP_PRE, DECLARE_ATTACKERS_STEP, DECLARE_ATTACKERS_STEP_POST,
		DECLARE_BLOCKERS_STEP_PRE, DECLARE_BLOCKERS_STEP, DECLARE_BLOCKERS_STEP_POST,
		COMBAT_DAMAGE_STEP_PRE, COMBAT_DAMAGE_STEP, COMBAT_DAMAGE_STEP_POST,
		END_COMBAT_STEP_PRE, END_COMBAT_STEP, END_COMBAT_STEP_POST,
		POSTCOMBAT_MAIN_PHASE, POSTCOMBAT_MAIN_PHASE_PRE, POSTCOMBAT_MAIN_PHASE_POST,
		POSTCOMBAT_MAIN_STEP_PRE, POSTCOMBAT_MAIN_STEP, POSTCOMBAT_MAIN_STEP_POST,
		END_PHASE, END_PHASE_PRE, END_PHASE_POST,
		END_TURN_STEP_PRE, END_TURN_STEP, END_TURN_STEP_POST,
		CLEANUP_STEP_PRE, CLEANUP_STEP, CLEANUP_STEP_POST,
		EMPTY_MANA_POOLS,
		AT_END_OF_TURN,

		//player events
		ZONE_CHANGE,
		DRAW_CARD, DREW_CARD,
		DISCARD_CARD, DISCARDED_CARD,
		CYCLE_CARD, CYCLED_CARD,
		DAMAGE_PLAYER, DAMAGED_PLAYER,
		COMBAT_DAMAGE_PLAYER, COMBAT_DAMAGED_PLAYER,
		NONCOMBAT_DAMAGE_PLAYER, NONCOMBAT_DAMAGED_PLAYER,
		PLAYER_LIFE_CHANGE,
		GAIN_LIFE, GAINED_LIFE,
		LOSE_LIFE, LOST_LIFE,
		PLAY_LAND, LAND_PLAYED,
		CAST_SPELL, SPELL_CAST,
		ACTIVATE_ABILITY, ACTIVATED_ABILITY,
		LOSES, LOST, WINS,
		TARGET, TARGETED,
		COUNTER, COUNTERED,
		DECLARING_ATTACKERS, DECLARED_ATTACKERS,
		DECLARE_ATTACKER, ATTACKER_DECLARED,
		DECLARING_BLOCKERS, DECLARED_BLOCKERS,
		DECLARE_BLOCKER, BLOCKER_DECLARED,
		SEARCH_LIBRARY, LIBRARY_SEARCHED,

		//permanent events
		TAP, TAPPED,
		UNTAP, UNTAPPED,
		FLIP, FLIPPED,
		UNFLIP, UNFLIPPED,
		PHASE_OUT, PHASED_OUT,
		PHASE_IN, PHASED_IN,
		TURNFACEUP, TURNEDFACEUP,
		TURNFACEDOWN, TURNEDFACEDOWN,
		DAMAGE_CREATURE, DAMAGED_CREATURE,
		DAMAGE_PLANESWALKER, DAMAGED_PLANESWALKER,
		DESTROY_PERMANENT, DESTROYED_PERMANENT,
		SACRIFICE_PERMANENT, SACRIFICED_PERMANENT,
		ATTACH, ATTACHED,
		UNATTACH, UNATTACHED,
		COUNTER_REMOVED,

		//combat events
		COMBAT_DAMAGE_APPLIED,
		SELECTED_ATTACKER, SELECTED_BLOCKER;

	}

	public GameEvent(EventType type, UUID targetId, UUID sourceId, UUID playerId) {
		this(type, targetId, sourceId, playerId, 0, false);
	}

	public GameEvent(EventType type, UUID targetId, UUID sourceId, UUID playerId, int amount, boolean flag) {
		this.type = type;
		this.targetId = targetId;
		this.sourceId = sourceId;
		this.amount = amount;
		this.playerId = playerId;
		this.flag = flag;
	}

	public static GameEvent getEvent(EventType type, UUID targetId, UUID sourceId, UUID playerId, int amount) {
		return new GameEvent(type, targetId, sourceId, playerId, amount, false);
	}

	public static GameEvent getEvent(EventType type, UUID targetId, UUID sourceId, UUID playerId) {
		return new GameEvent(type, targetId, sourceId, playerId);
	}

	public static GameEvent getEvent(EventType type, UUID targetId, UUID playerId) {
		return new GameEvent(type, targetId, null, playerId);
	}

	public EventType getType() {
		return type;
	}

	public UUID getTargetId() {
		return targetId;
	}

	public UUID getSourceId() {
		return sourceId;
	}

	public UUID getPlayerId() {
		return playerId;
	}

	public int getAmount() {
		return amount;
	}

	public boolean getFlag() {
		return flag;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
