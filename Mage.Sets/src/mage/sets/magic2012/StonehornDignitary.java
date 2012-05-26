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
package mage.sets.magic2012;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.turn.TurnMod;
import mage.target.Target;
import mage.target.common.TargetOpponent;

/**
 *
 * @author nantuko
 */
public class StonehornDignitary extends CardImpl<StonehornDignitary> {

    public StonehornDignitary(UUID ownerId) {
        super(ownerId, 37, "Stonehorn Dignitary", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.expansionSetCode = "M12";
        this.subtype.add("Rhino");
        this.subtype.add("Soldier");

        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // When Stonehorn Dignitary enters the battlefield, target opponent skips his or her next combat phase.
		Ability ability = new EntersBattlefieldTriggeredAbility(new SkipNextCombatEffect());
		Target target = new TargetOpponent();
		target.setRequired(true);
		ability.addTarget(target);
		this.addAbility(ability);
    }

    public StonehornDignitary(final StonehornDignitary card) {
        super(card);
    }

    @Override
    public StonehornDignitary copy() {
        return new StonehornDignitary(this);
    }
}

class SkipNextCombatEffect extends OneShotEffect<SkipNextCombatEffect> {

	public SkipNextCombatEffect() {
		super(Constants.Outcome.Detriment);
		staticText = "target opponent skips his or her next combat phase";
	}

	public SkipNextCombatEffect(SkipNextCombatEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		UUID targetId = source.getFirstTarget();
		if (targetId != null) {
			game.getState().getTurnMods().add(new TurnMod(targetId, Constants.TurnPhase.COMBAT, null, true));
			return true;
		}
		return false;
	}

	@Override
	public SkipNextCombatEffect copy() {
		return new SkipNextCombatEffect();
	}
}
