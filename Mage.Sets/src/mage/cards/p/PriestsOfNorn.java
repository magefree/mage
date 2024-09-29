

package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.InfectAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author ayratn
 */
public final class PriestsOfNorn extends CardImpl {

    public PriestsOfNorn (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);
        this.addAbility(VigilanceAbility.getInstance());
        this.addAbility(InfectAbility.getInstance());
    }

    private PriestsOfNorn(final PriestsOfNorn card) {
        super(card);
    }

    @Override
    public PriestsOfNorn copy() {
        return new PriestsOfNorn(this);
    }

}
