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
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public class Withdraw extends CardImpl {

    public Withdraw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{U}");

        // Return target creature to its owner's hand. Then return another target creature to its owner's hand unless its controller pays {1}.
        this.getSpellAbility().addEffect(new WithdrawEffect());
        Target target = new TargetCreaturePermanent(new FilterCreaturePermanent("creature to return unconditionally"));
        target.setTargetTag(1);
        this.getSpellAbility().addTarget(target);

        FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature to return unless {1} is paid");
        filter.add(new AnotherTargetPredicate(2));
        target = new TargetCreaturePermanent(filter);
        target.setTargetTag(2);
        this.getSpellAbility().addTarget(target);
    }

    public Withdraw(final Withdraw card) {
        super(card);
    }

    @Override
    public Withdraw copy() {
        return new Withdraw(this);
    }
}

class WithdrawEffect extends OneShotEffect {

    WithdrawEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return target creature to its owner's hand. Then return another target creature to its owner's hand unless its controller pays {1}";
    }

    WithdrawEffect(final WithdrawEffect effect) {
        super(effect);
    }

    @Override
    public WithdrawEffect copy() {
        return new WithdrawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Effect effect = new ReturnToHandTargetEffect();
        effect.setTargetPointer(new FixedTarget(source.getFirstTarget()));
        effect.apply(game, source);
        Permanent secondCreature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (secondCreature != null) {
            Player creatureController = game.getPlayer(secondCreature.getControllerId());
            if (creatureController != null) {
                Cost cost = new GenericManaCost(1);
                if (creatureController.chooseUse(Outcome.Benefit, "Pay {1}? (Otherwise " + secondCreature.getName() + " will be returned to its owner's hand)", source, game)) {
                    cost.pay(source, game, source.getSourceId(), creatureController.getId(), false);
                }
                if (!cost.isPaid()) {
                    creatureController.moveCards(secondCreature, Zone.HAND, source, game);
                }
            }
        }
        return true;
    }
}
