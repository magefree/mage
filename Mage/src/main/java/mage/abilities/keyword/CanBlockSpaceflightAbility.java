/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.keyword;

import java.io.ObjectStreamException;
import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public class CanBlockSpaceflightAbility extends StaticAbility implements MageSingleton {

    private static final CanBlockSpaceflightAbility fINSTANCE = new CanBlockSpaceflightAbility();

    private Object readResolve() throws ObjectStreamException {
        return fINSTANCE;
    }

    public static CanBlockSpaceflightAbility getInstance() {
        return fINSTANCE;
    }

    private CanBlockSpaceflightAbility() {
        super(Zone.ALL, null);
    }

    @Override
    public String getRule() {
        return "{this} can block creatures with spaceflight.";
    }

    @Override
    public CanBlockSpaceflightAbility copy() {
        return fINSTANCE;
    }

}
