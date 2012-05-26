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

package mage.sets.mirrodinbesieged;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.BattleCryAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class HeroOfOxidRidge extends CardImpl<HeroOfOxidRidge> {

	public HeroOfOxidRidge(UUID ownerId) {
		super(ownerId, 66, "Hero of Oxid Ridge", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
		this.expansionSetCode = "MBS";
		this.subtype.add("Human");
		this.subtype.add("Knight");
		this.color.setRed(true);
		this.power = new MageInt(4);
		this.toughness = new MageInt(2);

		this.addAbility(HasteAbility.getInstance());
		this.addAbility(new BattleCryAbility());
        this.addAbility(new AttacksTriggeredAbility(new HeroOfOxidRidgeEffect(), false));
	}

	public HeroOfOxidRidge(final HeroOfOxidRidge card) {
		super(card);
	}

	@Override
	public HeroOfOxidRidge copy() {
		return new HeroOfOxidRidge(this);
	}

}

class HeroOfOxidRidgeEffect extends RestrictionEffect<HeroOfOxidRidgeEffect> {

    public HeroOfOxidRidgeEffect() {
        super(Duration.EndOfTurn);
        staticText = "creatures with power 1 or less can't block this turn";
    }
    
    public HeroOfOxidRidgeEffect(final HeroOfOxidRidgeEffect effect) {
        super(effect);
    }
    
    @Override
    public HeroOfOxidRidgeEffect copy() {
        return new HeroOfOxidRidgeEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getPower().getValue() <= 1)
            return true;
        return false;
    }

    @Override
	public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
		return false;
	}

}
