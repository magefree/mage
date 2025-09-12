package mage.abilities.common;

import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 * @author North
 */
public class DiesCreatureTriggeredAbility extends TriggeredAbilityImpl {

    protected FilterPermanent filter;
    private SetTargetPointer setTargetPointer;

    public DiesCreatureTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, false);
    }

    public DiesCreatureTriggeredAbility(Effect effect, boolean optional, boolean another) {
        this(effect, optional, another, false);
    }

    public DiesCreatureTriggeredAbility(Effect effect, boolean optional, boolean another, boolean setTargetPointer) {
        this(effect, optional, new FilterCreaturePermanent(another ? "another creature" : "a creature"));
        if (another) {
            filter.add(AnotherPredicate.instance);
        }
        this.setTargetPointer = (setTargetPointer ? SetTargetPointer.PERMANENT : SetTargetPointer.NONE);
    }

    public DiesCreatureTriggeredAbility(Effect effect, boolean optional, FilterPermanent filter) {
        this(effect, optional, filter, false);
    }

    public DiesCreatureTriggeredAbility(Effect effect, boolean optional, FilterPermanent filter, boolean setTargetPointer) {
        this(Zone.BATTLEFIELD, effect, optional, filter, setTargetPointer);
    }

    public DiesCreatureTriggeredAbility(Effect effect, SetTargetPointer setTargetPointer) {
        this(Zone.BATTLEFIELD, effect, false, new FilterCreaturePermanent("a creature"), setTargetPointer);
    }

    public DiesCreatureTriggeredAbility(Zone zone, Effect effect, boolean optional, FilterPermanent filter, boolean setTargetPointer) {
        this(zone, effect, optional, filter, (setTargetPointer ? SetTargetPointer.PERMANENT : SetTargetPointer.NONE));
    }

    public DiesCreatureTriggeredAbility(Zone zone, Effect effect, boolean optional, FilterPermanent filter, SetTargetPointer setTargetPointer) {
        super(zone, effect, optional);
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
        setLeavesTheBattlefieldTrigger(true);
        setTriggerPhrase("Whenever " + CardUtil.addArticle(filter.getMessage()) + (filter.getMessage().startsWith("one or more") ? " die, " : " dies, "));
    }

    protected DiesCreatureTriggeredAbility(final DiesCreatureTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public DiesCreatureTriggeredAbility copy() {
        return new DiesCreatureTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (!zEvent.isDiesEvent() || !filter.match(zEvent.getTarget(), controllerId, this, game)) {
            return false;
        }
        getEffects().setValue("creatureDied", zEvent.getTarget());
        switch (setTargetPointer) {
            case PLAYER:
                this.getAllEffects().setTargetPointer(new FixedTarget(event.getPlayerId(), game));
                break;
            case PERMANENT:
                this.getAllEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
                break;
            case NONE:
                break;
            default:
                throw new IllegalArgumentException("Unsupported SetTargetPointer in DiesCreatureTriggeredAbility");
        }
        return true;
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject sourceObject, GameEvent event) {
        if (this.zone == Zone.BATTLEFIELD) {
            return TriggeredAbilityImpl.isInUseableZoneDiesTrigger(this, sourceObject, event, game);
        } else {
            return super.isInUseableZone(game, sourceObject, event);
        }
    }
}
