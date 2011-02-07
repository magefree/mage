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

package mage;

import java.util.ArrayList;
import java.util.List;

public final class Constants {
	
	public enum ColoredManaSymbol {
		W("W"), U("U"), B("B"), R("R"), G("G");

		private String text;

		ColoredManaSymbol(String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return text;
		}

		public static ColoredManaSymbol lookup(char c) {
			switch (c) {
				case 'W':
					return W;
				case 'R':
					return R;
				case 'G':
					return G;
				case 'B':
					return B;
				case 'U':
					return U;
			}
			return null;
		}

	}

	public enum CardType {
		ARTIFACT ("Artifact"),
		CREATURE ("Creature"),
		ENCHANTMENT ("Enchantment"),
		INSTANT ("Instant"),
		LAND ("Land"),
		PLANESWALKER ("Planeswalker"),
		SORCERY ("Sorcery"),
		TRIBAL ("Tribal");

		private String text;

		CardType(String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return text;
		}
		
	}

	public enum Rarity {

		NA ("na", "na", "N", 0),
		LAND ("Land", "common", "C", 1),
		COMMON ("Common", "common", "C", 1),
		UNCOMMON ("Uncommon", "uncommon", "U", 2),
		RARE ("Rare", "rare", "R", 3),
		MYTHIC ("Mythic", "mythic", "M", 3);

		private String text;
		private String symbolCode;
		private String code;
		private int rating;

		Rarity(String text, String symbolCode, String code, int rating) {
			this.text = text;
			this.symbolCode = symbolCode;
			this.code = code;
			this.rating = rating;
		}

		@Override
		public String toString() {
			return text;
		}

		public String getSymbolCode() {
			return symbolCode;
		}

		public String getCode() {
			return code;
		}

		public int getRating() {
			return rating;
		}
	}

	public enum AbilityType {
		PLAY_LAND("Play land"),
		MANA("Mana"),
		SPELL("Spell"),
		ACTIVATED("Activated"),
		STATIC("Static"),
		TRIGGERED("Triggered"),
		EVASION("Evasion"),
		LOYALTY("Loyalty"),
		SPECIAL_ACTION("Special Action");

		private String text;

		AbilityType(String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return text;
		}
	}

	public enum EffectType {

		ONESHOT("One Shot Effect"),
		CONTINUOUS("Continuous Effect"),
		REPLACEMENT("Replacement Effect"),
		PREVENTION("Prevention Effect"),
		REDIRECTION("Redirection Effect"),
		ASTHOUGH("As Though Effect"), 
		RESTRICTION("Restriction Effect"),
		REQUIREMENT("Requirement Effect"),
		COSTMODIFICATION("Cost Modification Effect");

		private String text;

		EffectType(String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return text;
		}
	}

	public enum AsThoughEffectType {
		BLOCK,
		BE_BLOCKED,
		ATTACK,
		CAST,
		TARGET,
		PAY,
		DAMAGE
	}

	public enum Duration {
		OneUse(""),
		EndOfGame("for the rest of the game"),
		WhileOnBattlefield(""),
		WhileOnStack(""),
		EndOfTurn("until end of turn"),
		EndOfCombat("until end of combat"),
		Custom("");

		private String text;

		Duration(String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return text;
		}

	}

	public enum Layer {
		CopyEffects_1,
		ControlChangingEffects_2,
		TextChangingEffects_3,
		TypeChangingEffects_4,
		ColorChangingEffects_5,
		AbilityAddingRemovingEffects_6,
		PTChangingEffects_7,
		PlayerEffects,
		RulesEffects
	}

	public enum SubLayer {
		CharacteristicDefining_7a,
		SetPT_7b,
		ModifyPT_7c,
		Counters_7d,
		SwitchPT_e,
		NA
	}

	public enum TableState {
		WAITING ("Waiting for players"),
		STARTING ("Waiting to start"),
		DRAFTING ("Drafting"),
		DUELING ("Dueling"),
		SIDEBOARDING ("Sideboarding"),
		CONSTRUCTING ("Constructing"),
		FINISHED ("Finished");

		private String text;

		TableState(String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return text;
		}

	}

	public enum TurnPhase {
		BEGINNING ("Beginning"),
		PRECOMBAT_MAIN ("Precombat Main"),
		COMBAT ("Combat"),
		POSTCOMBAT_MAIN ("Postcombat Main"),
		END ("End");

		private String text;

		TurnPhase(String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return text;
		}

	}

	public enum PhaseStep {
		UNTAP ("Untap"),
		UPKEEP ("Upkeep"),
		DRAW ("Draw"),
		PRECOMBAT_MAIN ("Precombat Main"),
		BEGIN_COMBAT ("Begin Combat"),
		DECLARE_ATTACKERS ("Declare Attackers"),
		DECLARE_BLOCKERS ("Declare Blockers"),
		FIRST_COMBAT_DAMAGE ("First Combat Damage"),
		COMBAT_DAMAGE ("Combat Damage"),
		END_COMBAT ("End Combat"),
		POSTCOMBAT_MAIN ("Postcombat Main"),
		END_TURN ("End Turn"),
		CLEANUP ("Cleanup");

		private String text;

		PhaseStep(String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return text;
		}

	}

	public enum Outcome {
		Damage(false),
		DestroyPermanent(false),
		BoostCreature(true),
		UnboostCreature(false),
		AddAbility(true),
		GainLife(true),
		ExtraTurn(true),
		BecomeCreature(true),
		PutCreatureInPlay(true),
		PutCardInPlay(true),
		PutLandInPlay(true),
		GainControl(true),
		DrawCard(true),
		Discard(false),
		Sacrifice(false),
		ReturnToHand(false),
		Exile(false),
		Protect(true),
		PutManaInPool(true),
		Regenerate(true),
		PreventDamage(true),
		RedirectDamage(true),
		Tap(false),
		Untap(true),
		Win(true),
		Copy(true),
		Benefit(true),
		Detriment(false),
		Neutral(true);

		private boolean good;

		Outcome(boolean good) {
			this.good = good;
		}

		public boolean isGood() {
			return good;
		}

	}

	public enum Zone {
		HAND, GRAVEYARD, LIBRARY, BATTLEFIELD, STACK, EXILED, ALL, OUTSIDE, PICK;
		
		public boolean match(Zone zone) {
			if (this == zone || this == ALL || zone == ALL)
				return true;
			return false;
		}
	}

	public enum TimingRule {
		INSTANT, SORCERY
	}

	public enum TargetController {
		ANY, YOU, NOT_YOU, OPPONENT
	}

	public enum RangeOfInfluence {
		ONE(1),
		TWO(2),
		ALL(0);

		private int range;

		RangeOfInfluence(int range) {
			this.range = range;
		}

		public int getRange() {
			return range;
		}
	}

	public enum MultiplayerAttackOption {
		MULITPLE("Attack Multiple Players"),
		LEFT("Attack Left"),
		RIGHT("Attack Right");

		private String text;

		MultiplayerAttackOption(String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return text;
		}
	}

	public enum SetType {
		CORE("Core"),
		EXPANSION("Expansion"),
		REPRINT("Reprint");

		private String text;

		SetType(String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return text;
		}
	}

	public static final List<String> PlaneswalkerTypes = new ArrayList<String>()
		{{add("Ajani"); add("Bolas"); add("Chandra"); add("Elspeth");add("Garruk"); add("Jace"); add("Liliana"); add("Nissa"); add("Sarkhan"); add("Sorin"); add("Tezzeret");}};
	
	private Constants() {
		throw new AssertionError();
	}
	
}
