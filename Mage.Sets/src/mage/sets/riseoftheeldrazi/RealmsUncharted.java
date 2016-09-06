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
package mage.sets.riseoftheeldrazi;

import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;

/**
 *
 * @author North
 */
public class RealmsUncharted extends CardImpl {

    public RealmsUncharted(UUID ownerId) {
        super(ownerId, 206, "Realms Uncharted", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{2}{G}");
        this.expansionSetCode = "ROE";

        // Search your library for four land cards with different names and reveal them. An opponent chooses two of those cards. Put the chosen cards into your graveyard and the rest into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new RealmsUnchartedEffect());
    }

    public RealmsUncharted(final RealmsUncharted card) {
        super(card);
    }

    @Override
    public RealmsUncharted copy() {
        return new RealmsUncharted(this);
    }
}

class RealmsUnchartedEffect extends OneShotEffect {

    public RealmsUnchartedEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Search your library for four land cards with different names and reveal them. An opponent chooses two of those cards. Put the chosen cards into your graveyard and the rest into your hand. Then shuffle your library";
    }

    public RealmsUnchartedEffect(final RealmsUnchartedEffect effect) {
        super(effect);
    }

    @Override
    public RealmsUnchartedEffect copy() {
        return new RealmsUnchartedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller == null || sourceObject == null) {
            return false;
        }

        RealmsUnchartedTarget target = new RealmsUnchartedTarget();
        if (controller.searchLibrary(target, game)) {
            if (target.getTargets().size() > 0) {
                Cards cards = new CardsImpl();
                for (UUID cardId : target.getTargets()) {
                    Card card = controller.getLibrary().getCard(cardId, game);
                    if (card != null) {
                        cards.add(card);
                    }
                }
                controller.revealCards(sourceObject.getName(), cards, game);

                CardsImpl cardsToKeep = new CardsImpl();
                if (cards.size() > 2) {
                    cardsToKeep.addAll(cards);

                    Player opponent;
                    Set<UUID> opponents = game.getOpponents(controller.getId());
                    if (opponents.size() == 1) {
                        opponent = game.getPlayer(opponents.iterator().next());
                    } else {
                        Target targetOpponent = new TargetOpponent(true);
                        controller.chooseTarget(Outcome.Detriment, targetOpponent, source, game);
                        opponent = game.getPlayer(targetOpponent.getFirstTarget());
                    }
                    TargetCard targetDiscard = new TargetCard(2, Zone.LIBRARY, new FilterCard("cards to put in graveyard"));
                    if (opponent != null && opponent.choose(Outcome.Discard, cards, targetDiscard, game)) {
                        cardsToKeep.removeAll(targetDiscard.getTargets());
                        cards.removeAll(cardsToKeep);
                    }
                }
                controller.moveCards(cards, Zone.GRAVEYARD, source, game);
                controller.moveCards(cardsToKeep, Zone.HAND, source, game);
            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        controller.shuffleLibrary(source, game);
        return false;
    }
}

class RealmsUnchartedTarget extends TargetCardInLibrary {

    public RealmsUnchartedTarget() {
        super(0, 4, new FilterLandCard("land cards with different names"));
    }

    public RealmsUnchartedTarget(final RealmsUnchartedTarget target) {
        super(target);
    }

    @Override
    public RealmsUnchartedTarget copy() {
        return new RealmsUnchartedTarget(this);
    }

    @Override
    public boolean canTarget(UUID id, Cards cards, Game game) {
        Card card = cards.get(id, game);
        if (card != null) {
            for (UUID targetId : this.getTargets()) {
                Card iCard = game.getCard(targetId);
                if (iCard != null && iCard.getName().equals(card.getName())) {
                    return false;
                }
            }
            return filter.match(card, game);
        }
        return false;
    }
}
