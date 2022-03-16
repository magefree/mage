package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author TheElk801
 */
public class AttacksAloneControlledTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterPermanent filter;
    private final boolean setTargetPointer;

    public AttacksAloneControlledTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public AttacksAloneControlledTriggeredAbility(Effect effect, boolean optional) {
        this(effect, false, optional);
    }

    public AttacksAloneControlledTriggeredAbility(Effect effect, boolean setTargetPointer, boolean optional) {
        this(effect, StaticFilters.FILTER_CONTROLLED_A_CREATURE, setTargetPointer, optional);
    }

    public AttacksAloneControlledTriggeredAbility(Effect effect, FilterPermanent filter, boolean setTargetPointer, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
    }

    private AttacksAloneControlledTriggeredAbility(final AttacksAloneControlledTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public AttacksAloneControlledTriggeredAbility copy() {
        return new AttacksAloneControlledTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!game.getCombat().attacksAlone()) {
            return false;
        }
        Permanent permanent = game.getPermanent(game.getCombat().getAttackers().get(0));
        if (permanent == null || !filter.match(permanent, getSourceId(), getControllerId(), game)) {
            return false;
        }
        if (setTargetPointer) {
            this.getEffects().setTargetPointer(new FixedTarget(permanent, game));
        }
        return true;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever " + filter.getMessage() + " attacks alone, ";
    }
}
