

package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * Cystbearer
 *
 * @author nantuko
 */
public final class Cystbearer extends CardImpl {

    public Cystbearer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.addAbility(InfectAbility.getInstance());
    }

    private Cystbearer(final Cystbearer card) {
        super(card);
    }

    @Override
    public Cystbearer copy() {
        return new Cystbearer(this);
    }

}
