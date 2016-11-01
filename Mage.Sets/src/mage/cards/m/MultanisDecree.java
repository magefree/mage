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
package mage.cards.m;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Backfir3
 */
public class MultanisDecree extends CardImpl {

    public MultanisDecree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}");


        // Destroy all enchantments. You gain 2 life for each enchantment destroyed this way.
        this.getSpellAbility().addEffect(new MultanisDecreeDestroyEffect());
    }

    public MultanisDecree(final MultanisDecree card) {
        super(card);
    }

    @Override
    public MultanisDecree copy() {
        return new MultanisDecree(this);
    }
}

class MultanisDecreeDestroyEffect extends OneShotEffect {
    public MultanisDecreeDestroyEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy all enchantments. You gain 2 life for each enchantment destroyed this way";
    }

    public MultanisDecreeDestroyEffect(final MultanisDecreeDestroyEffect effect) {
        super(effect);
    }

    @Override
    public MultanisDecreeDestroyEffect copy() {
        return new MultanisDecreeDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
		int enchantmentsDestoyed = 0;
        for (Permanent permanent: game.getState().getBattlefield().getActivePermanents(new FilterEnchantmentPermanent(), source.getControllerId(), source.getSourceId(), game)) {
            if (permanent.destroy(source.getSourceId(), game, false)) {
                enchantmentsDestoyed++;
            }
        }
		if(enchantmentsDestoyed > 0) {
			controller.gainLife(enchantmentsDestoyed * 2, game);
		}
        return false;
    }
}
