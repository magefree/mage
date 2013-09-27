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
package mage.sets.odyssey;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author cbt33, LevelX2 (Hunted Wumpus)
 */
 
public class WordsOfWisdom extends CardImpl<WordsOfWisdom> {

    public WordsOfWisdom(UUID ownerId) {
        super(ownerId, 114, "Words of Wisdom", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{U}");
        this.expansionSetCode = "ODY";

        this.color.setBlue(true);

        // You draw two cards, then each other player draws a card.
        this.getSpellAbility().addEffect(new WordsOfWisdomEffect());
    }

    public WordsOfWisdom(final WordsOfWisdom card) {
        super(card);
    }

    @Override
    public WordsOfWisdom copy() {
        return new WordsOfWisdom(this);
    }
}

class WordsOfWisdomEffect extends OneShotEffect<WordsOfWisdomEffect> {

    public WordsOfWisdomEffect() {
        super(Outcome.Detriment);
        this.staticText = "each other player draws a card";
    }

    public WordsOfWisdomEffect(final WordsOfWisdomEffect effect) {
        super(effect);
    }

    @Override
    public WordsOfWisdomEffect copy() {
        return new WordsOfWisdomEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
                    controller.drawCards(2, game);
            for(UUID playerId: controller.getInRange()) {
                if (!playerId.equals(controller.getId())) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        player.drawCards(1, game);
                        //TargetCardInHand target = new TargetCardInHand(new FilterCreatureCard());
                       // if (target.canChoose(source.getSourceId(), playerId, game)
                         //       && player.chooseUse(Outcome.Neutral, "Put a creature card from your hand in play?", game)
                           //     && player.choose(Outcome.PutCreatureInPlay, target, source.getSourceId(), game)) {
                           // Card card = game.getCard(target.getFirstTarget());
                           // if (card != null) {
                             //   card.putOntoBattlefield(game, Zone.HAND, source.getId(), player.getId());
                           }
                        //}
                    }
                }
            }
            return false;
        }
       
}



