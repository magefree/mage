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
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreatureOrPlayer;
import mage.watchers.Watcher;

/**
 *
 * @author MarcoMarin
 */
public class RocketLauncher extends CardImpl {

    public RocketLauncher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        Watcher watcher = new RocketLauncherWatcher(this.getId());        
        // {2}: Rocket Launcher deals 1 damage to target creature or player. Destroy Rocket Launcher at the beginning of the next end step. Activate this ability only if you've controlled Rocket Launcher continuously since the beginning of your most recent turn.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD,
                                new DamageTargetEffect(1), new GenericManaCost(2), new ControlledTurnCondition());
        ability.addTarget(new TargetCreatureOrPlayer());
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new DestroySourceEffect(true))));

        this.addAbility(ability, watcher);
    }

    public RocketLauncher(final RocketLauncher card) {
        super(card);
    }

    @Override
    public RocketLauncher copy() {
        return new RocketLauncher(this);
    }
}

class RocketLauncherWatcher extends Watcher {

    boolean changedControllerOR1stTurn;
    UUID cardId = null;

    public RocketLauncherWatcher(UUID cardId) {
        super("RocketLauncherWatcher", WatcherScope.GAME);
        this.changedControllerOR1stTurn = true;
        this.cardId = cardId;
    }

    public RocketLauncherWatcher(final RocketLauncherWatcher watcher) {
        super(watcher);        
        this.changedControllerOR1stTurn = watcher.changedControllerOR1stTurn;
        this.cardId = watcher.cardId;
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.CLEANUP_STEP_POST) {            
            changedControllerOR1stTurn = false;
        }
        if (event.getType() == GameEvent.EventType.LOST_CONTROL &&
                event.getSourceId()==cardId) {            
            changedControllerOR1stTurn = true;
        }
    }

    @Override
    public RocketLauncherWatcher copy() {
        return new RocketLauncherWatcher(this);
    }

    @Override
    public void reset() {
        super.reset();
        changedControllerOR1stTurn = true; //when is this reset called? may cause problems if in mid-life
    }
}

class ControlledTurnCondition implements Condition {

    private static final ControlledTurnCondition fInstance = new ControlledTurnCondition();

    public static ControlledTurnCondition getInstance() {
        return fInstance;
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        RocketLauncherWatcher watcher = (RocketLauncherWatcher) game.getState().getWatchers().get("RocketLauncherWatcher");        
        
        return !watcher.changedControllerOR1stTurn;
    }

    @Override
    public String toString() {
        return "Permanent hasn't changed controller NOR entered this turn";
    }

    
}