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
package mage.cards.b;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public class BloodOath extends CardImpl {

    public BloodOath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");

        // Choose a card type. Target opponent reveals his or her hand. Blood Oath deals 3 damage to that player for each card of the chosen type revealed this way.
        this.getSpellAbility().addEffect(new BloodOathEffect());
    }

    public BloodOath(final BloodOath card) {
        super(card);
    }

    @Override
    public BloodOath copy() {
        return new BloodOath(this);
    }
}

class BloodOathEffect extends OneShotEffect {

    private static final Set<String> choice = new LinkedHashSet<>();

    static {
        choice.add(CardType.ARTIFACT.toString());
        choice.add(CardType.CREATURE.toString());
        choice.add(CardType.ENCHANTMENT.toString());
        choice.add(CardType.INSTANT.toString());
        choice.add(CardType.LAND.toString());
        choice.add(CardType.PLANESWALKER.toString());
        choice.add(CardType.SORCERY.toString());
        choice.add(CardType.TRIBAL.toString());
    }

    public BloodOathEffect() {
        super(Outcome.Benefit);
        staticText = "Choose a card type. Target opponent reveals his or her hand. {this} deals 3 damage to that player for each card of the chosen type revealed this way";
    }

    public BloodOathEffect(final BloodOathEffect effect) {
        super(effect);
    }

    @Override
    public BloodOathEffect copy() {
        return new BloodOathEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = game.getObject(source.getSourceId());
        Player player = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (player != null && opponent != null && sourceObject != null) {
            Choice choiceImpl = new ChoiceImpl();
            choiceImpl.setChoices(choice);
            while (player.canRespond() && !player.choose(Outcome.Neutral, choiceImpl, game)) {
            }
            CardType type = null;
            String choosenType = choiceImpl.getChoice();

            if (choosenType.equals(CardType.ARTIFACT.toString())) {
                type = CardType.ARTIFACT;
            } else if (choosenType.equals(CardType.LAND.toString())) {
                type = CardType.LAND;
            } else if (choosenType.equals(CardType.CREATURE.toString())) {
                type = CardType.CREATURE;
            } else if (choosenType.equals(CardType.ENCHANTMENT.toString())) {
                type = CardType.ENCHANTMENT;
            } else if (choosenType.equals(CardType.INSTANT.toString())) {
                type = CardType.INSTANT;
            } else if (choosenType.equals(CardType.SORCERY.toString())) {
                type = CardType.SORCERY;
            } else if (choosenType.equals(CardType.PLANESWALKER.toString())) {
                type = CardType.PLANESWALKER;
            } else if (choosenType.equals(CardType.TRIBAL.toString())) {
                type = CardType.TRIBAL;
            }
            if (type != null) {
                Cards hand = opponent.getHand();
                opponent.revealCards(sourceObject.getIdName(), hand, game);
                Set<Card> cards = hand.getCards(game);
                int count = 0;
                for (Card card : cards) {
                    if (card != null && card.getCardType().contains(type)) {
                        count += 1;
                    }
                }
                opponent.damage(count * 3, source.getSourceId(), game, false, true);
                return true;
            }
        }
        return false;
    }
}
