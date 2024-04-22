

package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class HumbleBudoka extends CardImpl {

    public HumbleBudoka (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(ShroudAbility.getInstance());
    }

    private HumbleBudoka(final HumbleBudoka card) {
        super(card);
    }

    @Override
    public HumbleBudoka copy() {
        return new HumbleBudoka(this);
    }

}
