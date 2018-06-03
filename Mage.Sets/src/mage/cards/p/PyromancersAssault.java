
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetAnyTarget;
import mage.watchers.common.CastSpellLastTurnWatcher;

/**
 *
 * @author fireshoes
 */
public final class PyromancersAssault extends CardImpl {

    public PyromancersAssault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{R}");

        // Whenever you cast your second spell each turn, Pyromancer's Assault deals 2 damage to any target.
       Ability ability = new PyromancersAssaultTriggeredAbility();
       ability.addTarget(new TargetAnyTarget());
       this.addAbility(ability, new CastSpellLastTurnWatcher());
    }

    public PyromancersAssault(final PyromancersAssault card) {
        super(card);
    }

    @Override
    public PyromancersAssault copy() {
        return new PyromancersAssault(this);
    }
}

class PyromancersAssaultTriggeredAbility extends TriggeredAbilityImpl {

    public PyromancersAssaultTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(2));
    }

    public PyromancersAssaultTriggeredAbility(final PyromancersAssaultTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PyromancersAssaultTriggeredAbility copy() {
        return new PyromancersAssaultTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(controllerId)) {
            CastSpellLastTurnWatcher watcher = (CastSpellLastTurnWatcher) game.getState().getWatchers().get(CastSpellLastTurnWatcher.class.getSimpleName());
            if (watcher != null && watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(event.getPlayerId()) == 2) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast your second spell each turn, {this} deals 2 damage to any target.";
    }
}
