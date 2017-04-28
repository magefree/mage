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
package mage.cards.e;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author LevelX2
 */
public class ErdwalIlluminator extends CardImpl {

    public ErdwalIlluminator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add("Spirit");
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever you investigate for the first time each turn, investigate an additional time.
        this.addAbility(new ErdwalIlluminatorTriggeredAbility());

    }

    public ErdwalIlluminator(final ErdwalIlluminator card) {
        super(card);
    }

    @Override
    public ErdwalIlluminator copy() {
        return new ErdwalIlluminator(this);
    }
}

class ErdwalIlluminatorTriggeredAbility extends TriggeredAbilityImpl {

    public ErdwalIlluminatorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new InvestigateEffect(), false);
        addWatcher(new InvestigatedWatcher());
    }

    public ErdwalIlluminatorTriggeredAbility(final ErdwalIlluminatorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.INVESTIGATED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        InvestigatedWatcher watcher = (InvestigatedWatcher) game.getState().getWatchers().get(InvestigatedWatcher.class.getName());
        return watcher != null && watcher.getTimesInvestigated(getControllerId()) == 1;
    }

    @Override
    public ErdwalIlluminatorTriggeredAbility copy() {
        return new ErdwalIlluminatorTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you investigate for the first time each turn, investigate an additional time.";
    }
}

class InvestigatedWatcher extends Watcher {

    private final HashMap<UUID, Integer> timesInvestigated = new HashMap<>();

    public InvestigatedWatcher() {
        super(InvestigatedWatcher.class.getName(), WatcherScope.GAME);
    }

    public InvestigatedWatcher(final InvestigatedWatcher watcher) {
        super(watcher);
    }

    @Override
    public InvestigatedWatcher copy() {
        return new InvestigatedWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.INVESTIGATED) {
            timesInvestigated.put(event.getPlayerId(), getTimesInvestigated(event.getPlayerId()) + 1);

        }
    }

    @Override
    public void reset() {
        super.reset();
        timesInvestigated.clear();
    }

    public int getTimesInvestigated(UUID playerId) {
        return timesInvestigated.getOrDefault(playerId, 0);
    }
}
