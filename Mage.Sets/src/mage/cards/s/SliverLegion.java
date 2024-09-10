
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author KholdFuzion

 */
public final class SliverLegion extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.SLIVER, "All Sliver creatures");
    private static final FilterPermanent countfilter = new FilterPermanent(SubType.SLIVER, "other Sliver on the battlefield");

    static {
        countfilter.add(AnotherPredicate.instance);
    }

    public SliverLegion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{U}{B}{R}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // All Sliver creatures get +1/+1 for each other Sliver on the battlefield.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(new PermanentsOnBattlefieldCount(countfilter) , new PermanentsOnBattlefieldCount(countfilter), Duration.WhileOnBattlefield, filter, false)));
    }

    private SliverLegion(final SliverLegion card) {
        super(card);
    }

    @Override
    public SliverLegion copy() {
        return new SliverLegion(this);
    }
}
