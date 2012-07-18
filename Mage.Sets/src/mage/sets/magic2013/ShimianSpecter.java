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
package mage.sets.magic2013;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author jeffwadsworth
 */
public class ShimianSpecter extends CardImpl<ShimianSpecter> {

    public ShimianSpecter(UUID ownerId) {
        super(ownerId, 109, "Shimian Specter", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.expansionSetCode = "M13";
        this.subtype.add("Specter");

        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Whenever Shimian Specter deals combat damage to a player, that player reveals his or her hand. You choose a nonland card from it. Search that player's graveyard, hand, and library for all cards with the same name as that card and exile them. Then that player shuffles his or her library.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new ShimianSpecterEffect(), false, true));
    }

    public ShimianSpecter(final ShimianSpecter card) {
        super(card);
    }

    @Override
    public ShimianSpecter copy() {
        return new ShimianSpecter(this);
    }
}

class ShimianSpecterEffect extends OneShotEffect<ShimianSpecterEffect> {

    private static final FilterCard filter = new FilterCard("nonland card");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
    }

    public ShimianSpecterEffect() {
        super(Constants.Outcome.Benefit);
        staticText = "that player reveals his or her hand. You choose a nonland card from it. Search that player's graveyard, hand, and library for all cards with the same name as that card and exile them. Then that player shuffles his or her library";
    }

    public ShimianSpecterEffect(final ShimianSpecterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player damagedPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (damagedPlayer != null) {
            damagedPlayer.revealCards("Shimian Specter", damagedPlayer.getHand(), game);
            Player you = game.getPlayer(source.getControllerId());
            if (you != null) {
                TargetCard target = new TargetCard(Constants.Zone.PICK, filter);
                target.setRequired(true);
                target.setNotTarget(true);
                if (you.choose(Constants.Outcome.Benefit, damagedPlayer.getHand(), target, game)) {
                    Card chosenCard = damagedPlayer.getHand().get(target.getFirstTarget(), game);
                    if (chosenCard != null) {
                        if (damagedPlayer != null) {
                            
                            //cards in Library
                            Cards cardsInLibrary = new CardsImpl(Constants.Zone.LIBRARY);
                            cardsInLibrary.addAll(damagedPlayer.getLibrary().getCards(game));

                            // cards in Graveyard
                            Cards cardsInGraveyard = new CardsImpl(Constants.Zone.GRAVEYARD);
                            cardsInGraveyard.addAll(damagedPlayer.getGraveyard());
                                
                            // cards in Hand
                            Cards cardsInHand = new CardsImpl(Constants.Zone.HAND);
                            cardsInHand.addAll(damagedPlayer.getHand());
                            
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
                            
                            damagedPlayer.shuffleLibrary(game);
                            
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public ShimianSpecterEffect copy() {
        return new ShimianSpecterEffect(this);
    }

}
