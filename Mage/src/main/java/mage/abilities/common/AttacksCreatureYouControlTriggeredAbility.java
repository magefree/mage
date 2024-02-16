package mage.abilities.common;

import mage.MageObjectReference;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 * @author noxx
 */
public class AttacksCreatureYouControlTriggeredAbility extends TriggeredAbilityImpl {

    protected final FilterPermanent filter;
    protected final boolean setTargetPointer;
    protected boolean once = false;

    public AttacksCreatureYouControlTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public AttacksCreatureYouControlTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, StaticFilters.FILTER_CONTROLLED_CREATURE);
    }

    public AttacksCreatureYouControlTriggeredAbility(Effect effect, boolean optional, boolean setTargetPointer) {
        this(effect, optional, StaticFilters.FILTER_CONTROLLED_CREATURE, setTargetPointer);
    }

    public AttacksCreatureYouControlTriggeredAbility(Effect effect, boolean optional, FilterPermanent filter) {
        this(effect, optional, filter, false);
    }

    public AttacksCreatureYouControlTriggeredAbility(Effect effect, boolean optional, FilterPermanent filter, boolean setTargetPointer) {
        this(Zone.BATTLEFIELD, effect, optional, filter, setTargetPointer);
    }

    public AttacksCreatureYouControlTriggeredAbility(Zone zone, Effect effect, boolean optional, FilterPermanent filter, boolean setTargetPointer) {
        super(zone, effect, optional);
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase("Whenever " + CardUtil.addArticle(filter.getMessage()) + " attacks, ");
    }

    private AttacksCreatureYouControlTriggeredAbility(final AttacksCreatureYouControlTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.setTargetPointer = ability.setTargetPointer;
        this.once = ability.once;
    }

    public void setOnce(boolean once) {
        this.once = once;
        setTriggerPhrase("When" + (once ? " " : "ever ") + CardUtil.addArticle(filter.getMessage()) + " attacks, ");
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent sourcePermanent = game.getPermanent(event.getSourceId());
        if (filter.match(sourcePermanent, controllerId, this, game)) {
            if (setTargetPointer) {
                this.getEffects().setTargetPointer(new FixedTarget(event.getSourceId(), game));
            }
            this.getEffects().setValue("attackerRef", new MageObjectReference(sourcePermanent, game));
            return true;
        }
        return false;
    }

    @Override
    public AttacksCreatureYouControlTriggeredAbility copy() {
        return new AttacksCreatureYouControlTriggeredAbility(this);
    }
}
