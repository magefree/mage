/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.cards.a;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

import java.util.Set;
import java.util.UUID;

/**
 *
 * @author fenhl
 */
public class AnimalMagnetism extends CardImpl {

    public AnimalMagnetism(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{G}");

        // Reveal the top five cards of your library. An opponent chooses a creature card from among them. Put that card onto the battlefield and the rest into your graveyard.
        this.getSpellAbility().addEffect(new AnimalMagnetismEffect());
    }

    public AnimalMagnetism(final AnimalMagnetism card) {
        super(card);
    }

    public AnimalMagnetism copy() {
        return new AnimalMagnetism(this);
    }
}

class AnimalMagnetismEffect extends OneShotEffect {

    public AnimalMagnetismEffect() {
        super(Outcome.Benefit);
        this.staticText = "Reveal the top five cards of your library. An opponent chooses a creature card from among them. Put that card onto the battlefield and the rest into your graveyard";
    }

    public AnimalMagnetismEffect(final AnimalMagnetismEffect effect) {
        super(effect);
    }

    @Override
    public AnimalMagnetismEffect copy() {
        return new AnimalMagnetismEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject != null && controller != null) {
            Cards cards = new CardsImpl();
            cards.addAll(controller.getLibrary().getTopCards(game, 5));
            if (!cards.isEmpty()) {
                controller.revealCards(staticText, cards, game);
                Card cardToHand;
                if (cards.size() == 1) {
                    cardToHand = cards.getRandom(game);
                } else {
                    Player opponent;
                    Set<UUID> opponents = game.getOpponents(controller.getId());
                    if (opponents.size() == 1) {
                        opponent = game.getPlayer(opponents.iterator().next());
                    } else {
                        Target target = new TargetOpponent(true);
                        controller.chooseTarget(Outcome.Detriment, target, source, game);
                        opponent = game.getPlayer(target.getFirstTarget());
                    }
                    TargetCard target = new TargetCard(1, Zone.LIBRARY, new FilterCreatureCard());
                    opponent.chooseTarget(outcome, cards, target, source, game);
                    cardToHand = game.getCard(target.getFirstTarget());
                }
                if (cardToHand != null) {
                    controller.moveCards(cardToHand, Zone.HAND, source, game);
                    cards.remove(cardToHand);
                }
                controller.moveCards(cards, Zone.GRAVEYARD, source, game);
            }
            return true;
        }
        return false;
    }
}
