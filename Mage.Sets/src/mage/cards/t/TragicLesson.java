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
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author spjspj
 */
public class TragicLesson extends CardImpl {

    public TragicLesson(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Draw two cards. Then discard a card unless you return a land you control to its owner's hand.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
        this.getSpellAbility().addEffect(new TragicLessonEffect());
    }

    public TragicLesson(final TragicLesson card) {
        super(card);
    }

    @Override
    public TragicLesson copy() {
        return new TragicLesson(this);
    }
}

class TragicLessonEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a land you control");

    static {
        filter.add(new CardTypePredicate(CardType.LAND));
    }

    public TragicLessonEffect() {
        super(Outcome.Discard);
        staticText = "Then discard a card unless you return a land you control to its owner's hand.";
    }

    public TragicLessonEffect(final TragicLessonEffect effect) {
        super(effect);
    }

    @Override
    public TragicLessonEffect copy() {
        return new TragicLessonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        if (controller != null
                && controller.chooseUse(Outcome.Discard, "Do you want to return a land you control to its owner's hand? If you don't, you must discard 1 card", source, game)) {
            Cost cost = new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(filter));
            if (cost.canPay(source, source.getSourceId(), controller.getId(), game)) {
                if (cost.pay(source, game, source.getSourceId(), controller.getId(), false, null)) {
                    return true;                    
                }
            }
        }
        if (controller != null) {
            controller.discard(1, false, source, game);
            return true;
        }
        return false;
    }
}
