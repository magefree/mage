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
package mage.sets.urzassaga;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Backfir3
 */
public class Bulwark extends CardImpl<Bulwark> {

    public Bulwark(UUID ownerId) {
        super(ownerId, 178, "Bulwark", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{R}");
        this.expansionSetCode = "USG";
        this.color.setRed(true);
		
        // At the beginning of your upkeep, Bulwark deals X damage to target opponent, where X is
		// the number of cards in your hand minus the number of cards in that player's hand.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new BulwarkDamageEffect(), Constants.TargetController.YOU, false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public Bulwark(final Bulwark card) {
        super(card);
    }

    @Override
    public Bulwark copy() {
        return new Bulwark(this);
    }
}

class BulwarkDamageEffect extends OneShotEffect<BulwarkDamageEffect> {

    public BulwarkDamageEffect() {
        super(Outcome.Damage);
		staticText = "Bulwark deals X damage to target opponent, where X is the number of cards in your hand minus the number of cards in that player's hand";
    }

    public BulwarkDamageEffect(final BulwarkDamageEffect effect) {
        super(effect);
    }

    @Override
    public BulwarkDamageEffect copy() {
        return new BulwarkDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
		Player controller = game.getPlayer(source.getControllerId());
        if (opponent != null && controller != null) {
			int amount = controller.getHand().size() - opponent.getHand().size();
			if(amount > 0) {
				opponent.damage(amount, source.getSourceId(), game, false, true);
				return true;
			}
        }
        return false;
    }
}
