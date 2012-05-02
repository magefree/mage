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

package mage.sets.riseoftheeldrazi;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.LevelUpAbility;
import mage.abilities.keyword.LevelerCardBuilder;
import mage.cards.LevelerCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.turn.TurnMod;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com, noxx
 */
public class LighthouseChronologist extends LevelerCard<LighthouseChronologist> {

    public LighthouseChronologist (UUID ownerId) {
        super(ownerId, 75, "Lighthouse Chronologist", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "ROE";
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        this.addAbility(new LevelUpAbility(new ManaCostsImpl("{U}")));

        Abilities<Ability> abilities1 = new AbilitiesImpl<Ability>();

        Abilities<Ability> abilities2 = new AbilitiesImpl<Ability>();
        abilities2.add(new LighthouseChronologistAbility());

        LevelerCardBuilder.construct(this,
                new LevelerCardBuilder.LevelAbility(4, 6, abilities1, 2, 4),
                new LevelerCardBuilder.LevelAbility(7, -1, abilities2, 3, 5)
        );
    }

    public LighthouseChronologist (final LighthouseChronologist card) {
        super(card);
    }

    @Override
    public LighthouseChronologist copy() {
        return new LighthouseChronologist(this);
    }

}

class LighthouseChronologistAbility extends TriggeredAbilityImpl<LighthouseChronologistAbility> {

    public LighthouseChronologistAbility() {
        super(Constants.Zone.BATTLEFIELD, new LighthouseChronologistEffect(), false);
    }

    public LighthouseChronologistAbility(final LighthouseChronologistAbility ability) {
        super(ability);
    }

    @Override
    public LighthouseChronologistAbility copy() {
        return new LighthouseChronologistAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.END_TURN_STEP_PRE && !game.getActivePlayerId().equals(this.controllerId)) {
			return true;
		}
		return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of each end step, if it's not your turn, take an extra turn after this one.";
    }
}

class LighthouseChronologistEffect extends OneShotEffect<LighthouseChronologistEffect> {

	public LighthouseChronologistEffect() {
		super(Outcome.ExtraTurn);
	}

	public LighthouseChronologistEffect(final LighthouseChronologistEffect effect) {
		super(effect);
	}

	@Override
	public LighthouseChronologistEffect copy() {
		return new LighthouseChronologistEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		game.getState().getTurnMods().add(new TurnMod(source.getControllerId(), false));
		return true;
	}

}