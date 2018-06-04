
package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ManaAbility;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.events.GameEvent;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public class RowanKenrithEmblem extends Emblem {
    // Target player gets an emblem with "Whenever you activate an ability that isn't a mana ability, copy it. You may choose new targets for the copy."

    public RowanKenrithEmblem() {
        this.setName("Emblem Rowan Kenrith");
        this.getAbilities().add(new RowanKenrithEmblemTriggeredAbility());
    }
}

class RowanKenrithEmblemTriggeredAbility extends TriggeredAbilityImpl {

    public RowanKenrithEmblemTriggeredAbility() {
        super(Zone.COMMAND, new RowanKenrithEmblemCopyEffect(), false);
    }

    public RowanKenrithEmblemTriggeredAbility(final RowanKenrithEmblemTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            StackObject ability = game.getStack().getStackObject(event.getTargetId());
            if (ability != null && !(ability instanceof ManaAbility)) {
                this.getEffects().get(0).setTargetPointer(new FixedTarget(ability.getId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you activate an ability that isn't a mana ability, copy it. You may choose new targets for the copy.";
    }

    @Override
    public RowanKenrithEmblemTriggeredAbility copy() {
        return new RowanKenrithEmblemTriggeredAbility(this);
    }
}

class RowanKenrithEmblemCopyEffect extends OneShotEffect {

    public RowanKenrithEmblemCopyEffect() {
        super(Outcome.Copy);
        this.staticText = "copy it. You may choose new targets for the copy.";
    }

    public RowanKenrithEmblemCopyEffect(final RowanKenrithEmblemCopyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if (stackAbility != null) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                stackAbility.createCopyOnStack(game, source, source.getControllerId(), true);
                return true;
            }
        }
        return false;

    }

    @Override
    public RowanKenrithEmblemCopyEffect copy() {
        return new RowanKenrithEmblemCopyEffect(this);
    }
}
