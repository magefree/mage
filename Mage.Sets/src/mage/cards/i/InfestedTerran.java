package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifeDefendingPlayerEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 *
 * @author NinthWorld
 */
public final class InfestedTerran extends CardImpl {

    public InfestedTerran(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        
        this.subtype.add(SubType.ZERG);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Infested Terran dies during combat, if it was attacking, defending player loses 3 life.
        this.addAbility(new InfestedTerranAbility(), new InfestedTerranWatcher());
    }

    public InfestedTerran(final InfestedTerran card) {
        super(card);
    }

    @Override
    public InfestedTerran copy() {
        return new InfestedTerran(this);
    }
}

class InfestedTerranAbility extends ZoneChangeTriggeredAbility {

    public InfestedTerranAbility() {
        super(Zone.BATTLEFIELD, Zone.GRAVEYARD, new InfestedTerranEffect(), "When {this} dies during combat, if it was attacking, ", false);
    }

    public InfestedTerranAbility(final InfestedTerranAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            return game.getPhase().getType() == TurnPhase.COMBAT;
        }
        return false;
    }

    @Override
    public InfestedTerranAbility copy() {
        return new InfestedTerranAbility(this);
    }
}

class InfestedTerranEffect extends OneShotEffect {

    public InfestedTerranEffect() {
        super(Outcome.LoseLife);
        staticText = "defending player loses 3 life";
    }

    public InfestedTerranEffect(final InfestedTerranEffect ability) {
        super(ability);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        InfestedTerranWatcher watcher = (InfestedTerranWatcher) game.getState().getWatchers().get(InfestedTerranWatcher.class.getSimpleName(), source.getSourceId());
        if(watcher != null && watcher.attackedThisTurn) {
            if(watcher.defender != null) {
                watcher.defender.loseLife(3, game, false);
            }
        }
        return false;
    }

    @Override
    public InfestedTerranEffect copy() {
        return new InfestedTerranEffect(this);
    }
}

class InfestedTerranWatcher extends Watcher {

    public boolean attackedThisTurn = false;
    public Player defender = null;

    public InfestedTerranWatcher() {
        super(InfestedTerranWatcher.class.getSimpleName(), WatcherScope.CARD);
    }

    public InfestedTerranWatcher(final InfestedTerranWatcher watcher) {
        super(watcher);
        this.attackedThisTurn = watcher.attackedThisTurn;
        this.defender = watcher.defender;
    }

    @Override
    public InfestedTerranWatcher copy() {
        return new InfestedTerranWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if(event.getType() == GameEvent.EventType.ATTACKER_DECLARED && event.getSourceId().equals(sourceId)) {
            attackedThisTurn = true;
            defender = game.getPlayer(game.getCombat().getDefendingPlayerId(sourceId, game));
        }
    }

    @Override
    public void reset() {
        super.reset();
        attackedThisTurn = false;
        defender = null;
    }
}