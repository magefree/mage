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
package mage.sets.urzassaga;

import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public class Persecute extends CardImpl {

    public Persecute(UUID ownerId) {
        super(ownerId, 146, "Persecute", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");
        this.expansionSetCode = "USG";

        // Choose a color. Target player reveals his or her hand and discards all cards of that color.
        this.getSpellAbility().addEffect(new PersecuteEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

    }

    public Persecute(final Persecute card) {
        super(card);
    }

    @Override
    public Persecute copy() {
        return new Persecute(this);
    }
}

class PersecuteEffect extends OneShotEffect {

    public PersecuteEffect() {
        super(Outcome.Discard);
        this.staticText = "Choose a color. Target player reveals his or her hand and discards all cards of that color";
    }

    public PersecuteEffect(final PersecuteEffect effect) {
        super(effect);
    }

    @Override
    public PersecuteEffect copy() {
        return new PersecuteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            ChoiceColor choice = new ChoiceColor();
            while (!choice.isChosen()) {
                controller.choose(outcome, choice, game);
                if (!controller.canRespond()) {
                    return false;
                }
            }
            if (choice.getColor() == null) {
                return false;
            }
            Cards hand = controller.getHand();
            controller.revealCards(sourceObject.getIdName(), hand, game);
            Set<Card> cards = hand.getCards(game);
            for (Card card : cards) {
                if (card != null && card.getColor(game).shares(choice.getColor())) {
                    controller.discard(card, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
