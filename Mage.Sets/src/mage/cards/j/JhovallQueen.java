
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class JhovallQueen extends CardImpl {

    public JhovallQueen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{W}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.REBEL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(7);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
    }

    private JhovallQueen(final JhovallQueen card) {
        super(card);
    }

    @Override
    public JhovallQueen copy() {
        return new JhovallQueen(this);
    }
}
