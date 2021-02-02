
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class ZodiacHorse extends CardImpl {

    public ZodiacHorse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.HORSE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Islandwalk
        this.addAbility(new IslandwalkAbility());
    }

    private ZodiacHorse(final ZodiacHorse card) {
        super(card);
    }

    @Override
    public ZodiacHorse copy() {
        return new ZodiacHorse(this);
    }
}
