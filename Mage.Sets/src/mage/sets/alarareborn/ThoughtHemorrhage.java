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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public class ThoughtHemorrhage extends CardImpl<ThoughtHemorrhage> {

    public ThoughtHemorrhage(UUID ownerId) {
        super(ownerId, 47, "Thought Hemorrhage", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{2}{B}{R}");
        this.expansionSetCode = "ARB";

        this.color.setRed(true);
        this.color.setBlack(true);

        // Name a nonland card. Target player reveals his or her hand. Thought Hemorrhage deals 3 damage to that player for each card with that name revealed this way. Search that player's graveyard, hand, and library for all cards with that name and exile them. Then that player shuffles his or her library.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new ThoughtHemorrhageEffect());
    }

    public ThoughtHemorrhage(final ThoughtHemorrhage card) {
        super(card);
    }

    @Override
    public ThoughtHemorrhage copy() {
        return new ThoughtHemorrhage(this);
    }
}

class ThoughtHemorrhageEffect extends OneShotEffect<ThoughtHemorrhageEffect> {

    String cardName;
    final String rule = "Name a nonland card. Target player reveals his or her hand. Thought Hemorrhage deals 3 damage to that player for each card with that name revealed this way. Search that player's graveyard, hand, and library for all cards with that name and exile them. Then that player shuffles his or her library";

    public ThoughtHemorrhageEffect() {
        super(Outcome.Detriment);
        staticText = rule;
    }

    public ThoughtHemorrhageEffect(final ThoughtHemorrhageEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        if (you != null) {
            Choice cardChoice = new ChoiceImpl();
            cardChoice.setChoices(CardRepository.instance.getNonLandNames());
            cardChoice.clearChoice();
            while (!you.choose(Outcome.Detriment, cardChoice, game)) {
                if (!you.isInGame()) {
                    return false;
                }
            }
            cardName = cardChoice.getChoice();
            game.informPlayers("Thought Hemorrhage, named card: [" + cardName + "]");
        }
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer != null) {
            targetPlayer.revealCards("hand of target player", targetPlayer.getHand(), game);
            for (Card card : targetPlayer.getHand().getCards(game)) {
                if (card.getName().equals(cardName)) {
                    targetPlayer.damage(3, source.getId(), game, false, true);
                }
            }
            for (Card card : targetPlayer.getGraveyard().getCards(game)) {
                if (card.getName().equals(cardName)) {
                    card.moveToExile(null, "", source.getId(), game);
                }
            }
            for (Card card : targetPlayer.getHand().getCards(game)) {
                if (card.getName().equals(cardName)) {
                    card.moveToExile(null, "", source.getId(), game);
                }
            }
            for (Card card : targetPlayer.getLibrary().getCards(game)) {
                if (card.getName().equals(cardName)) {
                    card.moveToExile(null, "", source.getId(), game);
                }
            }
            targetPlayer.shuffleLibrary(game);
            return true;
        }
        return false;
    }

    @Override
    public ThoughtHemorrhageEffect copy() {
        return new ThoughtHemorrhageEffect(this);
    }
}
