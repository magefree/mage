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
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public class CrookedScales extends CardImpl {

    public CrookedScales(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {4}, {tap}: Flip a coin. If you win the flip, destroy target creature an opponent controls. If you lose the flip, destroy target creature you control unless you pay {3} and repeat this process.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CrookedScalesEffect(), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent());
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    public CrookedScales(final CrookedScales card) {
        super(card);
    }

    @Override
    public CrookedScales copy() {
        return new CrookedScales(this);
    }
}

class CrookedScalesEffect extends OneShotEffect {

    CrookedScalesEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Flip a coin. If you win the flip, destroy target creature an opponent controls. If you lose the flip, destroy target creature you control unless you pay {3} and repeat this process";
    }

    CrookedScalesEffect(final CrookedScalesEffect effect) {
        super(effect);
    }

    @Override
    public CrookedScalesEffect copy() {
        return new CrookedScalesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent yourGuy = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent theirGuy = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        boolean keepGoing;
        Cost cost;
        String message = "You lost the flip. Pay {3} to prevent your creature from being destroyed?";
        do {
            if (controller.flipCoin(game)) {
                if (theirGuy != null) {
                    theirGuy.destroy(controller.getId(), game, false);
                }
                keepGoing = false;
            } else {
                cost = new GenericManaCost(3);
                if (!(controller.chooseUse(Outcome.Benefit, message, source, game) && cost.pay(source, game, controller.getId(), controller.getId(), false, null))) {
                    if (yourGuy != null) {
                        yourGuy.destroy(controller.getId(), game, false);
                    }
                    keepGoing = false;
                } else {
                    keepGoing = true;
                }
            }
        } while (keepGoing);
        return true;
    }
}
