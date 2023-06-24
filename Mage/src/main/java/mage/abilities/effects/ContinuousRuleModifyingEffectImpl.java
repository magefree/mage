
package mage.abilities.effects;

import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.Duration;
import mage.constants.EffectType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */
public abstract class ContinuousRuleModifyingEffectImpl extends ContinuousEffectImpl implements ContinuousRuleModifyingEffect {

    protected final boolean messageToUser;
    protected final boolean messageToGameLog;
    protected final String infoMessage;

    // 613.10
    // Some continuous effects affect game rules rather than objects. For example, effects may modify
    // a player's maximum hand size, or say that a creature must attack this turn if able. These effects
    // are applied after all other continuous effects have been applied. Continuous effects that affect
    // the costs of spells or abilities are applied according to the order specified in rule 601.2e.
    // All other such effects are applied in timestamp order. See also the rules for timestamp order
    // and dependency (rules 613.6 and 613.7).
    // Some of this rule modifying effects are implemented as normal CONTINUOUS effects using the Layer.RulesEffects.
    // But if the rule change can be implemented simply by preventing an event from happening, CONTINUOUS_RULE_MODIFICATION effects can be used.
    // They work technical like a replacement effect that replaces the event completely.
    // But player isn't asked to choose order of effects if multiple are applied to the same event.
    public ContinuousRuleModifyingEffectImpl(Duration duration, Outcome outcome) {
        this(duration, outcome, false, true);
    }

    /**
     *
     * @param duration
     * @param outcome
     * @param messageToUser - Every time the effect replaces an event, the user
     * gets a message in a dialog window. Don't set it to true if the event
     * happens regularly or very often. The message itself can be changed by
     * overriding the getInfoMessage method.
     * @param messageToLog - Every time the effect replaces an event, a message
     * is posted to the game log. The message can be changed by overriding the
     * getInfoMessage method.
     */
    public ContinuousRuleModifyingEffectImpl(Duration duration, Outcome outcome, boolean messageToUser, boolean messageToLog) {
        super(duration, outcome);
        this.effectType = EffectType.CONTINUOUS_RULE_MODIFICATION;
        this.infoMessage = null;
        this.messageToUser = messageToUser;
        this.messageToGameLog = messageToLog;
    }

    public ContinuousRuleModifyingEffectImpl(final ContinuousRuleModifyingEffectImpl effect) {
        super(effect);
        this.infoMessage = effect.infoMessage;
        this.messageToUser = effect.messageToUser;
        this.messageToGameLog = effect.messageToGameLog;
    }

    /**
     * An early check for the event types this effect applies to. This check was
     * added to speed up event handling. Once all existing
     * ContinuousRuleModifiyingEffects have implemented this method, the method
     * should be changed to abstract here or removed.
     *
     * @param event
     * @param game
     * @return
     */
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        if (infoMessage == null) {
            String message;
            MageObject object = game.getObject(source);
            if (object != null) {
                message = source.getRule(messageToUser ? object.getIdName() : object.getLogName());
            } else {
                message = source.getRule();
            }
            return message;
        } else {
            return infoMessage;
        }
    }

    @Override
    public boolean sendMessageToUser() {
        return messageToUser;
    }

    @Override
    public boolean sendMessageToGameLog() {
        return messageToGameLog;
    }

}
