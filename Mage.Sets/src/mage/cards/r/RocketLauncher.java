
package mage.cards.r;

import java.util.Objects;
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
import mage.target.common.TargetAnyTarget;
import mage.watchers.Watcher;

/**
 *
 * @author MarcoMarin
 */
public final class RocketLauncher extends CardImpl {

    public RocketLauncher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        Watcher watcher = new RocketLauncherWatcher(this.getId());        
        // {2}: Rocket Launcher deals 1 damage to any target. Destroy Rocket Launcher at the beginning of the next end step. Activate this ability only if you've controlled Rocket Launcher continuously since the beginning of your most recent turn.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD,
                                new DamageTargetEffect(1), new GenericManaCost(2), ControlledTurnCondition.instance);
        ability.addTarget(new TargetAnyTarget());
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
        super(RocketLauncherWatcher.class.getSimpleName(), WatcherScope.GAME);
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
                Objects.equals(event.getSourceId(), cardId)) {
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

enum ControlledTurnCondition implements Condition {

    instance;

    
    @Override
    public boolean apply(Game game, Ability source) {
        RocketLauncherWatcher watcher = (RocketLauncherWatcher) game.getState().getWatchers().get(RocketLauncherWatcher.class.getSimpleName());
        
        return !watcher.changedControllerOR1stTurn;
    }

    @Override
    public String toString() {
        return "Permanent hasn't changed controller NOR entered this turn";
    }

    
}