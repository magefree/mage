
package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class AssemblyWorkerToken extends TokenImpl {

    public AssemblyWorkerToken() {
        super("Assembly-Worker", "2/2 Assembly-Worker artifact creature");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.ASSEMBLY_WORKER);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    public AssemblyWorkerToken(final AssemblyWorkerToken token) {
        super(token);
    }

    public AssemblyWorkerToken copy() {
        return new AssemblyWorkerToken(this);
    }
}
