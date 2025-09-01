
package mage.cards.n;

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
 * @author LevelX2
 */
public final class NetcasterSpider extends CardImpl {

    public NetcasterSpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.SPIDER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever Netcaster Spider blocks a creature with flying, Netcaster Spider gets +2/+0 until end of turn.
        this.addAbility(new BlocksCreatureTriggeredAbility(new BoostSourceEffect(2, 0, Duration.EndOfTurn), StaticFilters.FILTER_CREATURE_FLYING, false));
    }

    private NetcasterSpider(final NetcasterSpider card) {
        super(card);
    }

    @Override
    public NetcasterSpider copy() {
        return new NetcasterSpider(this);
    }
}
