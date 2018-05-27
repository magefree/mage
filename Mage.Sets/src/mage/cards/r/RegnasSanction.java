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
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapAllEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChooseFriendsAndFoes;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801
 */
public class RegnasSanction extends CardImpl {

    public RegnasSanction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}");

        // For each player, choose friend or foe. Each friend puts a +1/+1 counter on each creature they control. Each foe chooses one untapped creature they control, then taps the rest.
        this.getSpellAbility().addEffect(new RegnasSanctionEffect());
    }

    public RegnasSanction(final RegnasSanction card) {
        super(card);
    }

    @Override
    public RegnasSanction copy() {
        return new RegnasSanction(this);
    }
}

class RegnasSanctionEffect extends OneShotEffect {

    RegnasSanctionEffect() {
        super(Outcome.Benefit);
        this.staticText = "For each player, choose friend or foe. Each friend puts a +1/+1 counter on each creature they control. Each foe chooses one untapped creature they control, then taps the rest";
    }

    RegnasSanctionEffect(final RegnasSanctionEffect effect) {
        super(effect);
    }

    @Override
    public RegnasSanctionEffect copy() {
        return new RegnasSanctionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        ChooseFriendsAndFoes choice = new ChooseFriendsAndFoes();
        if (!choice.chooseFriendOrFoe(controller, source, game)) {
            return false;
        }
        FilterCreaturePermanent filterToTap = new FilterCreaturePermanent();
        for (Player player : choice.getFoes()) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent("untapped creature you control");
            filter.add(Predicates.not(new TappedPredicate()));
            filter.add(new ControllerIdPredicate(player.getId()));
            TargetPermanent target = new TargetPermanent(1, 1, filter, true);
            if (player.choose(Outcome.Benefit, target, source.getSourceId(), game)) {
                filterToTap.add(Predicates.not(new PermanentIdPredicate(target.getFirstTarget())));
            }
        }
        for (Player player : choice.getFriends()) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(new ControllerIdPredicate(player.getId()));
            new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter).apply(game, source);
            filterToTap.add(Predicates.not(new ControllerIdPredicate(player.getId())));
        }
        return new TapAllEffect(filterToTap).apply(game, source);
    }
}
