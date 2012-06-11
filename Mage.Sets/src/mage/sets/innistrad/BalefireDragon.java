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
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward
 */
public class BalefireDragon extends CardImpl<BalefireDragon> {

    public BalefireDragon(UUID ownerId) {
        super(ownerId, 129, "Balefire Dragon", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Dragon");

        this.color.setRed(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        this.addAbility(FlyingAbility.getInstance());
        // Whenever Balefire Dragon deals combat damage to a player, it deals that much damage to each creature that player controls.
		this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new BalefireDragonEffect(), false, true));
        
    }

    public BalefireDragon(final BalefireDragon card) {
        super(card);
    }

    @Override
    public BalefireDragon copy() {
        return new BalefireDragon(this);
    }
}

class BalefireDragonEffect extends OneShotEffect<BalefireDragonEffect> {

	public BalefireDragonEffect() {
		super(Outcome.Damage);
		staticText = "it deals that much damage to each creature that player controls";
	}

	public BalefireDragonEffect(final BalefireDragonEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Player player = game.getPlayer(targetPointer.getFirst(game, source));
		if (player != null) {
            int amount = (Integer)getValue("damage");
            if (amount > 0) {
                for (Permanent creature: game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), player.getId(), game)) {
                    creature.damage(amount, source.getSourceId(), game, true, false);
                }
            }
			return true;
		}
		return false;
	}

	@Override
	public BalefireDragonEffect copy() {
		return new BalefireDragonEffect(this);
	}

}