
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class EntersBattlefieldTriggeredAbility extends TriggeredAbilityImpl {

    protected String rulePrefix;
    protected boolean noRule;

    public EntersBattlefieldTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public EntersBattlefieldTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, null);
    }

    public EntersBattlefieldTriggeredAbility(Effect effect, boolean optional, boolean noRule) {
        this(effect, optional, null);
        this.noRule = noRule;
    }

    public EntersBattlefieldTriggeredAbility(Effect effect, boolean optional, String rulePrefix) {
        super(Zone.ALL, effect, optional); // Zone.All because a creature with trigger can be put into play and be sacrificed during the resolution of an effect (discard Obstinate Baloth with Smallpox)
        this.rulePrefix = rulePrefix;
    }

    public EntersBattlefieldTriggeredAbility(final EntersBattlefieldTriggeredAbility ability) {
        super(ability);
        this.rulePrefix = ability.rulePrefix;
        this.noRule = ability.noRule;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(getSourceId());
    }

    @Override
    public String getRule() {
        if (noRule) {
            return super.getRule();
        }
        return (rulePrefix != null ? rulePrefix : "") + "When {this} enters the battlefield, " + super.getRule();
    }

    @Override
    public EntersBattlefieldTriggeredAbility copy() {
        return new EntersBattlefieldTriggeredAbility(this);
    }
}
