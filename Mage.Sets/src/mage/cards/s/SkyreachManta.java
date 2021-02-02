
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.SunburstAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class SkyreachManta extends CardImpl {

    public SkyreachManta(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{5}");
        this.subtype.add(SubType.FISH);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Sunburst
        this.addAbility(new SunburstAbility(this));
        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private SkyreachManta(final SkyreachManta card) {
        super(card);
    }

    @Override
    public SkyreachManta copy() {
        return new SkyreachManta(this);
    }
}
