
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class PorcelainLegionnaire extends CardImpl {

    public PorcelainLegionnaire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}{W/P}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        this.addAbility(FirstStrikeAbility.getInstance());
    }

    private PorcelainLegionnaire(final PorcelainLegionnaire card) {
        super(card);
    }

    @Override
    public PorcelainLegionnaire copy() {
        return new PorcelainLegionnaire(this);
    }
}
