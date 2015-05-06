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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes
 */
public class LearnFromThePast extends CardImpl {

    public LearnFromThePast(UUID ownerId) {
        super(ownerId, 60, "Learn from the Past", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{3}{U}");
        this.expansionSetCode = "DTK";

        // Target player shuffles his or her graveyard into his or her library
        this.getSpellAbility().addEffect(new LearnFromThePastEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        
        // Draw a card
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    public LearnFromThePast(final LearnFromThePast card) {
        super(card);
    }

    @Override
    public LearnFromThePast copy() {
        return new LearnFromThePast(this);
    }
}

class LearnFromThePastEffect extends OneShotEffect {
    
    LearnFromThePastEffect() {
        super(Outcome.Neutral);
        this.staticText = "Target player shuffles his or her graveyard into his or her library";
    }
    
    LearnFromThePastEffect(final LearnFromThePastEffect effect) {
        super(effect);
    }
    
    @Override
    public LearnFromThePastEffect copy() {
        return new LearnFromThePastEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player != null) {
            for (Card card: player.getGraveyard().getCards(game)) {
                player.moveCardToLibraryWithInfo(card, source.getSourceId(), game, Zone.GRAVEYARD, true, true);
            }            
            player.shuffleLibrary(game);
            return true;
        }
        return false;
    }
}