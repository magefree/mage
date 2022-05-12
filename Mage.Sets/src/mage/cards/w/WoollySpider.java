
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksCreatureTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author tcontis
 */
public final class WoollySpider extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public WoollySpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add(SubType.SPIDER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever Woolly Spider blocks a creature with flying, Woolly Spider gets +0/+2 until end of turn.
        this.addAbility(new BlocksCreatureTriggeredAbility(new BoostSourceEffect(0, 2, Duration.EndOfTurn), filter, false));
    }

    private WoollySpider(final WoollySpider card) {
        super(card);
    }

    @Override
    public WoollySpider copy() {
        return new WoollySpider(this);
    }
}