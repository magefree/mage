
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author magenoxx_at_gmail.com
 */
public final class FencingAce extends CardImpl {

    public FencingAce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Double Strike
        this.addAbility(DoubleStrikeAbility.getInstance());
    }

    private FencingAce(final FencingAce card) {
        super(card);
    }

    @Override
    public FencingAce copy() {
        return new FencingAce(this);
    }
}
