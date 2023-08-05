
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author North
 */
public final class JasmineBoreal extends CardImpl {

    public JasmineBoreal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);
    }

    private JasmineBoreal(final JasmineBoreal card) {
        super(card);
    }

    @Override
    public JasmineBoreal copy() {
        return new JasmineBoreal(this);
    }
}
