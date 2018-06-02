
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author KholdFuzion
 */
public final class MightSliver extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("All Sliver creatures");

    static {
        filter.add(new SubtypePredicate(SubType.SLIVER));
    }

    public MightSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // All Sliver creatures get +2/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(2, 2, Duration.WhileOnBattlefield, filter, false)));
    }

    public MightSliver(final MightSliver card) {
        super(card);
    }

    @Override
    public MightSliver copy() {
        return new MightSliver(this);
    }
}
