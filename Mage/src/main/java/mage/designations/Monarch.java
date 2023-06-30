package mage.designations;

import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.BecomesMonarchTargetEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author LevelX2
 */
public class Monarch extends Designation {

    public Monarch() {
        super(DesignationType.THE_MONARCH);
        addAbility(new MonarchDrawTriggeredAbility());
        addAbility(new MonarchDealsCombatDamageToAPlayerTriggeredAbility());
    }

    private Monarch(final Monarch monarch) {
        super(monarch);
    }

    @Override
    public Monarch copy() {
        return new Monarch(this);
    }
}

// At the beginning of the monarch's end step, that player draws a card
class MonarchDrawTriggeredAbility extends BeginningOfEndStepTriggeredAbility {

    public MonarchDrawTriggeredAbility() {
        super(Zone.ALL, new DrawCardTargetEffect(1), TargetController.ANY, null, false);
    }

    public MonarchDrawTriggeredAbility(final MonarchDrawTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getMonarchId() != null && event.getPlayerId().equals(game.getMonarchId())) {
            setControllerId(game.getMonarchId());
            getEffects().get(0).setTargetPointer(new FixedTarget(game.getMonarchId()));
            return true;
        }
        return false;
    }

    @Override
    public MonarchDrawTriggeredAbility copy() {
        return new MonarchDrawTriggeredAbility(this);
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject source, GameEvent event) {
        return true;
    }

    @Override
    public String getRule() {
        return "At the beginning of the monarch's end step, that player draws a card.";
    }
}

// Whenever a creature deals combat damage to the monarch, its controller becomes the monarch.
class MonarchDealsCombatDamageToAPlayerTriggeredAbility extends TriggeredAbilityImpl {

    public MonarchDealsCombatDamageToAPlayerTriggeredAbility() {
        super(Zone.ALL, new BecomesMonarchTargetEffect(), false);
    }

    public MonarchDealsCombatDamageToAPlayerTriggeredAbility(final MonarchDealsCombatDamageToAPlayerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((DamagedPlayerEvent) event).isCombatDamage()) {
            MageObject damagingObject = game.getObject(event.getSourceId());
            if (damagingObject instanceof Permanent
                    && damagingObject.isCreature(game)
                    && event.getTargetId().equals(game.getMonarchId())) {
                setControllerId(event.getPlayerId());
                getEffects().get(0).setTargetPointer(new FixedTarget(((Permanent) damagingObject).getControllerId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public MonarchDealsCombatDamageToAPlayerTriggeredAbility copy() {
        return new MonarchDealsCombatDamageToAPlayerTriggeredAbility(this);
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject source, GameEvent event) {
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever a creature deals combat damage to the monarch, its controller becomes the monarch.";
    }

}
