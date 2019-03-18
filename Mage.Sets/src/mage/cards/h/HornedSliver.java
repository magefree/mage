
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 * @author Loki
 */
public final class HornedSliver extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("All Sliver creatures");

    static {
        filter.add(new SubtypePredicate(SubType.SLIVER));
    }

    public HornedSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter, false)));
    }

    public HornedSliver(final HornedSliver card) {
        super(card);
    }

    @Override
    public HornedSliver copy() {
        return new HornedSliver(this);
    }
}
