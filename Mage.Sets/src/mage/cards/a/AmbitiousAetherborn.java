
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FabricateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class AmbitiousAetherborn extends CardImpl {

    public AmbitiousAetherborn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.AETHERBORN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Fabricate 1
        this.addAbility(new FabricateAbility(1));
    }

    private AmbitiousAetherborn(final AmbitiousAetherborn card) {
        super(card);
    }

    @Override
    public AmbitiousAetherborn copy() {
        return new AmbitiousAetherborn(this);
    }
}
