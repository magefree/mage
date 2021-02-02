
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class RagingKavu extends CardImpl {

    public RagingKavu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{G}");
        this.subtype.add(SubType.KAVU);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        this.addAbility(FlashAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
    }

    private RagingKavu(final RagingKavu card) {
        super(card);
    }

    @Override
    public RagingKavu copy() {
        return new RagingKavu(this);
    }
}
