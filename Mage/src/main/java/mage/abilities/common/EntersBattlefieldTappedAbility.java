
package mage.abilities.common;

import mage.abilities.StaticAbility;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.constants.Zone;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class EntersBattlefieldTappedAbility extends StaticAbility {

    private String ruleText;

    public EntersBattlefieldTappedAbility() {
        super(Zone.ALL, new EntersBattlefieldEffect(new TapSourceEffect(true)));
    }

    public EntersBattlefieldTappedAbility(String ruleText) {
        this();
        this.ruleText = ruleText;
    }

    protected EntersBattlefieldTappedAbility(final EntersBattlefieldTappedAbility ability) {
        super(ability);
        this.ruleText = ability.ruleText;
    }

    @Override
    public String getRule() {
        if (ruleText != null) {
            return ruleText;
        }
        return "{this} enters the battlefield tapped.";
    }

    @Override
    public EntersBattlefieldTappedAbility copy() {
        return new EntersBattlefieldTappedAbility(this);
    }

}
