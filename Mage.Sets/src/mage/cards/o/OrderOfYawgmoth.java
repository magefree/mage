
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class OrderOfYawgmoth extends CardImpl {

    public OrderOfYawgmoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Fear
        this.addAbility(FearAbility.getInstance());
        // Whenever Order of Yawgmoth deals damage to a player, that player discards a card.
        this.addAbility(new DealsDamageToAPlayerTriggeredAbility(new DiscardTargetEffect(1, false), false, true));
    }

    private OrderOfYawgmoth(final OrderOfYawgmoth card) {
        super(card);
    }

    @Override
    public OrderOfYawgmoth copy() {
        return new OrderOfYawgmoth(this);
    }
}
