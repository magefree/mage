
package mage.abilities.keyword;

import mage.abilities.EvasionAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class CantBeBlockedSourceAbility extends EvasionAbility {

    public CantBeBlockedSourceAbility() {
        this("");
    }

    public CantBeBlockedSourceAbility(String ruleText) {
        Effect effect = new CantBeBlockedSourceEffect();
        if (ruleText != null && !ruleText.isEmpty()) {
            effect.setText(ruleText);
        }
        this.addEffect(effect);
    }

    private CantBeBlockedSourceAbility(CantBeBlockedSourceAbility ability) {
        super(ability);
    }

    @Override
    public CantBeBlockedSourceAbility copy() {
        return new CantBeBlockedSourceAbility(this);
    }
}
