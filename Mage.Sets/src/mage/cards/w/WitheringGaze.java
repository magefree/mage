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
package mage.sets.portal;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RevealHandTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LoneFox

 */
public class WitheringGaze extends CardImpl {

    public WitheringGaze(UUID ownerId) {
        super(ownerId, 79, "Withering Gaze", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2}{U}");
        this.expansionSetCode = "POR";

        // Target opponent reveals his or her hand. You draw a card for each Forest and green card in it.
        this.getSpellAbility().addEffect(new RevealHandTargetEffect());
        this.getSpellAbility().addEffect(new WitheringGazeEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    public WitheringGaze(final WitheringGaze card) {
        super(card);
    }

    @Override
    public WitheringGaze copy() {
        return new WitheringGaze(this);
    }
}

class WitheringGazeEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("Forest or green card");

    static {
        filter.add(Predicates.or(new SubtypePredicate("Forest"),
            new ColorPredicate(ObjectColor.GREEN)));
    }

    public WitheringGazeEffect() {
        super(Outcome.DrawCard);
        this.staticText = "You draw a card for each Forest and green card in it";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getTargets().getFirstTarget());
        if(controller != null && targetPlayer != null) {
            int count = 0;
            for(Card card : targetPlayer.getHand().getCards(game)) {
               if(filter.match(card, game)) {
                   count++;
               }
            }
            controller.drawCards(count, game);
            return true;
        }
        return false;
    }

    public WitheringGazeEffect(final WitheringGazeEffect effect) {
        super(effect);
    }

    @Override
    public WitheringGazeEffect copy() {
        return new WitheringGazeEffect(this);
    }
}
