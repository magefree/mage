package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

/**
 * @author nantuko, loki
 */
public class PutIntoGraveFromBattlefieldSourceTriggeredAbility extends TriggeredAbilityImpl {

    private static final String staticTriggerPhrase = "When {this} is put into a graveyard from the battlefield, ";
    private static final String staticTriggerPhraseOnlyController = "When {this} is put into your graveyard from the battlefield, ";
    private final boolean onlyToControllerGraveyard;

    public PutIntoGraveFromBattlefieldSourceTriggeredAbility(Effect effect) {
        this(effect, false, false);
    }

    public PutIntoGraveFromBattlefieldSourceTriggeredAbility(Effect effect, boolean optional, boolean onlyToControllerGraveyard) {
        super(Zone.ALL, effect, optional);
        setLeavesTheBattlefieldTrigger(true);
        this.onlyToControllerGraveyard = onlyToControllerGraveyard;
    }

    public PutIntoGraveFromBattlefieldSourceTriggeredAbility(final PutIntoGraveFromBattlefieldSourceTriggeredAbility ability) {
        super(ability);
        this.onlyToControllerGraveyard = ability.onlyToControllerGraveyard;
    }

    @Override
    public PutIntoGraveFromBattlefieldSourceTriggeredAbility copy() {
        return new PutIntoGraveFromBattlefieldSourceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(getSourceId())) {
            return false;
        }
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        Permanent permanent = zEvent.getTarget();
        if (permanent == null || !zEvent.isDiesEvent()
                || (onlyToControllerGraveyard && !this.isControlledBy(game.getOwnerId(zEvent.getTargetId())))) {
            return false;
        }
        this.getEffects().setValue("permanentWasCreature", permanent.isCreature(game));
        return true;
    }

    @Override
    public String getStaticTriggerPhrase() {
        return onlyToControllerGraveyard ? staticTriggerPhraseOnlyController : staticTriggerPhrase;
    }
}
