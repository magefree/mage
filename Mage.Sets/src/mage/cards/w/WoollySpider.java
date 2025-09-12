package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.BlocksCreatureTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author tcontis
 */
public final class WoollySpider extends CardImpl {

    public WoollySpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");
        this.subtype.add(SubType.SPIDER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever Woolly Spider blocks a creature with flying, Woolly Spider gets +0/+2 until end of turn.
        this.addAbility(new BlocksCreatureTriggeredAbility(new BoostSourceEffect(0, 2, Duration.EndOfTurn), StaticFilters.FILTER_CREATURE_FLYING, false));
    }

    private WoollySpider(final WoollySpider card) {
        super(card);
    }

    @Override
    public WoollySpider copy() {
        return new WoollySpider(this);
    }
}
