
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ReachAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class GrazingWhiptail extends CardImpl {

    public GrazingWhiptail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());
    }

    private GrazingWhiptail(final GrazingWhiptail card) {
        super(card);
    }

    @Override
    public GrazingWhiptail copy() {
        return new GrazingWhiptail(this);
    }
}
