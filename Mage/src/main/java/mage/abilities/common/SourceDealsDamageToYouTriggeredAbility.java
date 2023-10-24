package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author xenohedron
 */
public class SourceDealsDamageToYouTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterPermanent filter;

    /**
     * Whenever a source an opponent controls deals damage to you, effect
     */
    public SourceDealsDamageToYouTriggeredAbility(Effect effect, boolean optional) {
        this(effect, null, optional);
    }

    /**
     * Whenever a source an opponent controls deals damage to you or a [filter] you control, effect
     */
    public SourceDealsDamageToYouTriggeredAbility(Effect effect, FilterPermanent filter, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
        if (filter != null) {
            setTriggerPhrase("Whenever a source an opponent controls deals damage to you or a " + filter.getMessage() + " you control, ");
        } else {
            setTriggerPhrase("Whenever a source an opponent controls deals damage to you, ");
        }
    }

    protected SourceDealsDamageToYouTriggeredAbility(final SourceDealsDamageToYouTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
    }

    @Override
    public SourceDealsDamageToYouTriggeredAbility copy() {
        return new SourceDealsDamageToYouTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGED_PLAYER:
            case DAMAGED_PERMANENT:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGED_PLAYER:
                if (!this.isControlledBy(event.getTargetId())) {
                    return false;
                }
                break;
            case DAMAGED_PERMANENT:
                if (filter == null) {
                    return false;
                }
                Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
                if (permanent == null || !permanent.isControlledBy(this.getControllerId())) {
                    return false;
                }
                if (!filter.match(permanent, this.getControllerId(), this, game)) {
                    return false;
                }
                break;
            default:
                return false;
        }
        int damageAmount = event.getAmount();
        if (damageAmount < 1) {
            return false;
        }
        this.getAllEffects().setValue("damage", damageAmount);
        this.getAllEffects().setTargetPointer(new FixedTarget(game.getControllerId(event.getSourceId())));
        return true;
    }
}
