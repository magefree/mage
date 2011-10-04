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
package mage.sets.innistrad;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;

/**
 *
 * @author BetaSteward
 */
public class CurseOfTheNightlyHunt extends CardImpl<CurseOfTheNightlyHunt> {

    public CurseOfTheNightlyHunt(UUID ownerId) {
        super(ownerId, 137, "Curse of the Nightly Hunt", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Aura");
        this.subtype.add("Curse");

        this.color.setRed(true);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Creatures enchanted player controls attack each turn if able.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CurseOfTheNightlyHuntEffect()));
        
    }

    public CurseOfTheNightlyHunt(final CurseOfTheNightlyHunt card) {
        super(card);
    }

    @Override
    public CurseOfTheNightlyHunt copy() {
        return new CurseOfTheNightlyHunt(this);
    }
}

class CurseOfTheNightlyHuntEffect extends RequirementEffect<CurseOfTheNightlyHuntEffect> {

	public CurseOfTheNightlyHuntEffect() {
		super(Duration.WhileOnBattlefield);
        staticText = "Creatures enchanted player controls attack each turn if able";
	}

	public CurseOfTheNightlyHuntEffect(final CurseOfTheNightlyHuntEffect effect) {
		super(effect);
	}

	@Override
	public CurseOfTheNightlyHuntEffect copy() {
		return new CurseOfTheNightlyHuntEffect(this);
	}

	@Override
	public boolean applies(Permanent permanent, Ability source, Game game) {
		if (source.getControllerId().equals(permanent.getControllerId())) {
			return true;
		}
		return false;
	}

	@Override
	public boolean mustAttack(Game game) {
		return true;
	}

	@Override
	public boolean mustBlock(Game game) {
		return false;
	}

}