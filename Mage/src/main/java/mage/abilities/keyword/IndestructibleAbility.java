
package mage.abilities.keyword;

import java.io.ObjectStreamException;
import mage.constants.Zone;
import mage.abilities.StaticAbility;

/**
 * OLD RULES: 700.4. If a permanent is indestructible, rules and effects can't
 * destroy it. (See rule 701.6, "Destroy.") Such permanents are not destroyed by
 * lethal damage, and they ignore the lethal-damage state-based action (see rule
 * 704.5g). Rules or effects may cause an indestructible permanent to be
 * sacrificed, put into a graveyard, or exiled. #
 *
 * 700.4a Although the text "[This permanent] is indestructible" is an ability,
 * actually being indestructible is neither an ability nor a characteristic.
 * It's just something that's true about a permanent.
 *
 * NEW RULES
 *
 *
 *
 *
 *
 *
 * @author BetaSteward_at_googlemail.com
 */
public class IndestructibleAbility extends StaticAbility {

    private static final IndestructibleAbility instance;

    static {
        instance = new IndestructibleAbility();
    }

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static IndestructibleAbility getInstance() {
        return instance;
    }

    private IndestructibleAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    @Override
    public IndestructibleAbility copy() {
        return instance;
    }

    @Override
    public String getRule() {
        return "indestructible";
    }

}
