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

import java.util.List;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
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
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author North
 */
public class RealmsUncharted extends CardImpl<RealmsUncharted> {

    public RealmsUncharted(UUID ownerId) {
        super(ownerId, 206, "Realms Uncharted", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{2}{G}");
        this.expansionSetCode = "ROE";

        this.color.setGreen(true);

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

class RealmsUnchartedEffect extends OneShotEffect<RealmsUnchartedEffect> {

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
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        RealmsUnchartedTarget target = new RealmsUnchartedTarget();
        if (player.searchLibrary(target, game)) {
            if (target.getTargets().size() > 0) {
                Cards cards = new CardsImpl();
                for (UUID cardId : (List<UUID>) target.getTargets()) {
                    Card card = player.getLibrary().remove(cardId, game);
                    if (card != null) {
                        cards.add(card);
                    }
                }
                player.revealCards("Realms Uncharted", cards, game);

                CardsImpl cardsToKeep = new CardsImpl();
                if (cards.size() > 2) {
                    cardsToKeep.addAll(cards);

                    Player opponent = game.getPlayer(game.getOpponents(player.getId()).iterator().next());
                    TargetCard targetDiscard = new TargetCard(2, Zone.PICK, new FilterCard("cards to put in graveyard"));
                    targetDiscard.setRequired(true);
                    if (opponent != null && opponent.choose(Outcome.Discard, cards, targetDiscard, game)) {
                        cardsToKeep.removeAll(targetDiscard.getTargets());
                        cards.removeAll(cardsToKeep);
                    }
                }

                for (UUID cardId : cards) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        card.moveToZone(Zone.GRAVEYARD, source.getSourceId(), game, true);
                    }
                }
                for (UUID cardId : cardsToKeep) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        card.moveToZone(Zone.HAND, source.getSourceId(), game, true);
                    }
                }
            }
            player.shuffleLibrary(game);
            return true;
        }
        player.shuffleLibrary(game);
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
