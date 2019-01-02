package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.events.GameEvent;
import mage.game.stack.StackAbility;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class RowanKenrithEmblem extends Emblem {
    // Target player gets an emblem with "Whenever you activate an ability that isn't a mana ability, copy it. You may choose new targets for the copy."

    public RowanKenrithEmblem() {
        this.setName("Emblem Rowan Kenrith");
        this.getAbilities().add(new RowanKenrithEmblemTriggeredAbility());
    }
}

class RowanKenrithEmblemTriggeredAbility extends TriggeredAbilityImpl {

    RowanKenrithEmblemTriggeredAbility() {
        super(Zone.COMMAND, new RowanKenrithEmblemEffect(), false);
    }

    RowanKenrithEmblemTriggeredAbility(final RowanKenrithEmblemTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RowanKenrithEmblemTriggeredAbility copy() {
        return new RowanKenrithEmblemTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(getControllerId())) {
            StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getSourceId());
            if (stackAbility != null
                    && !(stackAbility.getStackAbility() instanceof ActivatedManaAbilityImpl)) {
                game.getState().setValue("rowanStackAbility", stackAbility);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you activate an ability that isn't a mana ability, copy it. You may choose new targets for the copy.";
    }
}

class RowanKenrithEmblemEffect extends OneShotEffect {

    RowanKenrithEmblemEffect() {
        super(Outcome.Benefit);
        this.staticText = ", copy it. You may choose new targets for the copy.";
    }

    RowanKenrithEmblemEffect(final RowanKenrithEmblemEffect effect) {
        super(effect);
    }

    @Override
    public RowanKenrithEmblemEffect copy() {
        return new RowanKenrithEmblemEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        StackAbility ability = (StackAbility) game.getState().getValue("rowanStackAbility");
        Player controller = game.getPlayer(source.getControllerId());
        if (ability != null
                && controller != null) {
            ability.createCopyOnStack(game, source, source.getControllerId(), true);
            game.informPlayers(source.getSourceObjectIfItStillExists(game).getName() + " : " + controller.getLogName() + " copied activated ability");
            return true;
        }
        return false;
    }
}
