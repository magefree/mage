
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class AshenmoorGouger extends CardImpl {

    public AshenmoorGouger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B/R}{B/R}{B/R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(new CantBlockAbility());
    }

    private AshenmoorGouger(final AshenmoorGouger card) {
        super(card);
    }

    @Override
    public AshenmoorGouger copy() {
        return new AshenmoorGouger(this);
    }
}
