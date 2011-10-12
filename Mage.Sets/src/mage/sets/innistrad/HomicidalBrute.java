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
package mage.sets.innistrad;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.WatcherImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author nantuko
 */
public class HomicidalBrute extends CardImpl<HomicidalBrute> {

    public HomicidalBrute(UUID ownerId) {
        super(ownerId, 47, "Homicidal Brute", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "");
        this.expansionSetCode = "ISD";
        this.subtype.add("Human");
        this.subtype.add("Mutant");

        // this card is the second face of double-faced card
        this.nightCard = true;
        this.canTransform = true;

        this.color.setRed(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(1);

        // At the beginning of your end step, if Homicidal Brute didn't attack this turn, tap Homicidal Brute, then transform it.
        this.addAbility(new HomicidalBruteTriggeredAbility());
    }

    public HomicidalBrute(final HomicidalBrute card) {
        super(card);
    }

    @Override
    public HomicidalBrute copy() {
        return new HomicidalBrute(this);
    }

    public static class HomicidalBruteWatcher extends WatcherImpl<HomicidalBruteWatcher> {

        public Map<UUID, Set<UUID>> blockedCreatures = new HashMap<UUID, Set<UUID>>();

        public HomicidalBruteWatcher() {
            super("HomicidalBruteWatcher");
        }

        public HomicidalBruteWatcher(final HomicidalBruteWatcher watcher) {
            super(watcher);
        }

        @Override
        public HomicidalBruteWatcher copy() {
            return new HomicidalBruteWatcher(this);
        }

        @Override
        public void watch(GameEvent event, Game game) {
            if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED && event.getSourceId().equals(sourceId)) {
                condition = true;
            }
        }
    }
}

class HomicidalBruteTriggeredAbility extends TriggeredAbilityImpl<HomicidalBruteTriggeredAbility> {

    public HomicidalBruteTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new TapSourceEffect(), false);
        addEffect(new TransformSourceEffect(false));
    }

    public HomicidalBruteTriggeredAbility(HomicidalBruteTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HomicidalBruteTriggeredAbility copy() {
        return new HomicidalBruteTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.END_PHASE_PRE && event.getPlayerId().equals(this.controllerId)) {
            HomicidalBrute.HomicidalBruteWatcher watcher = (HomicidalBrute.HomicidalBruteWatcher) game.getState().getWatchers().get(this.controllerId, "HomicidalBruteWatcher");
            if (watcher != null && !watcher.conditionMet()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of your end step, if Homicidal Brute didn't attack this turn, tap Homicidal Brute, then transform it";
    }
}

