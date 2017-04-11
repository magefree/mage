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
package mage.cards.m;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public class MayaelsAria extends CardImpl {

    public MayaelsAria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{R}{G}{W}");

        // At the beginning of your upkeep, put a +1/+1 counter on each creature you control if you control a creature with power 5 or greater.
        // Then you gain 10 life if you control a creature with power 10 or greater.
        // Then you win the game if you control a creature with power 20 or greater.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new MayaelsAriaEffect(), TargetController.YOU, false));
    }

    public MayaelsAria(final MayaelsAria card) {
        super(card);
    }

    @Override
    public MayaelsAria copy() {
        return new MayaelsAria(this);
    }
}

class MayaelsAriaEffect extends OneShotEffect {

    public MayaelsAriaEffect() {
        super(Outcome.Benefit);
        this.staticText = "put a +1/+1 counter on each creature you control if you control a creature with power 5 or greater. Then you gain 10 life if you control a creature with power 10 or greater. Then you win the game if you control a creature with power 20 or greater";
    }

    public MayaelsAriaEffect(final MayaelsAriaEffect effect) {
        super(effect);
    }

    @Override
    public MayaelsAriaEffect copy() {
        return new MayaelsAriaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        // put a +1/+1 counter on each creature you control if you control a creature with power 5 or greater.
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 4));
        if (game.getState().getBattlefield().countAll(filter, controller.getId(), game) > 0) {
            for (Permanent creature : game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), source.getControllerId(), game)) {
                creature.addCounters(CounterType.P1P1.createInstance(), source, game);
            }
        }
        game.applyEffects(); // needed because otehrwise the +1/+1 counters wouldn't be taken into account

        // Then you gain 10 life if you control a creature with power 10 or greater.
        filter = new FilterCreaturePermanent();
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 9));
        if (game.getState().getBattlefield().countAll(filter, controller.getId(), game) > 0) {
            controller.gainLife(10, game);
        }

        // Then you win the game if you control a creature with power 20 or greater.
        filter = new FilterCreaturePermanent();
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 19));
        if (game.getState().getBattlefield().countAll(filter, controller.getId(), game) > 0) {
            controller.won(game);
        }
        return true;
    }
}
