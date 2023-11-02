
package mage.abilities.common;

import mage.abilities.StaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.Effect;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.constants.Zone;
import mage.util.CardUtil;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class EntersBattlefieldAbility extends StaticAbility {

    protected String abilityRule;
    protected boolean optional;

    public EntersBattlefieldAbility(Effect effect) {
        this(effect, false);
    }

    /**
     * @param effect   effect that happens when the permanent enters the
     *                 battlefield
     * @param optional
     */
    public EntersBattlefieldAbility(Effect effect, boolean optional) {
        this(effect, optional, null, null, null);
    }

    public EntersBattlefieldAbility(Effect effect, String effectText) {
        this(effect, null, null, effectText);
    }

    public EntersBattlefieldAbility(Effect effect, Condition condition, String abilityRule, String effectText) {
        this(effect, false, condition, abilityRule, effectText);
    }

    /**
     * @param effect      effect that happens when the permanent enters the
     *                    battlefield
     * @param optional
     * @param condition   only if this condition is true, the effect will happen
     * @param abilityRule rule for this ability (no text from effects will be
     *                    added)
     * @param effectText  this text will be used for the EnterBattlefieldEffect
     */
    public EntersBattlefieldAbility(Effect effect, boolean optional, Condition condition, String abilityRule, String effectText) {
        super(Zone.ALL, new EntersBattlefieldEffect(effect, condition, effectText, true, optional));
        this.abilityRule = abilityRule;
        this.optional = optional;
    }

    protected EntersBattlefieldAbility(final EntersBattlefieldAbility ability) {
        super(ability);
        this.abilityRule = ability.abilityRule;
        this.optional = ability.optional;
    }

    @Override
    public void addEffect(Effect effect) {
        if (!getEffects().isEmpty()) {
            Effect entersBattlefieldEffect = this.getEffects().get(0);
            if (entersBattlefieldEffect instanceof EntersBattlefieldEffect) {
                ((EntersBattlefieldEffect) entersBattlefieldEffect).addEffect(effect);
                return;
            }
        }
        super.addEffect(effect);
    }

    @Override
    public EntersBattlefieldAbility copy() {
        return new EntersBattlefieldAbility(this);
    }

    @Override
    public String getRule() {
        if (abilityRule != null && !abilityRule.isEmpty()) {
            return abilityRule;
        }
        String superRule = super.getRule();
        String prefix;
        if (abilityWord != null) {
            prefix = abilityWord.formatWord();
        } else if (flavorWord != null) {
            prefix = CardUtil.italicizeWithEmDash(flavorWord);
        } else {
            prefix = null;
        }
        String rule = (optional ? "you may have " : "") + "{this} enter" + (optional ? "" : "s") +
                " the battlefield" + (!superRule.isEmpty() && superRule.charAt(0) == ' ' ? "" : " ") + superRule;
        if (prefix != null) {
            return prefix + CardUtil.getTextWithFirstCharUpperCase(rule);
        }
        return rule;
    }
}
