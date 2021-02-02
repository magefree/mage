
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
public final class ZodiacDog extends CardImpl {

    public ZodiacDog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.DOG);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Mountainwalk
        this.addAbility(new MountainwalkAbility());
    }

    private ZodiacDog(final ZodiacDog card) {
        super(card);
    }

    @Override
    public ZodiacDog copy() {
        return new ZodiacDog(this);
    }
}
