
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksCreatureWithFlyingTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author tcontis
 */
public final class WoollySpider extends CardImpl {

    public WoollySpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add(SubType.SPIDER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever Woolly Spider blocks a creature with flying, Woolly Spider gets +0/+2 until end of turn.
        this.addAbility(new BlocksCreatureWithFlyingTriggeredAbility(new BoostSourceEffect(0, 2, Duration.EndOfTurn), false));
    }

    private WoollySpider(final WoollySpider card) {
        super(card);
    }

    @Override
    public WoollySpider copy() {
        return new WoollySpider(this);
    }
}