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
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author Styxo
 */
public class ForceChoke extends CardImpl {

    public ForceChoke(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{B}");

        // Counter target spell. Its controller may pay life equal to that spell's cmc to return it to its owner's hand.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new ForceChokeEffect());

        // Scry 2
        this.getSpellAbility().addEffect(new ScryEffect(2));
    }

    public ForceChoke(final ForceChoke card) {
        super(card);
    }

    @Override
    public ForceChoke copy() {
        return new ForceChoke(this);
    }
}

class ForceChokeEffect extends OneShotEffect {

    public ForceChokeEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Counter target spell. Its controller may pay life equal to that spell's converted mana cost to return it to its owner's hand";
    }

    public ForceChokeEffect(final ForceChokeEffect effect) {
        super(effect);
    }

    @Override
    public ForceChokeEffect copy() {
        return new ForceChokeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        StackObject stackObject = (StackObject) game.getObject(getTargetPointer().getFirst(game, source));
        Player objectController = game.getPlayer(stackObject.getControllerId());
        if (player != null) {
            Cost cost = new PayLifeCost(stackObject.getConvertedManaCost());
            if (cost.canPay(source, source.getSourceId(), objectController.getId(), game)
                    && objectController.chooseUse(Outcome.LoseLife, "Pay " + stackObject.getConvertedManaCost() + " life?", source, game)
                    && cost.pay(source, game, source.getSourceId(), objectController.getId(), false, null)) {
                objectController.moveCards((Card) stackObject, Zone.HAND, source, game);
            } else {
                stackObject.counter(source.getId(), game);
            }
            return true;
        }
        return false;
    }
}
