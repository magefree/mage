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
package mage.sets.mirrodinbesieged;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public class VirulentWound extends CardImpl<VirulentWound> {

    public VirulentWound(UUID ownerId) {
        super(ownerId, 57, "Virulent Wound", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{B}");
        this.expansionSetCode = "MBS";

        this.color.setBlack(true);

        // Put a -1/-1 counter on target creature.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.M1M1.createInstance(), Outcome.UnboostCreature));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // When that creature dies this turn, its controller gets a poison counter.
        this.getSpellAbility().addEffect(new VirulentWoundEffect());
    }

    public VirulentWound(final VirulentWound card) {
        super(card);
    }

    @Override
    public VirulentWound copy() {
        return new VirulentWound(this);
    }
}

class VirulentWoundEffect extends OneShotEffect<VirulentWoundEffect> {

    public VirulentWoundEffect() {
        super(Outcome.UnboostCreature);
        this.staticText = "Put a -1/-1 counter on target creature. When that creature dies this turn, its controller gets a poison counter";
    }

    public VirulentWoundEffect(final VirulentWoundEffect effect) {
        super(effect);
    }

    @Override
    public VirulentWoundEffect copy() {
        return new VirulentWoundEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DelayedTriggeredAbility delayedAbility = new VirulentWoundDelayedTriggeredAbility(source.getFirstTarget());
        delayedAbility.setSourceId(source.getSourceId());
        delayedAbility.setControllerId(source.getControllerId());
        game.addDelayedTriggeredAbility(delayedAbility);
        return true;
    }
}

class VirulentWoundDelayedTriggeredAbility extends DelayedTriggeredAbility<VirulentWoundDelayedTriggeredAbility> {

    private UUID target;

    public VirulentWoundDelayedTriggeredAbility(UUID target) {
        super(new VirulentWoundDelayedEffect(target), Duration.EndOfTurn);
        this.target = target;
    }

    public VirulentWoundDelayedTriggeredAbility(VirulentWoundDelayedTriggeredAbility ability) {
        super(ability);
        this.target = ability.target;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.ZONE_CHANGE && event.getTargetId().equals(target)) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD && zEvent.getToZone() == Zone.GRAVEYARD) {
                return true;
            }
        }
        return false;
    }

    @Override
    public VirulentWoundDelayedTriggeredAbility copy() {
        return new VirulentWoundDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When that creature dies this turn, its controller gets a poison counter.";
    }
}

class VirulentWoundDelayedEffect extends OneShotEffect<VirulentWoundDelayedEffect> {

    protected UUID target;

    public VirulentWoundDelayedEffect(UUID target) {
        super(Outcome.Damage);
        this.target = target;
    }

    public VirulentWoundDelayedEffect(final VirulentWoundDelayedEffect effect) {
        super(effect);
        this.target = effect.target;
    }

    @Override
    public VirulentWoundDelayedEffect copy() {
        return new VirulentWoundDelayedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) game.getLastKnownInformation(target, Zone.BATTLEFIELD);
        if (permanent != null) {
            Player player = game.getPlayer(permanent.getControllerId());
            if (player != null) {
                player.addCounters(CounterType.POISON.createInstance(1), game);
                return true;
            }
        }
        return false;
    }
}
