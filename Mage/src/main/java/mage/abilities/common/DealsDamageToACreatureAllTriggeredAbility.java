package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author LevelX2
 */
public class DealsDamageToACreatureAllTriggeredAbility extends TriggeredAbilityImpl {

    private final boolean combatOnly;
    private final FilterPermanent filterPermanent;
    private final SetTargetPointer setTargetPointer;

    /**
     * This ability works only for permanents doing damage.
     *
     * @param effect
     * @param optional
     * @param filterPermanent  The filter that restricts which permanets have to
     *                         trigger
     * @param setTargetPointer The target to be set to target pointer of the
     *                         effect.<br>
     *                         - PLAYER = player controlling the damage source.<br>
     *                         - PERMANENT = source permanent.<br>
     *                         - PERMANENT_TARGET = damaged creature.
     * @param combatOnly       The flag to determine if only combat damage has
     *                         to trigger
     */
    public DealsDamageToACreatureAllTriggeredAbility(Effect effect, boolean optional, FilterPermanent filterPermanent, SetTargetPointer setTargetPointer, boolean combatOnly) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.combatOnly = combatOnly;
        this.setTargetPointer = setTargetPointer;
        this.filterPermanent = filterPermanent;
        setTriggerPhrase("Whenever " + filterPermanent.getMessage() + " deals "
                + (combatOnly ? "combat " : "") + "damage to a creature, ");
    }

    protected DealsDamageToACreatureAllTriggeredAbility(final DealsDamageToACreatureAllTriggeredAbility ability) {
        super(ability);
        this.combatOnly = ability.combatOnly;
        this.filterPermanent = ability.filterPermanent;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public DealsDamageToACreatureAllTriggeredAbility copy() {
        return new DealsDamageToACreatureAllTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (combatOnly && !((DamagedEvent) event).isCombatDamage()) {
            return false;
        }
        Permanent permanentDealtDamage = game.getPermanent(event.getTargetId());
        if (permanentDealtDamage == null || !permanentDealtDamage.isCreature(game)) {
            return false;
        }
        Permanent permanentDealingDamage = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if (!filterPermanent.match(permanentDealingDamage, getControllerId(), this, game)) {
            return false;
        }
        int damageAmount = event.getAmount();
        if (damageAmount < 1) {
            return false;
        }
        this.getEffects().setValue("damage", damageAmount);
        this.getEffects().setValue("sourceId", event.getSourceId());
        switch (setTargetPointer) {
            case PLAYER:
                this.getEffects().setTargetPointer(new FixedTarget(permanentDealingDamage.getControllerId()));
                break;
            case PERMANENT:
                this.getEffects().setTargetPointer(new FixedTarget(permanentDealingDamage, game));
                break;
            case PERMANENT_TARGET:
                this.getEffects().setTargetPointer(new FixedTarget(permanentDealtDamage, game));
                break;
        }
        return true;
    }
}
