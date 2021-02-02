
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class MerfolkOfTheDepths extends CardImpl {

    public MerfolkOfTheDepths(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G/U}{G/U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());
    }

    private MerfolkOfTheDepths(final MerfolkOfTheDepths card) {
        super(card);
    }

    @Override
    public MerfolkOfTheDepths copy() {
        return new MerfolkOfTheDepths(this);
    }
}
