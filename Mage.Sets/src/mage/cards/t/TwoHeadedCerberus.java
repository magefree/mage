
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class TwoHeadedCerberus extends CardImpl {

    public TwoHeadedCerberus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{R}");
        this.subtype.add(SubType.DOG);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());
    }

    private TwoHeadedCerberus(final TwoHeadedCerberus card) {
        super(card);
    }

    @Override
    public TwoHeadedCerberus copy() {
        return new TwoHeadedCerberus(this);
    }
}
