/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.keyword;

import java.io.ObjectStreamException;
import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByOneEffect;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public class MenaceAbility extends StaticAbility {
    
    private static final MenaceAbility fINSTANCE =  new MenaceAbility();

    public MenaceAbility() {
        super(Zone.BATTLEFIELD, new CantBeBlockedByOneEffect(2));
    }

    public MenaceAbility(MenaceAbility ability) {
        super(ability);
    }
    
    private Object readResolve() throws ObjectStreamException {
        return fINSTANCE;
    }

    public static MenaceAbility getInstance() {
        return fINSTANCE;
    }
    
    @Override
    public Ability copy() {
        return fINSTANCE;
    }    

    @Override
    public String getRule() {
        return "Menace <i>(This creature can't be blocked except by two or more creatures.)</i>";
    }    
    
}
