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
package mage.sets.tempest;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.SearchEffect;
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
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Plopman
 */
public class Intuition extends CardImpl {

    public Intuition(UUID ownerId) {
        super(ownerId, 70, "Intuition", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{2}{U}");
        this.expansionSetCode = "TMP";


        // Search your library for three cards and reveal them. Target opponent chooses one. Put that card into your hand and the rest into your graveyard. Then shuffle your library.
        this.getSpellAbility().addEffect(new IntuitionEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    public Intuition(final Intuition card) {
        super(card);
    }

    @Override
    public Intuition copy() {
        return new Intuition(this);
    }
}

class IntuitionEffect extends SearchEffect {

    

    public IntuitionEffect() {
        super(new TargetCardInLibrary(3, new FilterCard()), Outcome.Benefit);
        staticText = "Search your library for three cards and reveal them. Target opponent chooses one. Put that card into your hand and the rest into your graveyard. Then shuffle your library";
    }


    public IntuitionEffect(final IntuitionEffect effect) {
        super(effect);
    }

    @Override
    public IntuitionEffect copy() {
        return new IntuitionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (controller == null || opponent == null)
            return false;
        
        if (controller.getLibrary().size() >= 3 && controller.searchLibrary(target, game)) {
            
            if (target.getTargets().size() == 3) {
                Cards cards = new CardsImpl();
                for (UUID cardId: (List<UUID>)target.getTargets()) {
                    Card card = controller.getLibrary().getCard(cardId, game);
                    if (card != null) {
                        cards.add(card);
                    }
                }
                controller.revealCards("Reveal", cards, game);
                
                TargetCard targetCard = new TargetCard(Zone.LIBRARY, new FilterCard());
                
                while(!opponent.choose(Outcome.Neutral, cards, targetCard, game)) {
                    if (!opponent.canRespond()) {
                        return false;
                    }
                }
                Card card = cards.get(targetCard.getFirstTarget(), game);
                if (card != null) {
                    cards.remove(card);
                    controller.moveCards(card, Zone.HAND, source, game);
                }
                controller.moveCards(cards, Zone.GRAVEYARD, source, game);
            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        
        controller.shuffleLibrary(source, game);
        return false;
    }

    public List<UUID> getTargets() {
        return target.getTargets();
    }

}