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
package mage.sets.morningtide;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ClashWinReturnToHandSpellEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class RecrossThePaths extends CardImpl {

    public RecrossThePaths(UUID ownerId) {
        super(ownerId, 133, "Recross the Paths", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2}{G}");
        this.expansionSetCode = "MOR";

        // Reveal cards from the top of your library until you reveal a land card. Put that card onto the battlefield and the rest on the bottom of your library in any order. 
        this.getSpellAbility().addEffect(new RecrossThePathsEffect());
        
        // Clash with an opponent. If you win, return Recross the Paths to its owner's hand.
        this.getSpellAbility().addEffect(ClashWinReturnToHandSpellEffect.getInstance());
    }

    public RecrossThePaths(final RecrossThePaths card) {
        super(card);
    }

    @Override
    public RecrossThePaths copy() {
        return new RecrossThePaths(this);
    }
}

class RecrossThePathsEffect extends OneShotEffect {

    private static final FilterLandCard filter = new FilterLandCard();

    public RecrossThePathsEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "reveal cards from the top of your library until you reveal a land card. Put that card onto the battlefield and the rest on the bottom of your library in any order";
    }

    public RecrossThePathsEffect(final RecrossThePathsEffect effect) {
        super(effect);
    }

    @Override
    public RecrossThePathsEffect copy() {
        return new RecrossThePathsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }
        
        Cards cards = new CardsImpl();
        Card cardFound = null;
        while (controller.getLibrary().size() > 0) {
            Card card = controller.getLibrary().removeFromTop(game);
            if (card != null) {
                cards.add(card);                
                if (filter.match(card, game)){
                    cardFound = card;
                    break;
                }
            }            
        }
        if (!cards.isEmpty()) {
            controller.revealCards(sourceObject.getName(), cards, game);
            if (cardFound != null) {
                controller.putOntoBattlefieldWithInfo(cardFound, game, Zone.LIBRARY, source.getSourceId());
                cards.remove(cardFound);
            }
            controller.putCardsOnBottomOfLibrary(cards, game, source, true);
        }
        return true;
    }
}
