
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.RampageAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class HorribleHordes extends CardImpl {

    public HorribleHordes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Rampage 1
        this.addAbility(new RampageAbility(1));
    }

    private HorribleHordes(final HorribleHordes card) {
        super(card);
    }

    @Override
    public HorribleHordes copy() {
        return new HorribleHordes(this);
    }
}
