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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class MayaelsAria extends CardImpl<MayaelsAria> {

    public MayaelsAria(UUID ownerId) {
        super(ownerId, 121, "Mayael's Aria", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{R}{G}{W}");
        this.expansionSetCode = "ARB";

        this.color.setRed(true);
        this.color.setGreen(true);
        this.color.setWhite(true);

        // At the beginning of your upkeep, put a +1/+1 counter on each creature you control if you control a creature with power 5 or greater. Then you gain 10 life if you control a creature with power 10 or greater. Then you win the game if you control a creature with power 20 or greater.
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

class MayaelsAriaEffect extends OneShotEffect<MayaelsAriaEffect> {

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
        boolean condition1 = false;
        boolean condition2 = false;
        Player you = game.getPlayer(source.getControllerId());
        if (you == null) {
            return false;
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        for (Permanent creature : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
            if (creature.getPower().getValue() > 4) {
                condition1 = true;
            }
            if (creature.getPower().getValue() > 9) {
                condition2 = true;
            }
            if (creature.getPower().getValue() > 19) {
                you.won(game);
            }
        }
        if (condition1) {
            for (Permanent creature : game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
                creature.addCounters(CounterType.P1P1.createInstance(), game);
            }
        }
        if (condition2) {
            you.gainLife(10, game);
        }
        return true;
    }
}
