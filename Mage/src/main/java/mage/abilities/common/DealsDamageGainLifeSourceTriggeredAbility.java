

package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */

public class DealsDamageGainLifeSourceTriggeredAbility extends TriggeredAbilityImpl {

    public DealsDamageGainLifeSourceTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainThatMuchLifeEffect(), false);
    }

    public DealsDamageGainLifeSourceTriggeredAbility(final DealsDamageGainLifeSourceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DealsDamageGainLifeSourceTriggeredAbility copy() {
        return new DealsDamageGainLifeSourceTriggeredAbility(this);
    }
    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT
                || event.getType() ==  GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            for (Effect effect : this.getEffects()) {
                effect.setValue("damage", event.getAmount());
            }
            return true;
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever {this} deals damage, " ;
    }
}

class GainThatMuchLifeEffect extends OneShotEffect {

    public GainThatMuchLifeEffect() {
        super(Outcome.GainLife);
        this.staticText = "you gain that much life";
    }

    public GainThatMuchLifeEffect(final GainThatMuchLifeEffect effect) {
        super(effect);
    }

    @Override
    public GainThatMuchLifeEffect copy() {
        return new GainThatMuchLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int amount = (Integer) getValue("damage");
            if (amount > 0) {
                controller.gainLife(amount, game, source);

            }
            return true;
        }
        return false;
    }
}
