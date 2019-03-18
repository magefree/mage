
package mage.abilities.common;

import mage.abilities.MageSingleton;
import mage.abilities.StaticAbility;

import java.io.ObjectStreamException;

import mage.constants.AbilityType;
import mage.constants.Zone;

/**
 * @author L_J
 */
public class WhileSearchingPlayFromLibraryAbility extends StaticAbility implements MageSingleton {

    private static final WhileSearchingPlayFromLibraryAbility instance =  new WhileSearchingPlayFromLibraryAbility();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static WhileSearchingPlayFromLibraryAbility getInstance() {
        return instance;
    }

    private WhileSearchingPlayFromLibraryAbility() {
        super(AbilityType.STATIC, Zone.LIBRARY);
    }

    @Override
    public String getRule() {
        return "While you're searching your library, you may cast {this} from your library.";
    }

    @Override
    public WhileSearchingPlayFromLibraryAbility copy() {
        return instance;
    }

}
