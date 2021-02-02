
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.MountainwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class ZodiacGoat extends CardImpl {

    public ZodiacGoat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOAT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Mountainwalk
        this.addAbility(new MountainwalkAbility());
    }

    private ZodiacGoat(final ZodiacGoat card) {
        super(card);
    }

    @Override
    public ZodiacGoat copy() {
        return new ZodiacGoat(this);
    }
}
