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
    }

    public DealsDamageToACreatureAllTriggeredAbility(final DealsDamageToACreatureAllTriggeredAbility ability) {
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
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null || !permanent.isCreature(game)) {
            return false;
        }
        if (combatOnly && !((DamagedEvent) event).isCombatDamage()) {
            return false;
        }
        permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if (!filterPermanent.match(permanent, getControllerId(), this, game)) {
            return false;
        }
        this.getEffects().setValue("damage", event.getAmount());
        this.getEffects().setValue("sourceId", event.getSourceId());
        switch (setTargetPointer) {
            case PLAYER:
                this.getEffects().setTargetPointer(new FixedTarget(permanent.getControllerId()));
                break;
            case PERMANENT:
                this.getEffects().setTargetPointer(new FixedTarget(permanent, game));
                break;
            case PERMANENT_TARGET:
                Permanent permanent_target = game.getPermanentOrLKIBattlefield(event.getTargetId());
                if (permanent_target != null) {
                    this.getEffects().setTargetPointer(new FixedTarget(permanent_target, game));
                }
                break;
        }
        return true;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever " + filterPermanent.getMessage() + " deals "
                + (combatOnly ? "combat " : "") + "damage to a creature, ";
    }
}
