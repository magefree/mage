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
package mage.sets.weatherlight;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ShuffleLibrarySourceEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
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
public class AncestralKnowledge extends CardImpl {

    public AncestralKnowledge(UUID ownerId) {
        super(ownerId, 32, "Ancestral Knowledge", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");
        this.expansionSetCode = "WTH";


        // Cumulative upkeep {1}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{1}")));
        
        // When Ancestral Knowledge enters the battlefield, look at the top ten cards of your library, then exile any number of them and put the rest back on top of your library in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AncestralKnowledgeEffect()));
        
        // When Ancestral Knowledge leaves the battlefield, shuffle your library.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ShuffleLibrarySourceEffect(), false));
    }

    public AncestralKnowledge(final AncestralKnowledge card) {
        super(card);
    }

    @Override
    public AncestralKnowledge copy() {
        return new AncestralKnowledge(this);
    }
}

class AncestralKnowledgeEffect extends OneShotEffect {
    
    AncestralKnowledgeEffect() {
        super(Outcome.Benefit);
        this.staticText = "look at the top ten cards of your library, then exile any number of them and put the rest back on top of your library in any order";
    }
    
    AncestralKnowledgeEffect(final AncestralKnowledgeEffect effect) {
        super(effect);
    }
    
    @Override
    public AncestralKnowledgeEffect copy() {
        return new AncestralKnowledgeEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int numCards = Math.min(10, player.getLibrary().size());
            if (numCards > 0) {
                Cards cards = new CardsImpl();
                for (int i = 0; i < numCards; i++) {
                    cards.add(player.getLibrary().removeFromTop(game));
                }
                TargetCard target = new TargetCard(0, numCards, Zone.LIBRARY, new FilterCard("cards to exile"));
                player.choose(Outcome.Exile, cards, target, game);
                for (UUID cardId : target.getTargets()) {
                    Card card = cards.get(cardId, game);
                    if (card != null) {
                        player.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, Zone.LIBRARY, true);
                        cards.remove(card);
                    }
                }
                while (cards.size() > 1) {
                    target = new TargetCard(1, Zone.LIBRARY, new FilterCard("card to put on top of library (last put is first drawn)"));
                    player.choose(Outcome.Benefit, cards, target, game);
                    Card card = cards.get(target.getFirstTarget(), game);
                    player.moveCardToLibraryWithInfo(card, source.getSourceId(), game, Zone.LIBRARY, true, false);
                    cards.remove(card);
                }
                if (cards.size() == 1) {
                    Card card = cards.get(cards.iterator().next(), game);
                    if (card != null) {
                        player.moveCardToLibraryWithInfo(card, source.getSourceId(), game, Zone.LIBRARY, true, false);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
