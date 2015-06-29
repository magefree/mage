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
package mage.sets.tempest;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class Magmasaur extends CardImpl {

    public Magmasaur(UUID ownerId) {
        super(ownerId, 188, "Magmasaur", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.expansionSetCode = "TMP";
        this.subtype.add("Elemental");
        this.subtype.add("Lizard");
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Magmasaur enters the battlefield with five +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(5)),
                "with five +1/+1 counters on it"));

        // At the beginning of your upkeep, you may remove a +1/+1 counter from Magmasaur. If you don't, sacrifice Magmasaur and it deals damage equal to the number of +1/+1 counters on it to each creature without flying and each player.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new MagmasaurEffect(), TargetController.YOU, false, false));
    }

    public Magmasaur(final Magmasaur card) {
        super(card);
    }

    @Override
    public Magmasaur copy() {
        return new Magmasaur(this);
    }
}

class MagmasaurEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public MagmasaurEffect() {
        super(Outcome.Damage);
        this.staticText = "you may remove a +1/+1 counter from {this}. If you don't, sacrifice {this} and it deals damage equal to the number of +1/+1 counters on it to each creature without flying and each player";
    }

    public MagmasaurEffect(final MagmasaurEffect effect) {
        super(effect);
    }

    @Override
    public MagmasaurEffect copy() {
        return new MagmasaurEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourceObject = (Permanent)source.getSourceObjectIfItStillExists(game);
        if (sourceObject != null && controller != null) {
            if (controller.chooseUse(outcome, "Remove a +1/+1 counter from " + sourceObject.getLogName() + "?", source, game)) {
                sourceObject.getCounters().removeCounter(CounterType.P1P1, 1);
            } else {
                int counters = sourceObject.getCounters().getCount(CounterType.P1P1);
                sourceObject.sacrifice(source.getSourceId(), game);
                new DamageEverythingEffect(counters, filter).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
