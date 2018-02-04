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
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author L_J
 */
public class LiarsPendulum extends CardImpl {

    public LiarsPendulum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {2}, {T}: Choose a card name. Target opponent guesses whether a card with that name is in your hand. You may reveal your hand. If you do and your opponent guessed wrong, draw a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LiarsPendulumEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public LiarsPendulum(final LiarsPendulum card) {
        super(card);
    }

    @Override
    public LiarsPendulum copy() {
        return new LiarsPendulum(this);
    }
}

class LiarsPendulumEffect extends OneShotEffect {

    public LiarsPendulumEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Choose a card name. Target opponent guesses whether a card with that name is in your hand. You may reveal your hand. If you do and your opponent guessed wrong, draw a card";
    }

    public LiarsPendulumEffect(final LiarsPendulumEffect effect) {
        super(effect);
    }

    @Override
    public LiarsPendulumEffect copy() {
        return new LiarsPendulumEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (controller != null && opponent != null) {
            // Name a card.
            Choice choice = new ChoiceImpl();
            choice.setChoices(CardRepository.instance.getNames());
            choice.setMessage("Choose a card name");
            if (!controller.choose(Outcome.Benefit, choice, game)) {
                return false;
            }
            String cardName = choice.getChoice();
            game.informPlayers("Card named: " + cardName);
            boolean opponentGuess = false;

            if (opponent.chooseUse(Outcome.Neutral, "Is the chosen card (" + cardName + ") in " + controller.getLogName() + "'s hand?", source, game)) {
                opponentGuess = true;
            }
            boolean rightGuess = !opponentGuess;

            for (Card card : controller.getHand().getCards(game)) {
                if (card.isSplitCard()) {
                    SplitCard splitCard = (SplitCard) card;
                    if (splitCard.getLeftHalfCard().getName().equals(cardName)) {
                        rightGuess = opponentGuess;
                    } else if (splitCard.getRightHalfCard().getName().equals(cardName)) {
                        rightGuess = opponentGuess;
                    }
                }
                if (card.getName().equals(cardName)) {
                    rightGuess = opponentGuess;
                }
            }
            game.informPlayers(opponent.getLogName() + " guesses that " + cardName + " is " + (opponentGuess ? "" : "not") + " in " + controller.getLogName() + "'s hand");

            if (controller.chooseUse(outcome, "Reveal your hand?", source, game)) {
                controller.revealCards("hand of " + controller.getName(), controller.getHand(), game);
                if (!rightGuess) {
                    controller.drawCards(1, game);
                }
            }
            return true;
        }
        return false;
    }

}
