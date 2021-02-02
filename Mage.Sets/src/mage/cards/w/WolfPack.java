
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DamageAsThoughNotBlockedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class WolfPack extends CardImpl {

    public WolfPack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{G}{G}");
        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // You may have Wolf Pack assign its combat damage as though it weren't blocked.
        this.addAbility(DamageAsThoughNotBlockedAbility.getInstance());
    }

    private WolfPack(final WolfPack card) {
        super(card);
    }

    @Override
    public WolfPack copy() {
        return new WolfPack(this);
    }
}
