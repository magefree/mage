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
package mage.cards.v;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2
 */
public class Void extends CardImpl {

    public Void(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{R}");

        // Choose a number. Destroy all artifacts and creatures with converted mana cost equal to that number. Then target player reveals his or her hand and discards all nonland cards with converted mana cost equal to the number.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new VoidEffect());

    }

    public Void(final Void card) {
        super(card);
    }

    @Override
    public Void copy() {
        return new Void(this);
    }
}

class VoidEffect extends OneShotEffect {

    public VoidEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Choose a number. Destroy all artifacts and creatures with converted mana cost equal to that number. Then target player reveals his or her hand and discards all nonland cards with converted mana cost equal to the number";
    }

    public VoidEffect(final VoidEffect effect) {
        super(effect);
    }

    @Override
    public VoidEffect copy() {
        return new VoidEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Choice numberChoice = new ChoiceImpl();
            Set<String> numbers = new HashSet<>(16);
            for (int i = 0; i <= 15; i++) {
                numbers.add(Integer.toString(i));
            }
            numberChoice.setChoices(numbers);
            numberChoice.setMessage("Choose a number");
            controller.choose(Outcome.DestroyPermanent, numberChoice, game);
            int number = Integer.parseInt(numberChoice.getChoice());
            for (Permanent permanent : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
                if ((permanent.isArtifact() || permanent.isCreature())
                        && permanent.getConvertedManaCost() == number) {
                    permanent.destroy(source.getSourceId(), game, false);
                }
            }
            FilterCard filterCard = new FilterCard();
            filterCard.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, number));
            filterCard.add(Predicates.not(new CardTypePredicate(CardType.LAND)));

            Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
            if (targetPlayer != null) {
                targetPlayer.revealCards("Void", targetPlayer.getHand(), game);
                for (Card card : targetPlayer.getHand().getCards(game)) {
                    if (filterCard.match(card, game)) {
                        targetPlayer.discard(card, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
