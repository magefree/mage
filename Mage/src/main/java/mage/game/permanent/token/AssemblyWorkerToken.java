package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class AssemblyWorkerToken extends TokenImpl {

    public AssemblyWorkerToken() {
        super("Assembly-Worker Token", "2/2 colorless Assembly-Worker artifact creature token");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ASSEMBLY_WORKER);

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
