package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 * @author xenohedron
 */
public class DealsDamageToYouAllTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterPermanent filter;
    private final boolean onlyCombat;
    private final SetTargetPointer setTargetPointer;

    public DealsDamageToYouAllTriggeredAbility(FilterPermanent filter, Effect effect, boolean onlyCombat) {
        this(Zone.BATTLEFIELD, filter, effect, false, onlyCombat, SetTargetPointer.PERMANENT);
    }

    public DealsDamageToYouAllTriggeredAbility(Zone zone, FilterPermanent filter, Effect effect,
                                               boolean optional, boolean onlyCombat, SetTargetPointer setTargetPointer) {
        super(zone, effect, optional);
        this.filter = filter;
        this.onlyCombat = onlyCombat;
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase("Whenever " + CardUtil.addArticle(filter.getMessage()) + " deals " + (onlyCombat ? "combat " : "") + "damage to you, ");
    }

    protected DealsDamageToYouAllTriggeredAbility(final DealsDamageToYouAllTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.onlyCombat = ability.onlyCombat;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public DealsDamageToYouAllTriggeredAbility copy() {
        return new DealsDamageToYouAllTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (onlyCombat && !((DamagedPlayerEvent) event).isCombatDamage()) {
            return false;
        }
        if (event.getTargetId() != getControllerId()) {
            return false;
        }
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (!filter.match(permanent, getControllerId(), this, game)) {
            return false;
        }
        this.getEffects().setValue("damage", event.getAmount());
        switch (setTargetPointer) {
            case NONE:
                break;
            case PERMANENT:
                this.getEffects().setTargetPointer(new FixedTarget(permanent, game));
                break;
            case PLAYER:
                this.getEffects().setTargetPointer(new FixedTarget(permanent.getControllerId()));
                break;
            default:
                throw new UnsupportedOperationException("SetTargetPointer not handled in DealsDamageToYouAllTriggeredAbility " + setTargetPointer);
        }
        return true;
    }
}
