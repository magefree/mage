package mage.abilities.common;

import java.util.UUID;

import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.common.PermanentWasCastWatcher;

/**
 * An extension of triggered abilities that trigger when permanents enter the
 * battlefield, but this time they either must be cast or must not be cast.
 * 
 * @author alexander-novo
 */
public class EntersBattlefieldCastTriggeredAbility extends EntersBattlefieldAllTriggeredAbility {
    private final boolean mustCast;

    /**
     * zone = BATTLEFIELD optional = false
     *
     * @param effect
     * @param filter
     */
    public EntersBattlefieldCastTriggeredAbility(Effect effect, FilterPermanent filter, boolean mustCast) {
        this(Zone.BATTLEFIELD, effect, filter, mustCast, false);
    }

    public EntersBattlefieldCastTriggeredAbility(Effect effect, FilterPermanent filter, boolean mustCast, String rule) {
        this(Zone.BATTLEFIELD, effect, filter, mustCast, false, rule);
    }

    public EntersBattlefieldCastTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean mustCast,
            boolean optional) {
        this(zone, effect, filter, mustCast, optional, SetTargetPointer.NONE, null, false);
    }

    public EntersBattlefieldCastTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean mustCast,
            boolean optional,
            String rule) {
        this(zone, effect, filter, mustCast, optional, rule, false);
    }

    public EntersBattlefieldCastTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean mustCast,
            boolean optional,
            String rule, boolean controlledText) {
        this(zone, effect, filter, mustCast, optional, SetTargetPointer.NONE, rule, controlledText);
    }

    public EntersBattlefieldCastTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean mustCast,
            boolean optional,
            SetTargetPointer setTargetPointer, String rule) {
        this(zone, effect, filter, mustCast, optional, setTargetPointer, rule, false);
    }

    public EntersBattlefieldCastTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean mustCast,
            boolean optional,
            SetTargetPointer setTargetPointer, String rule, boolean controlledText) {
        super(zone, effect, filter, optional, setTargetPointer, rule, controlledText);

        this.mustCast = mustCast;
        this.addWatcher(new PermanentWasCastWatcher());

        StringBuilder triggerPhrase = new StringBuilder(this.generateTriggerPhrase());
        if (mustCast) {
            triggerPhrase.append("if it was cast, ");
        } else {
            triggerPhrase.append("if it wasn't cast, ");
        }
        this.setTriggerPhrase(triggerPhrase.toString());
    }

    public EntersBattlefieldCastTriggeredAbility(final EntersBattlefieldCastTriggeredAbility ability) {
        super(ability);

        this.mustCast = ability.mustCast;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game))
            return false;

        PermanentWasCastWatcher watcher = game.getState().getWatcher(PermanentWasCastWatcher.class);
        UUID targetId = event.getTargetId();

        return watcher.wasPermanentCastThisTurn(targetId) == this.mustCast;
    }

    @Override
    public EntersBattlefieldCastTriggeredAbility copy() {
        return new EntersBattlefieldCastTriggeredAbility(this);
    }
}
