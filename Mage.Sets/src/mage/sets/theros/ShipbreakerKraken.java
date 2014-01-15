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
package mage.sets.theros;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.Rarity;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.WatcherImpl;

/**
 *
 * @author LevelX2
 */
public class ShipbreakerKraken extends CardImpl<ShipbreakerKraken> {

    public ShipbreakerKraken(UUID ownerId) {
        super(ownerId, 63, "Shipbreaker Kraken", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");
        this.expansionSetCode = "THS";
        this.subtype.add("Kraken");

        this.color.setBlue(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // {6}{U}{U}: Monstrosity 4.
        this.addAbility(new MonstrosityAbility("{6}{U}{U}", 4));
        // When Shipbreaker Kraken becomes monstrous, tap up to four target creatures. Those creatures don't untap during their controllers' untap steps for as long as you control Shipbreaker Kraken.
        Ability ability = new BecomesMonstrousSourceTriggeredAbility(new TapTargetEffect());
        ability.addTarget(new TargetCreaturePermanent(0,4));
        ability.addEffect(new ShipbreakerKrakenReplacementEffect());
        this.addAbility(ability);
        this.addWatcher(new ShipbreakerKrakenWatcher());
    }

    public ShipbreakerKraken(final ShipbreakerKraken card) {
        super(card);
    }

    @Override
    public ShipbreakerKraken copy() {
        return new ShipbreakerKraken(this);
    }
}

class ShipbreakerKrakenReplacementEffect extends ReplacementEffectImpl<ShipbreakerKrakenReplacementEffect> {

    public ShipbreakerKrakenReplacementEffect() {
        super(Duration.OneUse, Outcome.Detriment);
        this.staticText = "Those creatures don't untap during their controllers' untap steps for as long as you control {this}";
    }

    public ShipbreakerKrakenReplacementEffect(final ShipbreakerKrakenReplacementEffect effect) {
        super(effect);
    }

    @Override
    public ShipbreakerKrakenReplacementEffect copy() {
        return new ShipbreakerKrakenReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        // Source must be on the battlefield (it's neccessary to check here because if as response to the enter
        // the battlefield triggered ability the source dies (or will be exiled), then the ZONE_CHANGE or LOST_CONTROL
        // event will happen before this effect is applied ever)
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent == null || !sourcePermanent.getControllerId().equals(source.getControllerId())) {
            this.used = true;
            return false;
        }
        if (event.getType() == GameEvent.EventType.LOST_CONTROL) {
            if (event.getTargetId().equals(source.getSourceId())) {
                this.used = true;
                return false;
            }
        }
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && event.getTargetId().equals(source.getSourceId())) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent)event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
                this.used = true;
                return false;
            }
        }

        if (game.getTurn().getStepType() == PhaseStep.UNTAP && event.getType() == GameEvent.EventType.UNTAP) {
            if (targetPointer.getTargets(game, source).contains(event.getTargetId())) {
                return true;
            }
        }

        return false;
    }
}

class ShipbreakerKrakenWatcher extends WatcherImpl<ShipbreakerKrakenWatcher> {

    ShipbreakerKrakenWatcher () {
        super("ControlLost", WatcherScope.CARD);
    }

    ShipbreakerKrakenWatcher(ShipbreakerKrakenWatcher watcher) {
        super(watcher);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.LOST_CONTROL && event.getPlayerId().equals(controllerId) && event.getTargetId().equals(sourceId)) {
            condition = true;
            game.replaceEvent(event);
            return;
        }
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && event.getTargetId().equals(sourceId)) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent)event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
                condition = true;
                game.replaceEvent(event);
            }
        }
    }

    @Override
    public void reset() {
        //don't reset condition each turn - only when this leaves the battlefield
    }

    @Override
    public ShipbreakerKrakenWatcher copy() {
        return new ShipbreakerKrakenWatcher(this);
    }
}
