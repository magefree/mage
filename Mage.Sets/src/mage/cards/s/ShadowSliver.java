
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.ShadowAbility;
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
 * @author anonymous
 */
public final class ShadowSliver extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("All Sliver creatures");

    static {
        filter.add(new SubtypePredicate(SubType.SLIVER));
    }

    public ShadowSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.SLIVER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // All Sliver creatures have shadow.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(
                ShadowAbility.getInstance(), Duration.WhileOnBattlefield,
                filter, "All Sliver creatures have shadow.")));
    }

    public ShadowSliver(final ShadowSliver card) {
        super(card);
    }

    @Override
    public ShadowSliver copy() {
        return new ShadowSliver(this);
    }
}
