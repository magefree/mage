package mage.abilities.common;

import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.Optional;

/**
 * @author TheElk801
 */
public class AttacksWhileSaddledTriggeredAbility extends AttacksTriggeredAbility {

    public AttacksWhileSaddledTriggeredAbility(Effect effect) {
        super(effect);
        this.setTriggerPhrase("Whenever {this} attacks while saddled, ");
    }

    private AttacksWhileSaddledTriggeredAbility(final AttacksWhileSaddledTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AttacksWhileSaddledTriggeredAbility copy() {
        return new AttacksWhileSaddledTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return super.checkTrigger(event, game)
                && Optional
                .ofNullable(getSourcePermanentIfItStillExists(game))
                .map(Permanent::isSaddled)
                .orElse(false);
    }
}
