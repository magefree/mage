package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

public class AttacksOrBlocksTriggeredAbility extends TriggeredAbilityImpl {

    private static final String staticTriggerPhraseDelayed = "When {this} attacks or blocks, ";
    private static final String staticTriggerPhraseNonDelayed = "Whenever {this} attacks or blocks, ";
    private final boolean delayed;

    public AttacksOrBlocksTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.delayed = effect instanceof CreateDelayedTriggeredAbilityEffect;
    }

    public AttacksOrBlocksTriggeredAbility(final AttacksOrBlocksTriggeredAbility ability) {
        super(ability);
        this.delayed = ability.delayed;
    }

    @Override
    public AttacksOrBlocksTriggeredAbility copy() {
        return new AttacksOrBlocksTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED || event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getSourceId().equals(this.getSourceId());
    }

    @Override
    public String getStaticTriggerPhrase() {
        return this.delayed ? staticTriggerPhraseDelayed : staticTriggerPhraseNonDelayed;
    }
}
