package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
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

    public DealsDamageToYouAllTriggeredAbility(FilterPermanent filter, Effect effect) {
        this(Zone.BATTLEFIELD, filter, effect, false, false);
    }

    public DealsDamageToYouAllTriggeredAbility(Zone zone, FilterPermanent filter, Effect effect, boolean optional, boolean onlyCombat) {
        super(zone, effect, optional);
        this.filter = filter;
        this.onlyCombat = onlyCombat;
        setTriggerPhrase("Whenever " + CardUtil.addArticle(filter.getMessage()) + " deals " + (onlyCombat ? "combat " : "") + "damage to you, ");
    }

    protected DealsDamageToYouAllTriggeredAbility(final DealsDamageToYouAllTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.onlyCombat = ability.onlyCombat;
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
        this.getEffects().setTargetPointer(new FixedTarget(permanent, game));
        return true;
    }
}
