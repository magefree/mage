package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class EntersBattlefieldTriggeredAbility extends TriggeredAbilityImpl {

    public EntersBattlefieldTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public EntersBattlefieldTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.ALL, effect, optional); // Zone.All because a creature with trigger can be put into play and be sacrificed during the resolution of an effect (discard Obstinate Baloth with Smallpox)
        this.replaceRuleText = true; // default true to replace "{this}" with "it"
        setTriggerPhrase("When {this} enters the battlefield, ");
    }

    protected EntersBattlefieldTriggeredAbility(final EntersBattlefieldTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(getSourceId())) {
            this.getEffects().setValue("permanentEnteredBattlefield", game.getPermanent(event.getTargetId()));
            return true;
        }
        return false;
    }

    @Override
    public EntersBattlefieldTriggeredAbility copy() {
        return new EntersBattlefieldTriggeredAbility(this);
    }
}
