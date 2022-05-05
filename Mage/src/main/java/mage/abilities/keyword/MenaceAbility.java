package mage.abilities.keyword;

import mage.abilities.StaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByOneEffect;
import mage.constants.Zone;

/**
 * @author LevelX2
 */
public class MenaceAbility extends StaticAbility { // Menace may not be a Singleton because the source ability is needed in the continuous effect

    private final boolean showAbilityHint;

    public MenaceAbility() {
        this(true);
    }

    public MenaceAbility(boolean showAbilityHint) {
        super(Zone.BATTLEFIELD, new CantBeBlockedByOneEffect(2));
        this.showAbilityHint = showAbilityHint;
    }

    public MenaceAbility(final MenaceAbility ability) {
        super(ability);
        this.showAbilityHint = ability.showAbilityHint;
    }

    @Override
    public MenaceAbility copy() {
        return new MenaceAbility(this);
    }

    @Override
    public String getRule() {
        String res = "menace";
        if (this.showAbilityHint) {
            res += " <i>(This creature can't be blocked except by two or more creatures.)</i>";
        }
        return res;
    }
}
