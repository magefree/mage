
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class ThunderingTanadon extends CardImpl {

    public ThunderingTanadon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}{G/P}{G/P}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        this.addAbility(TrampleAbility.getInstance());
    }

    private ThunderingTanadon(final ThunderingTanadon card) {
        super(card);
    }

    @Override
    public ThunderingTanadon copy() {
        return new ThunderingTanadon(this);
    }
}
