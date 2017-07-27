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
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author jeffwadsworth
 */
public class RitesOfRefusal extends CardImpl {

    public RitesOfRefusal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Discard any number of cards. Counter target spell unless its controller pays {3} for each card discarded this way.
        this.getSpellAbility().addEffect(new RitesOfRefusalEffect());
        this.getSpellAbility().addTarget(new TargetSpell());

    }

    public RitesOfRefusal(final RitesOfRefusal card) {
        super(card);
    }

    @Override
    public RitesOfRefusal copy() {
        return new RitesOfRefusal(this);
    }
}

class RitesOfRefusalEffect extends OneShotEffect {

    RitesOfRefusalEffect() {
        super(Outcome.AIDontUseIt);
        this.staticText = "Discard any number of cards. Counter target spell unless its controller pays {3} for each card discarded this way";
    }

    RitesOfRefusalEffect(final RitesOfRefusalEffect effect) {
        super(effect);
    }

    @Override
    public RitesOfRefusalEffect copy() {
        return new RitesOfRefusalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Spell targetSpell = game.getStack().getSpell(source.getFirstTarget());
        if (targetSpell != null) {
            Player controllerOfTargetedSpell = game.getPlayer(targetSpell.getControllerId());
            if (controller != null
                    && controllerOfTargetedSpell != null) {
                int numToDiscard = controller.getAmount(0, controller.getHand().size(), "How many cards do you want to discard?", game);
                Cards discardedCards = controller.discard(numToDiscard, false, source, game);
                int actualNumberDiscarded = discardedCards.size();
                GenericManaCost cost = new GenericManaCost(actualNumberDiscarded * 3);
                if (controllerOfTargetedSpell.chooseUse(Outcome.AIDontUseIt, "Do you want to pay " + cost.convertedManaCost() + " to prevent " + targetSpell.getName() + " from gettting countered?", source, game)
                        && cost.pay(source, game, source.getSourceId(), controllerOfTargetedSpell.getId(), false)) {
                    return true;
                } else {
                    targetSpell.counter(source.getSourceId(), game);
                    return true;
                }
            }
        }
        return false;
    }
}
