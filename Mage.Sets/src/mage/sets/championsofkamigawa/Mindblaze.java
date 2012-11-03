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

package mage.sets.championsofkamigawa;

import java.util.HashSet;
import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;


/**
 *
 * @author Loki
 */
public class Mindblaze extends CardImpl<Mindblaze> {

    public Mindblaze (UUID ownerId) {
        super(ownerId, 180, "Mindblaze", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{5}{R}");
        this.expansionSetCode = "CHK";
        this.color.setRed(true);

        // Name a nonland card and choose a number greater than 0. Target player reveals his or her library.
        // If that library contains exactly the chosen number of the named card,
        // Mindblaze deals 8 damage to that player.
        // Then that player shuffles his or her library.
        this.getSpellAbility().addEffect(new MindblazeEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public Mindblaze (final Mindblaze card) {
        super(card);
    }

    @Override
    public Mindblaze copy() {
        return new Mindblaze(this);
    }

}

class MindblazeEffect extends OneShotEffect<MindblazeEffect> {
    MindblazeEffect() {
        super(Constants.Outcome.Damage);
        staticText = "Name a nonland card and choose a number greater than 0. Target player reveals his or her library. If that library contains exactly the chosen number of the named card, {this} deals 8 damage to that player. Then that player shuffles his or her library";
    }

    MindblazeEffect(final MindblazeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        Player playerControls = game.getPlayer(source.getControllerId());
        if (player != null && playerControls != null) {
            Choice cardChoice = new ChoiceImpl();
            cardChoice.setChoices(CardRepository.instance.getNonLandNames());
            cardChoice.clearChoice();
            Choice numberChoice = new ChoiceImpl();
            numberChoice.setMessage("Choose a number greater than 0");
            HashSet<String> numbers = new HashSet<String>();
            for (int i = 1; i <= 4; i++) {
                numbers.add(Integer.toString(i));
            }
            numberChoice.setChoices(numbers);

            while (!playerControls.choose(Constants.Outcome.Neutral, cardChoice, game)) {
                game.debugMessage("player canceled choosing name. retrying.");
            }

            while (!playerControls.choose(Constants.Outcome.Neutral, numberChoice, game)) {
                game.debugMessage("player canceled choosing number. retrying.");
            }

            game.informPlayers("Mindblaze, named card: [" + cardChoice.getChoice() + "]");
            game.informPlayers("Mindblaze, chosen number: [" + numberChoice.getChoice() + "]");

            Cards cards = new CardsImpl();
            cards.addAll(player.getLibrary().getCards(game));
            playerControls.revealCards("Library", cards, game);
            FilterCard filter = new FilterCard();
            filter.add(new NamePredicate(cardChoice.getChoice()));
            int count = Integer.parseInt(numberChoice.getChoice());
            if (player.getLibrary().count(filter, game) == count) {
                player.damage(8, source.getSourceId(), game.copy(), false, true);
            }
            player.shuffleLibrary(game);
        }
        return false;
    }

    @Override
    public MindblazeEffect copy() {
        return new MindblazeEffect(this);
    }

}