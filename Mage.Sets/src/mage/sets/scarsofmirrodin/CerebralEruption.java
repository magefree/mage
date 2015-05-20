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
package mage.sets.scarsofmirrodin;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CerebralEruption extends CardImpl {

    public CerebralEruption(UUID ownerId) {
        super(ownerId, 86, "Cerebral Eruption", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{2}{R}{R}");
        this.expansionSetCode = "SOM";

        
        // Target opponent reveals the top card of his or her library. Cerebral Eruption deals damage equal to the revealed card's converted mana cost to that player and each creature he or she controls. If a land card is revealed this way, return Cerebral Eruption to its owner's hand.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new CerebralEruptionEffect());
    }

    public CerebralEruption(final CerebralEruption card) {
        super(card);
    }

    @Override
    public CerebralEruption copy() {
        return new CerebralEruption(this);
    }
}

class CerebralEruptionEffect extends OneShotEffect {

    private static FilterPermanent filter = new FilterCreaturePermanent();

    CerebralEruptionEffect() {
        super(Outcome.Damage);
        staticText = "Target opponent reveals the top card of his or her library. {this} deals damage equal to the revealed card's converted mana cost to that player and each creature he or she controls. If a land card is revealed this way, return {this} to its owner's hand";
    }

    CerebralEruptionEffect(final CerebralEruptionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null && player.getLibrary().size() > 0) {
            Card card = player.getLibrary().getFromTop(game);
            Cards cards = new CardsImpl();
            cards.add(card);
            player.revealCards("Cerebral Eruption", cards, game);
            game.getState().setValue(source.getSourceId().toString(), card);
            int damage = card.getManaCost().convertedManaCost();
            player.damage(damage, source.getSourceId(), game, false, true);
            for (Permanent perm: game.getBattlefield().getAllActivePermanents(filter, player.getId(), game)) {
                perm.damage(damage, source.getSourceId(), game, false, true);
            }
            if (card.getCardType().contains(CardType.LAND)) {
                Card spellCard = game.getStack().getSpell(source.getSourceId()).getCard();
                if (spellCard != null) {
                    player.moveCardToHandWithInfo(spellCard, source.getSourceId(), game, Zone.STACK);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public CerebralEruptionEffect copy() {
        return new CerebralEruptionEffect(this);
    }
}
