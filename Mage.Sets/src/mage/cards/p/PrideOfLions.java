
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DamageAsThoughNotBlockedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author anonymous
 */
public final class PrideOfLions extends CardImpl {

    public PrideOfLions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.CAT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // You may have Pride of Lions assign its combat damage as though it weren't blocked.
        this.addAbility(DamageAsThoughNotBlockedAbility.getInstance());
    }

    private PrideOfLions(final PrideOfLions card) {
        super(card);
    }

    @Override
    public PrideOfLions copy() {
        return new PrideOfLions(this);
    }
}
