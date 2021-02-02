
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageToOpponentTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class HypnoticSpecter extends CardImpl {

    public HypnoticSpecter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.SPECTER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(FlyingAbility.getInstance());

        // Whenever Hypnotic Specter deals damage to an opponent, that player discards a card at random.
        this.addAbility(new DealsDamageToOpponentTriggeredAbility(new DiscardTargetEffect(1, true), false, false, true));
    }

    private HypnoticSpecter(final HypnoticSpecter card) {
        super(card);
    }

    @Override
    public HypnoticSpecter copy() {
        return new HypnoticSpecter(this);
    }
}
