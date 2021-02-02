
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.RampageAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class BalduvianWarMakers extends CardImpl {

    public BalduvianWarMakers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.HUMAN, SubType.BARBARIAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Rampage 1
        this.addAbility(new RampageAbility(1));
    }

    private BalduvianWarMakers(final BalduvianWarMakers card) {
        super(card);
    }

    @Override
    public BalduvianWarMakers copy() {
        return new BalduvianWarMakers(this);
    }
}
