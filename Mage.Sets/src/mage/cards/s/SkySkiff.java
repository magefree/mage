
package mage.cards.s;

import mage.MageInt;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public final class SkySkiff extends CardImpl {

    public SkySkiff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private SkySkiff(final SkySkiff card) {
        super(card);
    }

    @Override
    public SkySkiff copy() {
        return new SkySkiff(this);
    }
}
