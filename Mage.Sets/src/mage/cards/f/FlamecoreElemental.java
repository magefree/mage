
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.EchoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class FlamecoreElemental extends CardImpl {

    public FlamecoreElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Echo {2}{R}{R}
        this.addAbility(new EchoAbility("{2}{R}{R}"));
    }

    private FlamecoreElemental(final FlamecoreElemental card) {
        super(card);
    }

    @Override
    public FlamecoreElemental copy() {
        return new FlamecoreElemental(this);
    }
}
