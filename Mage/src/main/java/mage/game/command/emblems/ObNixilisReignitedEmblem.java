
package mage.game.command.emblems;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author spjspj
 */
public final class ObNixilisReignitedEmblem extends Emblem {

    public ObNixilisReignitedEmblem() {
        setName("Emblem Nixilis");

        this.getAbilities().add(new ObNixilisEmblemTriggeredAbility(new LoseLifeSourceControllerEffect(2), false));
        this.setExpansionSetCodeForImage("BFZ");
    }
}

class ObNixilisEmblemTriggeredAbility extends TriggeredAbilityImpl {

    public ObNixilisEmblemTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.COMMAND, effect, optional);
    }

    public ObNixilisEmblemTriggeredAbility(final ObNixilisEmblemTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DREW_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId() != null;
    }

    @Override
    public String getRule() {
        return "Whenever a player draws a card, you lose 2 life.";
    }

    @Override
    public ObNixilisEmblemTriggeredAbility copy() {
        return new ObNixilisEmblemTriggeredAbility(this);
    }
}
