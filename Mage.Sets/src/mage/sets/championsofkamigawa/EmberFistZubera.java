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

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.common.TargetCreatureOrPlayer;
import mage.watchers.Watcher;
import mage.watchers.WatcherImpl;

/**
 *
 * @author Loki
 */
public class EmberFistZubera extends CardImpl<EmberFistZubera> {

    public EmberFistZubera (UUID ownerId) {
        super(ownerId, 166, "Ember-Fist Zubera", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Zubera");
        this.subtype.add("Spirit");
		this.color.setRed(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);
        Ability ability = new PutIntoGraveFromBattlefieldTriggeredAbility(new DamageTargetEffect(new EmberFistZuberaDynamicValue()));
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);
        this.watchers.add(new EmberFistZuberaWatcher(ownerId));
    }

    public EmberFistZubera (final EmberFistZubera card) {
        super(card);
    }

    @Override
    public EmberFistZubera copy() {
        return new EmberFistZubera(this);
    }

}

class EmberFistZuberaWatcher extends WatcherImpl<EmberFistZuberaWatcher> {

    public int zuberasDiedThisTurn = 0;

    public EmberFistZuberaWatcher(UUID controllerId) {
        super("ZuberasDiedEmberFistZubera", controllerId);
    }

    public EmberFistZuberaWatcher(final EmberFistZuberaWatcher watcher) {
        super(watcher);
    }

    @Override
    public EmberFistZuberaWatcher copy() {
        return new EmberFistZuberaWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            if (((ZoneChangeEvent) event).getFromZone() == Constants.Zone.BATTLEFIELD &&
                    ((ZoneChangeEvent) event).getToZone() == Constants.Zone.GRAVEYARD) {
                Card card = game.getLastKnownInformation(event.getTargetId(), Constants.Zone.BATTLEFIELD);
                if (card != null && card.getSubtype().contains("Zubera")) {
                    zuberasDiedThisTurn++;
                }
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        zuberasDiedThisTurn = 0;
    }

}

class EmberFistZuberaDynamicValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        Watcher watcher = game.getState().getWatchers().get(sourceAbility.getControllerId(), "ZuberasDiedEmberFistZubera");
        return ((EmberFistZuberaWatcher) watcher).zuberasDiedThisTurn;
    }

    @Override
    public DynamicValue clone() {
        return new EmberFistZuberaDynamicValue();
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "Zubera put into all graveyards from play this turn";
    }
}

