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
package mage.sets.darkascension;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.Watcher;

/**
 *
 * @author BetaSteward
 */
public class DungeonGeists extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public DungeonGeists(UUID ownerId) {
        super(ownerId, 36, "Dungeon Geists", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.expansionSetCode = "DKA";
        this.subtype.add("Spirit");

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());

        // When Dungeon Geists enters the battlefield, tap target creature an opponent controls. That creature doesn't untap during its controller's untap step for as long as you control Dungeon Geists.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect(), false);
        ability.addEffect(new DungeonGeistsEffect());
        Target target = new TargetCreaturePermanent(filter);
        ability.addTarget(target);
        this.addAbility(ability, new DungeonGeistsWatcher());
        // watcher needed to send normal events to Dungeon Geists ReplacementEffect
    }

    public DungeonGeists(final DungeonGeists card) {
        super(card);
    }

    @Override
    public DungeonGeists copy() {
        return new DungeonGeists(this);
    }
}

class DungeonGeistsEffect extends ContinuousRuleModifyingEffectImpl {

    public DungeonGeistsEffect() {
        super(Duration.Custom, Outcome.Detriment, false, false);
        this.staticText = "That creature doesn't untap during its controller's untap step for as long as you control {this}";
    }

    public DungeonGeistsEffect(final DungeonGeistsEffect effect) {
        super(effect);
    }

    @Override
    public DungeonGeistsEffect copy() {
        return new DungeonGeistsEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNTAP || event.getType() == GameEvent.EventType.ZONE_CHANGE || event.getType() == GameEvent.EventType.LOST_CONTROL;
    }


    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        // Source must be on the battlefield (it's neccessary to check here because if as response to the enter
        // the battlefield triggered ability the source dies (or will be exiled), then the ZONE_CHANGE or LOST_CONTROL
        // event will happen before this effect is applied ever)
        Permanent sourcePermanent = (Permanent) source.getSourceObjectIfItStillExists(game);
        if (sourcePermanent == null || !sourcePermanent.getControllerId().equals(source.getControllerId())) {
            discard();
            return false;
        }
        switch(event.getType()) {
            case ZONE_CHANGE:
                // end effect if source does a zone move
                if (event.getTargetId().equals(source.getSourceId())) {
                    ZoneChangeEvent zEvent = (ZoneChangeEvent)event;
                    if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
                        discard();
                        return false;
                    }
                }
                break;
            case UNTAP:
                // prevent to untap the target creature
                if (game.getTurn().getStepType() == PhaseStep.UNTAP && event.getTargetId().equals(targetPointer.getFirst(game, source))) {
                    Permanent targetCreature = game.getPermanent(targetPointer.getFirst(game, source));
                    if (targetCreature != null) {
                        return targetCreature.getControllerId().equals(game.getActivePlayerId());
                    } else {
                        discard();
                        return false;
                    }
                }                
                break;
            case LOST_CONTROL:
                // end effect if source control is changed
                if (event.getTargetId().equals(source.getSourceId())) {
                    discard();
                    return false;
                }                
                break;
        }
        return false;
    }
}

class DungeonGeistsWatcher extends Watcher {

    DungeonGeistsWatcher () {
        super("ControlLost", WatcherScope.CARD);
    }

    DungeonGeistsWatcher(DungeonGeistsWatcher watcher) {
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
    public DungeonGeistsWatcher copy() {
        return new DungeonGeistsWatcher(this);
    }
}
