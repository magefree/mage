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
package mage.sets.lorwyn;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.HideawayPlayEffect;
import mage.abilities.keyword.HideawayAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;
import mage.watchers.WatcherImpl;

/**
 *
 * @author LevelX2
 */
public class WindbriskHeights extends CardImpl<WindbriskHeights> {

    public WindbriskHeights(UUID ownerId) {
        super(ownerId, 281, "Windbrisk Heights", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "LRW";

        // Hideaway (This land enters the battlefield tapped. When it does, look at the top four cards of your library, exile one face down, then put the rest on the bottom of your library.)
        this.addAbility(new HideawayAbility(this));
        // {tap}: Add {W} to your mana pool.
        this.addAbility(new WhiteManaAbility());
        // {W}, {tap}: You may play the exiled card without paying its mana cost if you attacked with three or more creatures this turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new HideawayPlayEffect(), new ManaCostsImpl("{W}"), WindbriskHeightsAttackersCondition.getInstance());
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);     
        
        this.addWatcher(new WindbriskHeightsWatcher());

    }

    public WindbriskHeights(final WindbriskHeights card) {
        super(card);
    }

    @Override
    public WindbriskHeights copy() {
        return new WindbriskHeights(this);
    }
}

class WindbriskHeightsWatcher extends WatcherImpl<WindbriskHeightsWatcher> {

    private int numberOfattackers;

    public WindbriskHeightsWatcher() {
        super("WindbriskHeightsAttackersWatcher", WatcherScope.PLAYER);
    }

    public WindbriskHeightsWatcher(final WindbriskHeightsWatcher watcher) {
        super(watcher);
        this.numberOfattackers = watcher.numberOfattackers;
    }

    @Override
    public WindbriskHeightsWatcher copy() {
        return new WindbriskHeightsWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DECLARED_ATTACKERS && game.getActivePlayerId().equals(this.getControllerId())) {
            numberOfattackers += game.getCombat().getAttackers().size(); // because there are more than one attack phase possible, sum up the attackers
            if (numberOfattackers > 2) {
                condition = true;
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        numberOfattackers = 0;
    }
}

class WindbriskHeightsAttackersCondition implements Condition {

    private static WindbriskHeightsAttackersCondition fInstance = new WindbriskHeightsAttackersCondition();

    public static Condition getInstance() {
        return fInstance;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Watcher watcher = game.getState().getWatchers().get("WindbriskHeightsAttackersWatcher", source.getControllerId());
        if (watcher != null && watcher.conditionMet()) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "you attacked with three or more creatures this turn";
    }
}
