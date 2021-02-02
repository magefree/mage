
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class MarkovPatrician extends CardImpl {

    public MarkovPatrician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.VAMPIRE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        this.addAbility(LifelinkAbility.getInstance());
    }

    private MarkovPatrician(final MarkovPatrician card) {
        super(card);
    }

    @Override
    public MarkovPatrician copy() {
        return new MarkovPatrician(this);
    }
}
