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
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public class StompingSlabs extends CardImpl {

    public StompingSlabs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}");

        // Reveal the top seven cards of your library, then put those cards on the bottom of your library in any order. If a card named Stomping Slabs was revealed this way, Stomping Slabs deals 7 damage to target creature or player.
        this.getSpellAbility().addEffect(new StompingSlabsEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());
    }

    public StompingSlabs(final StompingSlabs card) {
        super(card);
    }

    @Override
    public StompingSlabs copy() {
        return new StompingSlabs(this);
    }
}

class StompingSlabsEffect extends OneShotEffect {
    
    StompingSlabsEffect() {
        super(Outcome.Damage);
        this.staticText = "Reveal the top seven cards of your library, then put those cards on the bottom of your library in any order. If a card named Stomping Slabs was revealed this way, {this} deals 7 damage to target creature or player";
    }
    
    StompingSlabsEffect(final StompingSlabsEffect effect) {
        super(effect);
    }
    
    @Override
    public StompingSlabsEffect copy() {
        return new StompingSlabsEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cards = new CardsImpl();
            cards.addAll(controller.getLibrary().getTopCards(game, 7));
            if (!cards.isEmpty()) {
                controller.revealCards("Stomping Slabs", cards, game);
                boolean stompingSlabsFound = false;
                for (UUID cardId : cards) {
                    Card card = game.getCard(cardId);
                    if (card != null && card.getName().equals("Stomping Slabs")) {
                        stompingSlabsFound = true;
                        break;
                    }
                }
                controller.putCardsOnBottomOfLibrary(cards, game, source, true);
                if (stompingSlabsFound) {
                    Effect effect = new DamageTargetEffect(7);
                    effect.setTargetPointer(new FixedTarget(this.getTargetPointer().getFirst(game, source)));
                    effect.apply(game, source);
                }
            }
            return true;
        }
        return false;
    }
}
