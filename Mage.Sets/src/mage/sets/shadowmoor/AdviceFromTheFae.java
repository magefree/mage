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
package mage.sets.shadowmoor;

import java.util.List;
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
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author jeffwadsworth
 */
public class AdviceFromTheFae extends CardImpl {

    public AdviceFromTheFae(UUID ownerId) {
        super(ownerId, 28, "Advice from the Fae", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2/U}{2/U}{2/U}");
        this.expansionSetCode = "SHM";

        // <i>({2U} can be paid with any two mana or with {U}. This card's converted mana cost is 6.)</i>
        // Look at the top five cards of your library. If you control more creatures than each other player, put two of those cards into your hand. Otherwise, put one of them into your hand. Then put the rest on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new AdviceFromTheFaeEffect());

    }

    public AdviceFromTheFae(final AdviceFromTheFae card) {
        super(card);
    }

    @Override
    public AdviceFromTheFae copy() {
        return new AdviceFromTheFae(this);
    }
}

class AdviceFromTheFaeEffect extends OneShotEffect {

    public AdviceFromTheFaeEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Look at the top five cards of your library. If you control more creatures than each other player, put two of those cards into your hand. Otherwise, put one of them into your hand. Then put the rest on the bottom of your library in any order";
    }

    public AdviceFromTheFaeEffect(final AdviceFromTheFaeEffect effect) {
        super(effect);
    }

    @Override
    public AdviceFromTheFaeEffect copy() {
        return new AdviceFromTheFaeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getObject(source.getSourceId());
        if (controller != null) {
            List<Card> cardsFromTopLibrary = controller.getLibrary().getTopCards(game, 5);
            Cards cards = new CardsImpl(Zone.LIBRARY);
            for (Card card : cardsFromTopLibrary) {
                cards.add(card);
            }
            controller.lookAtCards(mageObject.getName(), cards, game);
            int max = 0;
            for (UUID playerId : controller.getInRange()) {
                FilterCreaturePermanent filter = new FilterCreaturePermanent();
                filter.add(new ControllerIdPredicate(playerId));
                if (playerId != controller.getId()) {
                    if (max < game.getBattlefield().countAll(filter, playerId, game)) {
                        max = game.getBattlefield().countAll(filter, playerId, game);
                    }
                }
            }
            if (game.getBattlefield().countAll(new FilterControlledCreaturePermanent(), controller.getId(), game) > max) {
                TargetCard target = new TargetCard(2, Zone.LIBRARY, new FilterCard());
                if (controller.choose(Outcome.DrawCard, cards, target, game)) {
                    controller.moveCardToHandWithInfo(game.getCard(target.getFirstTarget()), source.getSourceId(), game, Zone.LIBRARY);
                    cards.remove(game.getCard(target.getFirstTarget()));
                    controller.moveCardToHandWithInfo(game.getCard(target.getTargets().get(1)), source.getSourceId(), game, Zone.LIBRARY);
                    cards.remove(game.getCard(target.getTargets().get(1)));
                }
            } else {
                TargetCard target = new TargetCard(1, Zone.LIBRARY, new FilterCard());
                if (controller.choose(Outcome.DrawCard, cards, target, game)) {
                    controller.moveCardToHandWithInfo(game.getCard(target.getFirstTarget()), source.getSourceId(), game, Zone.LIBRARY);
                    cards.remove(game.getCard(target.getFirstTarget()));
                }
            }
            controller.putCardsOnBottomOfLibrary(cards, game, source, true);
            return true;
        }
        return false;
    }
}
