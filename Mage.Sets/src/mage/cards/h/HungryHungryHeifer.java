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
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author spjspj
 */
public class HungryHungryHeifer extends CardImpl {

    public HungryHungryHeifer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.COW);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of your upkeep, you may remove a counter from a permanent you control. If you don't, sacrifice Hungry Hungry Heifer.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new HungryHungryHeiferEffect(), TargetController.YOU, false, false));
    }

    public HungryHungryHeifer(final HungryHungryHeifer card) {
        super(card);
    }

    @Override
    public HungryHungryHeifer copy() {
        return new HungryHungryHeifer(this);
    }
}

class HungryHungryHeiferEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a permanent you control with a counter on it");

    static {
        filter.add(new CounterPredicate(null));
    }

    public HungryHungryHeiferEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "you may remove a counter from a permanent you control. If you don't, sacrifice {this}";
    }

    public HungryHungryHeiferEffect(final HungryHungryHeiferEffect effect) {
        super(effect);
    }

    @Override
    public HungryHungryHeiferEffect copy() {
        return new HungryHungryHeiferEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourceObject = (Permanent) source.getSourceObjectIfItStillExists(game);
        if (sourceObject != null && controller != null) {
            if (controller.chooseUse(outcome, "Remove a counter from a permanent you control?", source, game)) {
                TargetControlledPermanent target = new TargetControlledPermanent(1, 1, filter, true);
                RemoveCounterCost cost = new RemoveCounterCost(target);
                if (cost.pay(null, game, source.getSourceId(), controller.getId(), true)) {
                    return true;
                }
            }
            sourceObject.sacrifice(source.getSourceId(), game);
            return true;
        }
        return false;
    }
}
