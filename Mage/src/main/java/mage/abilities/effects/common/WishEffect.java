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
package mage.abilities.effects.common;

import java.util.List;
import java.util.Set;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author Styxo
 */
public class WishEffect extends OneShotEffect {

    private final FilterCard filter;
    private final boolean reveal;
    private final boolean alsoFromExile;
    private final String choiceText;

    public WishEffect() {
        this(new FilterCard());
    }

    public WishEffect(FilterCard filter) {
        this(filter, true);
    }

    public WishEffect(FilterCard filter, boolean reveal) {
        this(filter, reveal, false);
    }

    public WishEffect(FilterCard filter, boolean reveal, boolean alsoFromExile) {
        super(Outcome.DrawCard);
        this.filter = filter;
        this.alsoFromExile = alsoFromExile;
        this.reveal = reveal;
        choiceText = "Choose " + filter.getMessage() + " you own from outside the game"
                + (alsoFromExile ? " or in exile" : "")
                + (reveal ? ", reveal that card," : "")
                + " and put it into your hand.";
        staticText = "You may c" + choiceText.substring(1, choiceText.length() - 1);

    }

    public WishEffect(final WishEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.alsoFromExile = effect.alsoFromExile;
        this.reveal = effect.reveal;
        this.choiceText = effect.choiceText;
    }

    @Override
    public WishEffect copy() {
        return new WishEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            if (controller.chooseUse(Outcome.Benefit, choiceText, source, game)) {
                Cards cards = controller.getSideboard();
                List<Card> exile = game.getExile().getAllCards(game);
                boolean noTargets = cards.isEmpty() && (alsoFromExile ? exile.isEmpty() : true);
                if (noTargets) {
                    game.informPlayer(controller, "You have no cards outside the game" + (alsoFromExile ? " or in exile" : "") + '.');
                    return true;
                }

                Set<Card> filtered = cards.getCards(filter, game);
                Cards filteredCards = new CardsImpl();
                for (Card card : filtered) {
                    filteredCards.add(card.getId());
                }
                if (alsoFromExile) {
                    for (Card exileCard : exile) {
                        if (exileCard.getOwnerId().equals(source.getControllerId()) && filter.match(exileCard, game)) {
                            filteredCards.add(exileCard);
                        }
                    }
                }
                if (filteredCards.isEmpty()) {
                    game.informPlayer(controller, "You don't have " + filter.getMessage() + " outside the game" + (alsoFromExile ? " or in exile" : "") + '.');
                    return true;
                }

                TargetCard target = new TargetCard(Zone.OUTSIDE, filter);
                target.setNotTarget(true);
                if (controller.choose(Outcome.Benefit, filteredCards, target, game)) {
                    Card card = controller.getSideboard().get(target.getFirstTarget(), game);
                    if (card == null && alsoFromExile) {
                        card = game.getCard(target.getFirstTarget());
                    }
                    if (card != null) {
                        card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                        if (reveal) {
                            Cards revealCard = new CardsImpl();
                            revealCard.add(card);
                            controller.revealCards(sourceObject.getIdName(), revealCard, game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

}
