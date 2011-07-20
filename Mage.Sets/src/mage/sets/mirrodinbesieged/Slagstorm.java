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

package mage.sets.mirrodinbesieged;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Loki
 */
public class Slagstorm extends CardImpl<Slagstorm> {

    public Slagstorm (UUID ownerId) {
        super(ownerId, 75, "Slagstorm", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{1}{R}{R}");
        this.expansionSetCode = "MBS";
		this.color.setRed(true);
        this.getSpellAbility().addEffect(new DamageAllEffect(3, FilterCreaturePermanent.getDefault()));
		Mode mode = new Mode();
		mode.getEffects().add(new SlagstormEffect());
		this.getSpellAbility().addMode(mode);
    }

    public Slagstorm (final Slagstorm card) {
        super(card);
    }

    @Override
    public Slagstorm copy() {
        return new Slagstorm(this);
    }

}


class SlagstormEffect extends OneShotEffect<SlagstormEffect> {
    SlagstormEffect() {
        super(Constants.Outcome.Damage);
    }

    SlagstormEffect(final SlagstormEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
		for (UUID playerId: game.getPlayer(source.getControllerId()).getInRange()) {
			Player player = game.getPlayer(playerId);
			if (player != null)
				player.damage(3, source.getId(), game, false, true);
		}
        return true;
    }

    @Override
    public SlagstormEffect copy() {
        return new SlagstormEffect(this);
    }

    @Override
    public String getText(Ability source) {
        return "Slagstorm deals 3 damage to each player";
    }
}