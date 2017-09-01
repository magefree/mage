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
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */
public class TemporalDistortion extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature or land");
    private static final FilterPermanent filter2 = new FilterPermanent("permanents with hourglass counters on them");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.LAND), new CardTypePredicate(CardType.CREATURE)));
        filter2.add(new CounterPredicate(CounterType.HOURGLASS));
    }

    public TemporalDistortion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}{U}");

        // Whenever a creature or land becomes tapped, put an hourglass counter on it.
        Effect effect = new AddCountersTargetEffect(CounterType.HOURGLASS.createInstance());
        effect.setText("put an hourglass counter on it");
        this.addAbility(new BecomesTappedTriggeredAbility(effect, false, filter, true));

        // Permanents with hourglass counters on them don't untap during their controllers' untap steps.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepAllEffect(Duration.WhileOnBattlefield, TargetController.ANY, filter2)));

        // At the beginning of each player's upkeep, remove all hourglass counters from permanents that player controls.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new TemporalDistortionRemovalEffect(), TargetController.ANY, false));
    }

    public TemporalDistortion(final TemporalDistortion card) {
        super(card);
    }

    @Override
    public TemporalDistortion copy() {
        return new TemporalDistortion(this);
    }
}

class TemporalDistortionRemovalEffect extends OneShotEffect {

    public TemporalDistortionRemovalEffect() {
        super(Outcome.Neutral);
        staticText = "remove all hourglass counters from permanents that player controls";
    }

    public TemporalDistortionRemovalEffect(final TemporalDistortionRemovalEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(game.getActivePlayerId())) {
            permanent.removeCounters(CounterType.HOURGLASS.createInstance(permanent.getCounters(game).getCount(CounterType.HOURGLASS)), game);
        }
        return true;
    }

    @Override
    public TemporalDistortionRemovalEffect copy() {
        return new TemporalDistortionRemovalEffect(this);
    }
}
