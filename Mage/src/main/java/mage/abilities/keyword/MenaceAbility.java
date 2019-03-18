/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByOneEffect;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public class MenaceAbility extends StaticAbility { // Menace may not be a Singleton because the source ability is needed in the continuous effect

    private boolean showAbilityHint = true;

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
    public Ability copy() {
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
