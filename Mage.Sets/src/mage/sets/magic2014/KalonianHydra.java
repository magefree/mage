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
package mage.sets.magic2014;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class KalonianHydra extends CardImpl<KalonianHydra> {

    public KalonianHydra(UUID ownerId) {
        super(ownerId, 181, "Kalonian Hydra", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.expansionSetCode = "M14";
        this.subtype.add("Hydra");

        this.color.setGreen(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Kalonian Hydra enters the battlefield with four +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(4))));
        // Whenever Kalonian Hydra attacks, double the number of +1/+1 counters on each creature you control.
        this.addAbility(new AttacksTriggeredAbility(new KalonianHydraEffect(), false));

    }

    public KalonianHydra(final KalonianHydra card) {
        super(card);
    }

    @Override
    public KalonianHydra copy() {
        return new KalonianHydra(this);
    }
}


class KalonianHydraEffect extends OneShotEffect<KalonianHydraEffect> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new CounterPredicate(CounterType.P1P1));
    }

    public KalonianHydraEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "double the number of +1/+1 counters on each creature you control";
    }

    public KalonianHydraEffect(final KalonianHydraEffect effect) {
        super(effect);
    }

    @Override
    public KalonianHydraEffect copy() {
        return new KalonianHydraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game);
        for (Permanent permanent : permanents) {
            int existingCounters = permanent.getCounters().getCount(CounterType.P1P1);
            if (existingCounters > 0) {
                permanent.addCounters(CounterType.P1P1.createInstance(existingCounters), game);
            }
        }
        return true;
    }
}
