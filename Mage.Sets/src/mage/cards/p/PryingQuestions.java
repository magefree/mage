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
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class PryingQuestions extends CardImpl {

    public PryingQuestions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");

        // Target opponent loses 3 life and puts a card from his or her hand on top of his or her library.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(3));
        this.getSpellAbility().addEffect(new PryingQuestionsEffect());

    }

    public PryingQuestions(final PryingQuestions card) {
        super(card);
    }

    @Override
    public PryingQuestions copy() {
        return new PryingQuestions(this);
    }
}

class PryingQuestionsEffect extends OneShotEffect {

    public PryingQuestionsEffect() {
        super(Outcome.Detriment);
        this.staticText = "and puts a card from his or her hand on top of his or her library";
    }

    public PryingQuestionsEffect(final PryingQuestionsEffect effect) {
        super(effect);
    }

    @Override
    public PryingQuestionsEffect copy() {
        return new PryingQuestionsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetOpponent = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (targetOpponent != null) {
            if (targetOpponent.getHand().size() > 0) {
                TargetCardInHand target = new TargetCardInHand();
                target.setNotTarget(true);
                target.setTargetName("a card from your hand to put on top of your library");
                targetOpponent.choose(Outcome.Detriment, target, source.getSourceId(), game);
                Card card = targetOpponent.getHand().get(target.getFirstTarget(), game);
                if (card != null) {
                    targetOpponent.moveCardToLibraryWithInfo(card, source.getSourceId(), game, Zone.HAND, true, false);
                }
            }
            return true;
        }
        return false;
    }
}
