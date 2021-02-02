
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageToOpponentTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.HorsemanshipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class WeiNightRaiders extends CardImpl {

    public WeiNightRaiders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Horsemanship
        this.addAbility(HorsemanshipAbility.getInstance());
        // Whenever Wei Night Raiders deals damage to an opponent, that player discards a card.
        this.addAbility(new DealsDamageToOpponentTriggeredAbility(new DiscardTargetEffect(1), false, false, true));
    }

    private WeiNightRaiders(final WeiNightRaiders card) {
        super(card);
    }

    @Override
    public WeiNightRaiders copy() {
        return new WeiNightRaiders(this);
    }
}
