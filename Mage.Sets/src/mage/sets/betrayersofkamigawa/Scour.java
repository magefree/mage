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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public class Scour extends CardImpl<Scour> {
    private static final FilterPermanent filter = new FilterPermanent("enchantment");

    static {
        filter.add(new CardTypePredicate(CardType.ENCHANTMENT));
    }

    public Scour(UUID ownerId) {
        super(ownerId, 20, "Scour", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{2}{W}{W}");
        this.expansionSetCode = "BOK";

        this.color.setWhite(true);

        // Exile target enchantment.
        this.getSpellAbility().addEffect(new ScourExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        
        // Search its controller's graveyard, hand, and library for all cards with the same name as that enchantment and exile them. Then that player shuffles his or her library.


    }

    public Scour(final Scour card) {
        super(card);
    }

    @Override
    public Scour copy() {
        return new Scour(this);
    }
    
    
    
    private class ScourExileTargetEffect extends OneShotEffect<ScourExileTargetEffect> {

        private String exileZone = null;
        private UUID exileId = null;


        public ScourExileTargetEffect() {
            super(Constants.Outcome.Exile);
            this.staticText = "Exile target enchantment. Search its controller's graveyard, hand, and library for all cards with the same name as that enchantment and exile them. Then that player shuffles his or her library";
        }

        public ScourExileTargetEffect(final ScourExileTargetEffect effect) {
            super(effect);
            this.exileZone = effect.exileZone;
            this.exileId = effect.exileId;
        }

        @Override
        public ScourExileTargetEffect copy() {
            return new ScourExileTargetEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            boolean result;
            Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));

            if (exileId == null) {
                exileId = getId();
            }

            if (permanent != null) {
                Player targetPlayer = game.getPlayer(permanent.getOwnerId());
                Player you = game.getPlayer(source.getControllerId());

                Card chosenCard = permanent;
                result = permanent.moveToExile(exileId, exileZone, source.getSourceId(), game);

                if (targetPlayer != null && you != null) {
                    //cards in Library
                    Cards cardsInLibrary = new CardsImpl(Constants.Zone.LIBRARY);
                    cardsInLibrary.addAll(targetPlayer.getLibrary().getCards(game));
                    you.lookAtCards("Scour search of Library", cardsInLibrary, game);

                    // cards in Graveyard
                    Cards cardsInGraveyard = new CardsImpl(Constants.Zone.GRAVEYARD);
                    cardsInGraveyard.addAll(targetPlayer.getGraveyard());

                    // cards in Hand
                    Cards cardsInHand = new CardsImpl(Constants.Zone.HAND);
                    cardsInHand.addAll(targetPlayer.getHand());
                    you.lookAtCards("Scour search of Hand", cardsInHand, game);

                    // exile same named cards from zones
                    for (Card checkCard : cardsInLibrary.getCards(game)) {
                        if (checkCard.getName().equals(chosenCard.getName())) {
                            checkCard.moveToExile(id, "Library", id, game);
                        }
                    }
                    for (Card checkCard : cardsInGraveyard.getCards(game)) {
                        if (checkCard.getName().equals(chosenCard.getName())) {
                            checkCard.moveToExile(id, "Graveyard", id, game);
                        }
                    }
                    for (Card checkCard : cardsInHand.getCards(game)) {
                        if (checkCard.getName().equals(chosenCard.getName())) {
                            checkCard.moveToExile(id, "Hand", id, game);
                        }
                    }

                    targetPlayer.shuffleLibrary(game);            
                }
                return result;

            } 
            return false;
        }
    }

}
