

package mage.abilities.abilityword;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.AbilityWord;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * Constellation
 *
 * @author LevelX2
 */

public class ConstellationAbility extends TriggeredAbilityImpl {

    private final boolean thisOr;

    public ConstellationAbility(Effect effect) {
        this(effect, false);
    }

    public ConstellationAbility(Effect effect, boolean optional) {
        this(effect, optional, true);
    }

    public ConstellationAbility(Effect effect, boolean optional, boolean thisOr) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.thisOr = thisOr;
        setAbilityWord(AbilityWord.CONSTELLATION);
        setTriggerPhrase("Whenever " + (thisOr ? "{this} or another" : "an")
                + " enchantment enters the battlefield under your control, ");
    }

    public ConstellationAbility(final ConstellationAbility ability) {
        super(ability);
        this.thisOr = ability.thisOr;
    }

    @Override
    public ConstellationAbility copy() {
        return new ConstellationAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getPlayerId().equals(this.getControllerId())) {
            return false;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent != null && ((thisOr && permanent.getId().equals(getSourceId())) || permanent.isEnchantment(game));
    }
}
