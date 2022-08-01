package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.AbilityWord;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class LandfallAbility extends TriggeredAbilityImpl {

    protected SetTargetPointer setTargetPointer;
    protected Permanent triggeringLand;

    public LandfallAbility(Effect effect) {
        this(effect, false);
    }

    public LandfallAbility(Effect effect, boolean optional) {
        this(Zone.BATTLEFIELD, effect, optional);
    }

    public LandfallAbility(Zone zone, Effect effect, boolean optional) {
        this(zone, effect, optional, SetTargetPointer.NONE);
    }

    public LandfallAbility(Zone zone, Effect effect, boolean optional, SetTargetPointer setTargetPointer) {
        super(zone, effect, optional);
        this.setTargetPointer = setTargetPointer;
        this.setAbilityWord(AbilityWord.LANDFALL);
        setTriggerPhrase("Whenever a land enters the battlefield under your control, ");
    }

    public LandfallAbility(final LandfallAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
        this.triggeringLand = ability.triggeringLand;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null
                && permanent.isLand(game)
                && permanent.isControlledBy(this.controllerId)) {
            triggeringLand = permanent;
            if (setTargetPointer == SetTargetPointer.PERMANENT) {
                for (Effect effect : getAllEffects()) {
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public LandfallAbility copy() {
        return new LandfallAbility(this);
    }

    public Permanent getTriggeringPermanent() {
        return triggeringLand;
    }
}
