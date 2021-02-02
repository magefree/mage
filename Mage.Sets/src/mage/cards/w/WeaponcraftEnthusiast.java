
package mage.cards.w;

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
public final class WeaponcraftEnthusiast extends CardImpl {

    public WeaponcraftEnthusiast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.AETHERBORN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Fabricate 2
        this.addAbility(new FabricateAbility(2));
    }

    private WeaponcraftEnthusiast(final WeaponcraftEnthusiast card) {
        super(card);
    }

    @Override
    public WeaponcraftEnthusiast copy() {
        return new WeaponcraftEnthusiast(this);
    }
}
