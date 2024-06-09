package mage.abilities.common;

import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.common.PermanentWasCastWatcher;

/**
 * An extension of triggered abilities that trigger when permanents enter the
 * battlefield under your control, but this time they either must be cast or must not be cast.
 *
 * @author alexander-novo
 */
public class EntersBattlefieldCastTriggeredAbility extends EntersBattlefieldControlledTriggeredAbility {
    private final boolean mustCast;

    public EntersBattlefieldCastTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean optional,
                                                 SetTargetPointer setTargetPointer, boolean mustCast) {
        super(zone, effect, filter, optional, setTargetPointer);
        this.mustCast = mustCast;
        this.addWatcher(new PermanentWasCastWatcher());
        setTriggerPhrase(getTriggerPhraseFromFilter() + " under your control, if it " + (mustCast ? "was" : "wasn't") + " cast, " );
    }

    protected EntersBattlefieldCastTriggeredAbility(final EntersBattlefieldCastTriggeredAbility ability) {
        super(ability);
        this.mustCast = ability.mustCast;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        PermanentWasCastWatcher watcher = game.getState().getWatcher(PermanentWasCastWatcher.class);
        return watcher != null && watcher.wasPermanentCastThisTurn(event.getTargetId()) == this.mustCast
                && super.checkTrigger(event, game);
    }

    @Override
    public EntersBattlefieldCastTriggeredAbility copy() {
        return new EntersBattlefieldCastTriggeredAbility(this);
    }
}
