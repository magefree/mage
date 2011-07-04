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

package mage.sets.championsofkamigawa;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ReturnToHandSpellEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author Loki
 */
public class HanabiBlast extends CardImpl<HanabiBlast> {

    public HanabiBlast (UUID ownerId) {
        super(ownerId, 170, "Hanabi Blast", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{1}{R}{R}");
        this.expansionSetCode = "CHK";
		this.color.setRed(true);
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());
        this.getSpellAbility().addEffect(ReturnToHandSpellEffect.getInstance());
        this.getSpellAbility().addEffect(new HanabiBlastDiscardEffect());
    }

    public HanabiBlast (final HanabiBlast card) {
        super(card);
    }

    @Override
    public HanabiBlast copy() {
        return new HanabiBlast(this);
    }

}

class HanabiBlastDiscardEffect extends OneShotEffect<HanabiBlastDiscardEffect> {

	private static final String effectText = "discard a card at random";

	HanabiBlastDiscardEffect () {
		super(Constants.Outcome.Discard);
	}

	HanabiBlastDiscardEffect(HanabiBlastDiscardEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Player player = game.getPlayer(source.getControllerId());
        if(player != null) {
            player.discard(player.getHand().getRandom(game), source, game);
            return true;
        }
		return false;
	}

	@Override
	public HanabiBlastDiscardEffect copy() {
		return new HanabiBlastDiscardEffect(this);
	}

	@Override
	public String getText(Ability source) {
		return effectText;
	}
}

