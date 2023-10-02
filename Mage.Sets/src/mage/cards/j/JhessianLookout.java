

package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class JhessianLookout extends CardImpl {

    public JhessianLookout (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
    }

    private JhessianLookout(final JhessianLookout card) {
        super(card);
    }

    @Override
    public JhessianLookout copy() {
        return new JhessianLookout(this);
    }

}
