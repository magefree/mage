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

package mage.sets.championsofkamigawa;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.watchers.Watcher;

import java.util.UUID;

/**
 * @author Loki
 */
public class FloatingDreamZubera extends CardImpl {

    public FloatingDreamZubera(UUID ownerId) {
        super(ownerId, 61, "Floating-Dream Zubera", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Zubera");
        this.subtype.add("Spirit");

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);
        this.addAbility(new DiesTriggeredAbility(new DrawCardSourceControllerEffect(new ZuberasDiedDynamicValue())), new ZuberasDiedWatcher());
    }

    public FloatingDreamZubera(final FloatingDreamZubera card) {
        super(card);
    }

    @Override
    public FloatingDreamZubera copy() {
        return new FloatingDreamZubera(this);
    }

}


class ZuberasDiedWatcher extends Watcher {

    public int zuberasDiedThisTurn = 0;

    public ZuberasDiedWatcher() {
        super("ZuberasDied", WatcherScope.GAME);
    }

    public ZuberasDiedWatcher(final ZuberasDiedWatcher watcher) {
        super(watcher);
        this.zuberasDiedThisTurn = watcher.zuberasDiedThisTurn;
    }

    @Override
    public ZuberasDiedWatcher copy() {
        return new ZuberasDiedWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent) event).isDiesEvent()) {
            MageObject card = game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (card != null && card.hasSubtype("Zubera", game)) {
                zuberasDiedThisTurn++;
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        zuberasDiedThisTurn = 0;
    }

}

class ZuberasDiedDynamicValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        ZuberasDiedWatcher watcher = (ZuberasDiedWatcher) game.getState().getWatchers().get("ZuberasDied");
        return watcher.zuberasDiedThisTurn;
    }

    @Override
    public ZuberasDiedDynamicValue copy() {
        return new ZuberasDiedDynamicValue();
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "Zubera that died this turn";
    }
}
