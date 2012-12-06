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
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.SearchEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Plopman
 */
public class Intuition extends CardImpl<Intuition> {

    public Intuition(UUID ownerId) {
        super(ownerId, 70, "Intuition", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{2}{U}");
        this.expansionSetCode = "TMP";

        this.color.setBlue(true);

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

class IntuitionEffect extends SearchEffect<IntuitionEffect> {

    

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
        Player player = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (player == null || opponent == null)
            return false;
        
        if (player.getLibrary().size() >= 3 && player.searchLibrary(target, game)) {
            
            target.setRequired(true);
            if (target.getTargets().size() == 3) {
                Cards cards = new CardsImpl();
                for (UUID cardId: (List<UUID>)target.getTargets()) {
                    Card card = player.getLibrary().getCard(cardId, game);
                    if (card != null) {
                        cards.add(card);
                    }
                }
                player.revealCards("Reveal", cards, game);
                
                TargetCard targetCard = new TargetCard(Constants.Zone.PICK, new FilterCard());
                targetCard.setRequired(true);
                
                while(!opponent.choose(Outcome.Neutral, cards, targetCard, game));
                Card card = cards.get(targetCard.getFirstTarget(), game);
                if (card != null) {
                    cards.remove(card);
                    card.moveToZone(Constants.Zone.HAND, source.getId(), game, false);
                }
                
                for(UUID uuid : cards){
                    card = cards.get(uuid, game);
                    card.moveToZone(Constants.Zone.GRAVEYARD, source.getId(), game, false);
                }
                
            }
            player.shuffleLibrary(game);
            return true;
        }
        
        player.shuffleLibrary(game);
        return false;
    }

    public List<UUID> getTargets() {
        return target.getTargets();
    }

}