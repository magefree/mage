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
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public class Lobotomy extends CardImpl<Lobotomy> {

    public Lobotomy(UUID ownerId) {
        super(ownerId, 342, "Lobotomy", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2}{U}{B}");
        this.expansionSetCode = "TMP";

        this.color.setBlue(true);
        this.color.setBlack(true);

        // Target player reveals his or her hand, then you choose a card other than a basic land card from it. Search that player's graveyard, hand, and library for all cards with the same name as the chosen card and exile them. Then that player shuffles his or her library.
        this.getSpellAbility().addEffect(new LobotomyEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public Lobotomy(final Lobotomy card) {
        super(card);
    }

    @Override
    public Lobotomy copy() {
        return new Lobotomy(this);
    }
}

class LobotomyEffect extends OneShotEffect<LobotomyEffect> {

    private static final FilterCard filter = new FilterCard("card other than a basic land card");

    static {
        filter.add(Predicates.not(new SupertypePredicate("Basic")));
    }

    public LobotomyEffect() {
        super(Constants.Outcome.Benefit);
        staticText = "Target player reveals his or her hand, then you choose a card other than a basic land card from it. Search that player's graveyard, hand, and library for all cards with the same name as the chosen card and exile them. Then that player shuffles his or her library";
    }

    public LobotomyEffect(final LobotomyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player you = game.getPlayer(source.getControllerId());
        if (targetPlayer != null) {
            targetPlayer.revealCards("Lobotomy", targetPlayer.getHand(), game);
            if (you != null) {
                TargetCard target = new TargetCard(Constants.Zone.PICK, filter);
                target.setRequired(true);
                target.setNotTarget(true);
                if (you.choose(Constants.Outcome.Benefit, targetPlayer.getHand(), target, game)) {
                    Card chosenCard = targetPlayer.getHand().get(target.getFirstTarget(), game);
                    if (chosenCard != null) {
                        if (targetPlayer != null) {

                            //cards in Library
                            Cards cardsInLibrary = new CardsImpl(Constants.Zone.LIBRARY);
                            cardsInLibrary.addAll(targetPlayer.getLibrary().getCards(game));
                            you.lookAtCards("Lobotomy search of Library", cardsInLibrary, game);

                            // cards in Graveyard
                            Cards cardsInGraveyard = new CardsImpl(Constants.Zone.GRAVEYARD);
                            cardsInGraveyard.addAll(targetPlayer.getGraveyard());

                            // cards in Hand
                            Cards cardsInHand = new CardsImpl(Constants.Zone.HAND);
                            cardsInHand.addAll(targetPlayer.getHand());
                            you.lookAtCards("Lobotomy search of Hand", cardsInHand, game);

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

                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }

    @Override
    public LobotomyEffect copy() {
        return new LobotomyEffect(this);
    }
}
