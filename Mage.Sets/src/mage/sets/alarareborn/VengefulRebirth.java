/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.sets.alarareborn;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class VengefulRebirth extends CardImpl {

    public VengefulRebirth(UUID ownerId) {
        super(ownerId, 62, "Vengeful Rebirth", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{4}{R}{G}");
        this.expansionSetCode = "ARB";

        // Return target card from your graveyard to your hand. If you return a nonland card to your hand this way, {this} deals damage equal to that card's converted mana cost to target creature or player
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());
        this.getSpellAbility().addEffect(new VengefulRebirthEffect());
        
        // Exile Vengeful Rebirth.
        this.getSpellAbility().addEffect(ExileSpellEffect.getInstance());
    }

    public VengefulRebirth(final VengefulRebirth card) {
        super(card);
    }

    @Override
    public VengefulRebirth copy() {
        return new VengefulRebirth(this);
    }
}

class VengefulRebirthEffect extends OneShotEffect {

    public VengefulRebirthEffect() {
        super(Outcome.DrawCard);
        staticText = "Return target card from your graveyard to your hand. If you return a nonland card to your hand this way, {this} deals damage equal to that card's converted mana cost to target creature or player";
    }

    public VengefulRebirthEffect(final VengefulRebirthEffect effect) {
        super(effect);
    }

    @Override
    public VengefulRebirthEffect copy() {
        return new VengefulRebirthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = (Card)game.getObject(source.getFirstTarget());
        if (controller != null && card != null && controller.removeFromGraveyard(card, game)) {
            controller.moveCards(card, Zone.GRAVEYARD, Zone.HAND, source, game);
            if (!card.getCardType().contains(CardType.LAND)) {
                int damage = card.getManaCost().convertedManaCost();                
                Permanent permanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
                if (permanent != null) {
                    permanent.damage(damage, source.getSourceId(), game, false, true);
                }
                Player targetPlayer = game.getPlayer(source.getTargets().get(1).getFirstTarget());
                if (targetPlayer != null) {
                    targetPlayer.damage(damage, source.getSourceId(), game, false, true);
                }
            }
            return true;
        }
        return false;
    }

}
