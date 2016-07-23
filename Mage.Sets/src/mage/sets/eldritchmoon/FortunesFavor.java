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

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
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
import mage.target.common.TargetOpponent;
import mage.util.MessageToClient;

/**
 *
 * @author LevelX2
 */
public class FortunesFavor extends CardImpl {

    public FortunesFavor(UUID ownerId) {
        super(ownerId, 61, "Fortune's Favor", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{3}{U}");
        this.expansionSetCode = "EMN";

        // Target opponent looks at the top four cards of your library and separates them into a face-down pile and a face-up pile. Put one pile into your hand and the other into your graveyard.
        getSpellAbility().addEffect(new FortunesFavorEffect());
        getSpellAbility().addTarget(new TargetOpponent());
    }

    public FortunesFavor(final FortunesFavor card) {
        super(card);
    }

    @Override
    public FortunesFavor copy() {
        return new FortunesFavor(this);
    }
}

class FortunesFavorEffect extends OneShotEffect {

    public FortunesFavorEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target opponent looks at the top four cards of your library and separates them into a face-down pile and a face-up pile. Put one pile into your hand and the other into your graveyard";
    }

    public FortunesFavorEffect(final FortunesFavorEffect effect) {
        super(effect);
    }

    @Override
    public FortunesFavorEffect copy() {
        return new FortunesFavorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetOpponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && targetOpponent != null && sourceObject != null) {
            Cards cards = new CardsImpl();
            cards.addAll(controller.getLibrary().getTopCards(game, 4));

            TargetCard target = new TargetCard(0, Integer.MAX_VALUE, Zone.LIBRARY, new FilterCard("cards for the face-down pile"));
            targetOpponent.choose(outcome, cards, target, game);
            Cards faceDownPile = new CardsImpl();
            faceDownPile.addAll(target.getTargets());
            cards.removeAll(target.getTargets());
            controller.revealCards(sourceObject.getIdName() + " - cards in face-up pile", cards, game);
            game.informPlayers(targetOpponent.getLogName() + " puts " + faceDownPile.size() + " card(s) into the face-down pile");
            MessageToClient message = new MessageToClient("Put the face-down pile into your hand?", "(If you say yes, the face-up pile goes to the graveyard.)");
            if (controller.chooseUse(outcome, message, source, game)) {
                controller.moveCards(faceDownPile, Zone.HAND, source, game);
                controller.moveCards(cards, Zone.GRAVEYARD, source, game);
            } else {
                controller.moveCards(faceDownPile, Zone.GRAVEYARD, source, game);
                controller.moveCards(cards, Zone.HAND, source, game);
            }
            return true;
        }
        return false;
    }
}
