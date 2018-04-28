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

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.RandomUtil;

/**
 *
 * @author LevelX2
 */
public class HaphazardBombardment extends CardImpl {

    public HaphazardBombardment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{R}");

        // When Haphazard Bombardment enters the battlefield, choose four nonenchantment permanents you don't control and put an aim counter on each of them.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new HaphazardBombardmentEffect(), false));

        // At the beginning of your end step, if two or more permanents you don't control have an aim counter on them, destroy one of those permanents at random.
        FilterPermanent filter = new FilterPermanent("if two or more permanents you don't control have an aim counter on them");
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
        filter.add(new CounterPredicate(CounterType.AIM));
        this.addAbility(new ConditionalTriggeredAbility(new BeginningOfYourEndStepTriggeredAbility(new HaphazardBombardmentEndOfTurnEffect(), false),
                new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 1, false),
                "At the beginning of your end step, if two or more permanents you don't control have an aim counter on them, destroy one of those permanents at random"));
    }

    public HaphazardBombardment(final HaphazardBombardment card) {
        super(card);
    }

    @Override
    public HaphazardBombardment copy() {
        return new HaphazardBombardment(this);
    }
}

class HaphazardBombardmentEffect extends OneShotEffect {

    public HaphazardBombardmentEffect() {
        super(Outcome.Benefit);
        this.staticText = "choose four nonenchantment permanents you don't control and put an aim counter on each of them";
    }

    public HaphazardBombardmentEffect(final HaphazardBombardmentEffect effect) {
        super(effect);
    }

    @Override
    public HaphazardBombardmentEffect copy() {
        return new HaphazardBombardmentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            FilterPermanent filter = new FilterPermanent("nonenchantment permanents you don't control");
            filter.add(Predicates.not(new CardTypePredicate(CardType.ENCHANTMENT)));
            filter.add(new ControllerPredicate(TargetController.OPPONENT));
            List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game);
            if (permanents.size() > 4) {
                permanents.clear();
                TargetPermanent target = new TargetPermanent(4, filter);
                controller.chooseTarget(outcome, target, source, game);
                for (UUID targetId : target.getTargets()) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null) {
                        permanents.add(permanent);
                    }
                }
            }
            for (Permanent permanent : permanents) {
                permanent.addCounters(CounterType.AIM.createInstance(), source, game);
            }
            return true;

        }
        return false;
    }
}

class HaphazardBombardmentEndOfTurnEffect extends OneShotEffect {

    public HaphazardBombardmentEndOfTurnEffect() {
        super(Outcome.Benefit);
        this.staticText = "destroy one of those permanents at random";
    }

    public HaphazardBombardmentEndOfTurnEffect(final HaphazardBombardmentEndOfTurnEffect effect) {
        super(effect);
    }

    @Override
    public HaphazardBombardmentEndOfTurnEffect copy() {
        return new HaphazardBombardmentEndOfTurnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        // 4/27/2018 	If one or more of the permanents with aim counters on them have indestructible,
        // select the permanent destroyed at random from among the permanents with aim counters
        // that don’t have indestructible.
        FilterPermanent filter = new FilterPermanent("if two or more permanents you don't control have an aim counter on them");
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
        filter.add(new CounterPredicate(CounterType.AIM));
        filter.add(Predicates.not(new AbilityPredicate(IndestructibleAbility.class)));
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game);
        Permanent permanent = permanents.get(RandomUtil.nextInt(permanents.size()));
        if (permanent != null) {
            permanent.destroy(source.getSourceId(), game, false);
        }
        return true;
    }
}
