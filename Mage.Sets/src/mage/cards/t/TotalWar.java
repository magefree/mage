
package mage.cards.t;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.common.AttackedOrBlockedThisCombatWatcher;

/**
 *
 * @author jeffwadsworth & emerald000 & L_J
 */
public final class TotalWar extends CardImpl {

    public TotalWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{R}");

        // Whenever a player attacks with one or more creatures, destroy all untapped non-Wall creatures that player controls that didn't attack, except for creatures the player hasn't controlled continuously since the beginning of the turn.
        this.addAbility(new TotalWarTriggeredAbility(), new AttackedOrBlockedThisCombatWatcher());
    }

    private TotalWar(final TotalWar card) {
        super(card);
    }

    @Override
    public TotalWar copy() {
        return new TotalWar(this);
    }
}

class TotalWarTriggeredAbility extends TriggeredAbilityImpl {

    public TotalWarTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TotalWarDestroyEffect());
        setTriggerPhrase("Whenever a player attacks with one or more creatures, ");
    }

    public TotalWarTriggeredAbility(final TotalWarTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TotalWarTriggeredAbility copy() {
        return new TotalWarTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return !game.getCombat().getAttackers().isEmpty();
    }
}

class TotalWarDestroyEffect extends OneShotEffect {

    TotalWarDestroyEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "destroy all untapped non-Wall creatures that player controls that didn't attack, except for creatures the player hasn't controlled continuously since the beginning of the turn";
    }

    TotalWarDestroyEffect(final TotalWarDestroyEffect effect) {
        super(effect);
    }

    @Override
    public TotalWarDestroyEffect copy() {
        return new TotalWarDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player activePlayer = game.getPlayer(game.getActivePlayerId());
        if (activePlayer == null) {
            return false;
        }

        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(activePlayer.getId())) {
            // Noncreature cards are safe.
            if (!permanent.isCreature(game)) {
                continue;
            }
            // Tapped cards are safe.
            if (permanent.isTapped()) {
                continue;
            }
            // Walls are safe.
            if (permanent.hasSubtype(SubType.WALL, game)) {
                continue;
            }
            // Creatures that attacked are safe.
            AttackedOrBlockedThisCombatWatcher watcher = game.getState().getWatcher(AttackedOrBlockedThisCombatWatcher.class);
            if (watcher != null
                && watcher.getAttackedThisTurnCreatures().contains(new MageObjectReference(permanent, game))) {
                continue;
            }
            // Creatures that weren't controlled since the beginning of turn are safe.
            if (!permanent.wasControlledFromStartOfControllerTurn()) {
                continue;
            }
            // Destroy the rest.
            permanent.destroy(source, game, false);
        }
        return true;
    }
}
