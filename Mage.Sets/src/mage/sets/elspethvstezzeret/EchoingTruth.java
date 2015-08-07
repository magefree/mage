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
package mage.sets.elspethvstezzeret;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author LevelX2
 */
public class EchoingTruth extends CardImpl {

    public EchoingTruth(UUID ownerId) {
        super(ownerId, 66, "Echoing Truth", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{U}");
        this.expansionSetCode = "DDF";

        // Return target nonland permanent and all other permanents with the same name as that permanent to their owners' hands.
        Target target = new TargetNonlandPermanent();
        this.getSpellAbility().addTarget(target);
        this.getSpellAbility().addEffect(new ReturnToHandAllNamedPermanentsEffect());
    }

    public EchoingTruth(final EchoingTruth card) {
        super(card);
    }

    @Override
    public EchoingTruth copy() {
        return new EchoingTruth(this);
    }
}

class ReturnToHandAllNamedPermanentsEffect extends OneShotEffect {

    public ReturnToHandAllNamedPermanentsEffect() {
        super(Outcome.ReturnToHand);
    }

    public ReturnToHandAllNamedPermanentsEffect(final ReturnToHandAllNamedPermanentsEffect effect) {
        super(effect);
    }

    @Override
    public ReturnToHandAllNamedPermanentsEffect copy() {
        return new ReturnToHandAllNamedPermanentsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (controller != null && permanent != null) {
            FilterPermanent filter = new FilterPermanent();
            if (permanent.getName().isEmpty()) {
                filter.add(new PermanentIdPredicate(permanent.getId()));  // if no name (face down creature) only the creature itself is selected
            } else {
                filter.add(new NamePredicate(permanent.getName()));
            }
            Cards cardsToHand = new CardsImpl();
            for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
                cardsToHand.add(perm);
            }
            controller.moveCards(cardsToHand, null, Zone.HAND, source, game);
            return true;
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return "Return target nonland permanent and all other permanents with the same name as that permanent to their owners' hands";
    }

}
