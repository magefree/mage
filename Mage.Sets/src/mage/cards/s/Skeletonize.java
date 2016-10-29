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
package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.token.SkeletonToken;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.DamagedByWatcher;

import java.util.UUID;

/**
 *
 * @author North
 */
public class Skeletonize extends CardImpl {

    public Skeletonize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{R}");

        // Skeletonize deals 3 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // When a creature dealt damage this way dies this turn, create a 1/1 black Skeleton creature token with "{B}: Regenerate this creature."
        this.getSpellAbility().addEffect(new SkeletonizeEffect());
        this.getSpellAbility().addWatcher(new DamagedByWatcher());
    }

    public Skeletonize(final Skeletonize card) {
        super(card);
    }

    @Override
    public Skeletonize copy() {
        return new Skeletonize(this);
    }
}

class SkeletonizeEffect extends OneShotEffect {

    public SkeletonizeEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "When a creature dealt damage this way dies this turn, create a 1/1 black Skeleton creature token with \"{B}: Regenerate this creature\"";
    }

    public SkeletonizeEffect(final SkeletonizeEffect effect) {
        super(effect);
    }

    @Override
    public SkeletonizeEffect copy() {
        return new SkeletonizeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DelayedTriggeredAbility delayedAbility = new SkeletonizeDelayedTriggeredAbility();
        game.addDelayedTriggeredAbility(delayedAbility, source);
        return true;
    }
}

class SkeletonizeDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public SkeletonizeDelayedTriggeredAbility() {
        super(new CreateTokenEffect(new SkeletonToken()), Duration.EndOfTurn);
    }

    public SkeletonizeDelayedTriggeredAbility(final SkeletonizeDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SkeletonizeDelayedTriggeredAbility copy() {
        return new SkeletonizeDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zce = (ZoneChangeEvent) event;
        if (zce.isDiesEvent()) {
            DamagedByWatcher watcher = (DamagedByWatcher) game.getState().getWatchers().get("DamagedByWatcher", this.getSourceId());
            if (watcher != null) {
                return watcher.wasDamaged(zce.getTarget(), game);
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When a creature dealt damage this way dies this turn, " + super.getRule();
    }
}
