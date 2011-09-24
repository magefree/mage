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

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterEnchantment;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author nantuko
 */
public class Paraselene extends CardImpl<Paraselene> {

    public Paraselene(UUID ownerId) {
        super(ownerId, 26, "Paraselene", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2}{W}");
        this.expansionSetCode = "ISD";

        this.color.setWhite(true);

        // Destroy all enchantments. You gain 1 life for each enchantment destroyed this way.
        this.getSpellAbility().addEffect(new ParaseleneEffect());
    }

    public Paraselene(final Paraselene card) {
        super(card);
    }

    @Override
    public Paraselene copy() {
        return new Paraselene(this);
    }
}

class ParaseleneEffect extends OneShotEffect<ParaseleneEffect> {

    public ParaseleneEffect() {
        super(Constants.Outcome.DestroyPermanent);
        staticText = "Destroy all enchantments. You gain 1 life for each enchantment destroyed this way";
    }

    public ParaseleneEffect(ParaseleneEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int count = 0;
        for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterEnchantment(), source.getControllerId(), source.getSourceId(), game)) {
            if (permanent.destroy(source.getId(), game, false)) {
                count++;
            }
        }
        if (count > 0) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                player.gainLife(count, game);
            }
        }
        return true;
    }

    @Override
    public ParaseleneEffect copy() {
        return new ParaseleneEffect(this);
    }
}
