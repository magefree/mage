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
package mage.sets.eldritchmoon;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public class CoaxFromTheBlindEternities extends CardImpl {

    public CoaxFromTheBlindEternities(UUID ownerId) {
        super(ownerId, 51, "Coax from the Blind Eternities", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{2}{U}");
        this.expansionSetCode = "EMN";

        // You may choose an Eldrazi card you own from outside the game or in exile, reveal that card, and put it into your hand.
        this.getSpellAbility().addEffect(new CoaxFromTheBlindEternitiesEffect());
    }

    public CoaxFromTheBlindEternities(final CoaxFromTheBlindEternities card) {
        super(card);
    }

    @Override
    public CoaxFromTheBlindEternities copy() {
        return new CoaxFromTheBlindEternities(this);
    }
}

class CoaxFromTheBlindEternitiesEffect extends OneShotEffect {

    private static final String choiceText = "Choose a Eldrazi card you own from outside the game (sideboard) or in exile, and put it into your hand?";

    private static final FilterCard filter = new FilterCard("Eldrazi card");

    static {
        filter.add(new SubtypePredicate("Eldrazi"));
    }

    public CoaxFromTheBlindEternitiesEffect() {
        super(Outcome.Benefit);
        this.staticText = "You may choose an Eldrazi card you own from outside the game or in exile, reveal that card, and put it into your hand";
    }

    public CoaxFromTheBlindEternitiesEffect(final CoaxFromTheBlindEternitiesEffect effect) {
        super(effect);
    }

    @Override
    public CoaxFromTheBlindEternitiesEffect copy() {
        return new CoaxFromTheBlindEternitiesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            if (controller.chooseUse(Outcome.Benefit, choiceText, source, game)) {
                Set<Card> sideboard = controller.getSideboard().getCards(filter, game);
                List<Card> exile = game.getExile().getAllCards(game);
                Cards filteredCards = new CardsImpl();
                Card card = null;

                for (Card sideboardCard : sideboard) {
                    filteredCards.add(sideboardCard.getId());
                }
                for (Card exileCard : exile) {
                    if (exileCard.getOwnerId().equals(source.getControllerId()) && exileCard.hasSubtype("Eldrazi", game)) {
                        filteredCards.add(exileCard);
                    }
                }

                if (filteredCards.isEmpty()) {
                    game.informPlayer(controller, "You have no " + filter.getMessage() + " outside the game (your sideboard) or in exile.");
                }
                else {
                    TargetCard target = new TargetCard(Zone.OUTSIDE, filter);
                    target.setNotTarget(true);
                    if (controller.choose(outcome, filteredCards, target, game)) {
                        card = controller.getSideboard().get(target.getFirstTarget(), game);
                        if (card == null) {
                            card = game.getCard(target.getFirstTarget());
                        }
                    }
                }

                if (card != null) {
                    card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                    controller.revealCards(sourceObject.getIdName(), new CardsImpl(card), game);
                }
            }
            return true;
        }
        return false;
    }
}
