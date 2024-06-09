
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageToOpponentTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author LoneFox
 */
public final class ZhangLiaoHeroOfHefei extends CardImpl {

    public ZhangLiaoHeroOfHefei(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Zhang Liao, Hero of Hefei deals damage to an opponent, that opponent discards a card.
        this.addAbility(new DealsDamageToOpponentTriggeredAbility(new DiscardTargetEffect(1), false, false, true));
    }

    private ZhangLiaoHeroOfHefei(final ZhangLiaoHeroOfHefei card) {
        super(card);
    }

    @Override
    public ZhangLiaoHeroOfHefei copy() {
        return new ZhangLiaoHeroOfHefei(this);
    }
}
