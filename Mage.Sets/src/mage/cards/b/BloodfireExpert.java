
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class BloodfireExpert extends CardImpl {

    public BloodfireExpert(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.EFREET, SubType.MONK);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Prowess
        this.addAbility(new ProwessAbility());
    }

    private BloodfireExpert(final BloodfireExpert card) {
        super(card);
    }

    @Override
    public BloodfireExpert copy() {
        return new BloodfireExpert(this);
    }
}
