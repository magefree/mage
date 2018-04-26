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
package mage.cards.q;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.Counter;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class QuarantineField extends CardImpl {

    public QuarantineField(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{X}{X}{W}{W}");

        // Quarantine Field enters the battlefield with X isolation counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(new Counter("isolation"))));

        // When Quarantine Field enters the battlefield, for each isolation counter on it, exile up to one target nonland permanent an opponenet controls until Quarantine Field leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new QuarantineFieldEffect(), false);
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        this.addAbility(ability);

    }

    public QuarantineField(final QuarantineField card) {
        super(card);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof EntersBattlefieldTriggeredAbility) {
            Permanent sourceObject = game.getPermanent(ability.getSourceId());
            if (sourceObject != null) {
                int isolationCounters = sourceObject.getCounters(game).getCount("isolation");
                FilterNonlandPermanent filter = new FilterNonlandPermanent("up to " + isolationCounters + " nonland permanents controlled by any opponents");
                filter.add(new ControllerPredicate(TargetController.OPPONENT));
                ability.addTarget(new TargetPermanent(0, isolationCounters, filter, false));
            }

        }
    }

    @Override
    public QuarantineField copy() {
        return new QuarantineField(this);
    }
}

class QuarantineFieldEffect extends OneShotEffect {

    public QuarantineFieldEffect() {
        super(Outcome.Exile);
        this.staticText = "for each isolation counter on it, exile up to one target nonland permanent an opponenet controls until {this} leaves the battlefield";
    }

    public QuarantineFieldEffect(final QuarantineFieldEffect effect) {
        super(effect);
    }

    @Override
    public QuarantineFieldEffect copy() {
        return new QuarantineFieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        // If the source permanent leaves the battlefield before its triggered ability resolves,
        // the targets won't be exiled.
        if (permanent != null) {
            return new ExileTargetEffect(CardUtil.getCardExileZoneId(game, source), permanent.getIdName()).apply(game, source);
        }
        return false;
    }
}
