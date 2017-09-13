/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.keyword;

import java.io.ObjectStreamException;
import mage.abilities.Ability;
import mage.abilities.EvasionAbility;
import mage.abilities.MageSingleton;
import mage.abilities.effects.common.combat.CantBeBlockedByOneEffect;

/**
 *
 * @author LevelX2
 */
public class MenaceAbility extends EvasionAbility implements MageSingleton {

    private static final MenaceAbility instance = new MenaceAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static MenaceAbility getInstance() {
        return instance;
    }

    private MenaceAbility() {
        this.addEffect(new CantBeBlockedByOneEffect(2));
    }

    @Override
    public Ability copy() {
        return instance;
    }

    @Override
    public String getRule() {
        return "Menace <i>(This creature can't be blocked except by two or more creatures.)</i>";
    }

}
