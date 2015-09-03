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
package mage.sets.iceage;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceOnBattlefieldControlUnchangedCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class MeriekeRiBerit extends CardImpl {

    public MeriekeRiBerit(UUID ownerId) {
        super(ownerId, 375, "Merieke Ri Berit", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{W}{U}{B}");
        this.expansionSetCode = "ICE";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Merieke Ri Berit doesn't untap during your untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepSourceEffect()));

        // {tap}: Gain control of target creature for as long as you control Merieke Ri Berit. When Merieke Ri Berit leaves the battlefield or becomes untapped, destroy that creature. It can't be regenerated.
        ConditionalContinuousEffect MeriekeRiBeritGainControlEffect = new ConditionalContinuousEffect(
                new GainControlTargetEffect(Duration.Custom),
                new SourceOnBattlefieldControlUnchangedCondition(),
                "Gain control of target creature for as long as you control {this}");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, MeriekeRiBeritGainControlEffect, new TapSourceCost());
        ability.addTarget(new TargetPermanent(new FilterCreaturePermanent("target creature")));
        ability.addEffect(new MeriekeRiBeritCreateDelayedTriggerEffect());
        this.addAbility(ability);

    }

    public MeriekeRiBerit(final MeriekeRiBerit card) {
        super(card);
    }

    @Override
    public MeriekeRiBerit copy() {
        return new MeriekeRiBerit(this);
    }
}

class MeriekeRiBeritCreateDelayedTriggerEffect extends OneShotEffect {

    public MeriekeRiBeritCreateDelayedTriggerEffect() {
        super(Outcome.Detriment);
        this.staticText = "When {this} leaves the battlefield or becomes untapped, destroy that creature.  It can't be regenerated.";
    }

    public MeriekeRiBeritCreateDelayedTriggerEffect(final MeriekeRiBeritCreateDelayedTriggerEffect effect) {
        super(effect);
    }

    @Override
    public MeriekeRiBeritCreateDelayedTriggerEffect copy() {
        return new MeriekeRiBeritCreateDelayedTriggerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent controlledCreature = game.getPermanent(source.getFirstTarget());
        if (controlledCreature != null) {
            DelayedTriggeredAbility delayedAbility = new MeriekeRiBeritDelayedTriggeredAbility();
            delayedAbility.getEffects().get(0).setTargetPointer(new FixedTarget(controlledCreature.getId()));
            delayedAbility.setSourceId(source.getSourceId());
            delayedAbility.setControllerId(source.getControllerId());
            delayedAbility.setSourceObject(source.getSourceObject(game), game);
            delayedAbility.init(game);
            game.addDelayedTriggeredAbility(delayedAbility);
            return true;
        }
        return false;
    }
}

class MeriekeRiBeritDelayedTriggeredAbility extends DelayedTriggeredAbility {

    MeriekeRiBeritDelayedTriggeredAbility() {
        super(new DestroyTargetEffect(true), Duration.EndOfGame, true);
    }

    MeriekeRiBeritDelayedTriggeredAbility(MeriekeRiBeritDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE
                || event.getType() == EventType.UNTAPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId() != null) {
            if (GameEvent.EventType.ZONE_CHANGE.equals(event.getType())
                    && event.getTargetId().equals(getSourceId())) {
                ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
                return zEvent.getFromZone().equals(Zone.BATTLEFIELD);
            }
        }
        return GameEvent.EventType.UNTAPPED.equals(event.getType())
                && event.getTargetId() != null && event.getTargetId().equals(getSourceId());
    }

    @Override
    public MeriekeRiBeritDelayedTriggeredAbility copy() {
        return new MeriekeRiBeritDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When {this} leaves the battlefield or becomes untapped, destroy that creature. It can't be regenerated.";
    }
}
