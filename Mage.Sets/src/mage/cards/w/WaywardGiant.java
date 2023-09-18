
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class WaywardGiant extends CardImpl {

    public WaywardGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Menace
        this.addAbility(new MenaceAbility(false));
    }

    private WaywardGiant(final WaywardGiant card) {
        super(card);
    }

    @Override
    public WaywardGiant copy() {
        return new WaywardGiant(this);
    }
}
