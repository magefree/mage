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
package mage.sets.khansoftarkir;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author emerald000
 */
public class BitterRevelation extends CardImpl {

    public BitterRevelation(UUID ownerId) {
        super(ownerId, 65, "Bitter Revelation", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{3}{B}");
        this.expansionSetCode = "KTK";


        // Look at the top four cards of your library. Put two of them into your hand and the rest into your graveyard. You lose 2 life.
        this.getSpellAbility().addEffect(new BitterRevelationEffect());
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(2));
    }

    public BitterRevelation(final BitterRevelation card) {
        super(card);
    }

    @Override
    public BitterRevelation copy() {
        return new BitterRevelation(this);
    }
}

class BitterRevelationEffect extends OneShotEffect {
    
    BitterRevelationEffect() {
        super(Outcome.Benefit);
        this.staticText = "Look at the top four cards of your library. Put two of them into your hand and the rest into your graveyard";
    }
    
    BitterRevelationEffect(final BitterRevelationEffect effect) {
        super(effect);
    }
    
    @Override
    public BitterRevelationEffect copy() {
        return new BitterRevelationEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Cards cards = new CardsImpl(Zone.LIBRARY);
            int cardsCount = Math.min(4, player.getLibrary().size());
            for (int i = 0; i < cardsCount; i++) {
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) {
                    cards.add(card);
                }
            }
            if (cards.size() > 0) {
                player.lookAtCards("Bitter Revelation", cards, game);
                TargetCard target = new TargetCard(Math.min(2, cards.size()), Zone.PICK, new FilterCard("two cards to put in your hand"));
                if (player.choose(Outcome.DrawCard, cards, target, game)) {
                    for (UUID targetId : target.getTargets()) {
                        Card card = cards.get(targetId, game);
                        if (card != null) {
                            player.putInHand(card, game);
                            cards.remove(card);
                        }   
                    }
                }
                for (Card card : cards.getCards(game)) {
                    player.moveCardToGraveyardWithInfo(card, source.getSourceId(), game, Zone.LIBRARY);
                }
            }
            return true;
        }
        return false;
    }
}
