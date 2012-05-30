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

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.WatcherScope;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.Watcher;
import mage.watchers.WatcherImpl;

import java.util.UUID;

/**
 *
 * @author BetaSteward
 */
public class DungeonGeists extends CardImpl<DungeonGeists> {

	private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");

	static {
		filter.setTargetController(Constants.TargetController.OPPONENT);
	}

    public DungeonGeists(UUID ownerId) {
        super(ownerId, 36, "Dungeon Geists", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.expansionSetCode = "DKA";
        this.subtype.add("Spirit");

        this.color.setBlue(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());

        // When Dungeon Geists enters the battlefield, tap target creature an opponent controls. That creature doesn't untap during its controller's untap step for as long as you control Dungeon Geists.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect(), false);
        ability.addEffect(new DungeonGeistsEffect());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
        this.addWatcher(new DungeonGeistsWatcher());
    }

    public DungeonGeists(final DungeonGeists card) {
        super(card);
    }

    @Override
    public DungeonGeists copy() {
        return new DungeonGeists(this);
    }
}

class DungeonGeistsEffect extends ReplacementEffectImpl<DungeonGeistsEffect> {

	public DungeonGeistsEffect() {
		super(Constants.Duration.OneUse, Constants.Outcome.Detriment);
        this.staticText = "That creature doesn't untap during its controller's untap step for as long as you control Dungeon Geists";
	}

	public DungeonGeistsEffect(final DungeonGeistsEffect effect) {
		super(effect);
	}

	@Override
	public DungeonGeistsEffect copy() {
		return new DungeonGeistsEffect(this);
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
        //don't replace untap event if control of this has been lost
        Watcher watcher = game.getState().getWatchers().get("ControlLost", source.getSourceId());
        if (watcher == null || !watcher.conditionMet()) {
            if (game.getTurn().getStepType() == Constants.PhaseStep.UNTAP && event.getType() == GameEvent.EventType.UNTAP) {
                if (event.getTargetId().equals(targetPointer.getFirst(source))) {
                    return true;
                }
            }
        }
		return false;
	}
}

class DungeonGeistsWatcher extends WatcherImpl<DungeonGeistsWatcher> {

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
            return;
		}
		if (event.getType() == GameEvent.EventType.ZONE_CHANGE && event.getTargetId().equals(sourceId)) {
			ZoneChangeEvent zEvent = (ZoneChangeEvent)event;
			if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
				condition = false;
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
