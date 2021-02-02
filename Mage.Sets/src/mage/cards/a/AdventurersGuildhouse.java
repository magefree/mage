
package mage.cards.a;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.BandsWithOtherAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author L_J
 */
public final class AdventurersGuildhouse extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Green legendary creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public AdventurersGuildhouse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Green legendary creatures you control have "bands with other legendary creatures."
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(new BandsWithOtherAbility(SuperType.LEGENDARY), Duration.WhileOnBattlefield, filter).withForceQuotes()));
    }

    private AdventurersGuildhouse(final AdventurersGuildhouse card) {
        super(card);
    }

    @Override
    public AdventurersGuildhouse copy() {
        return new AdventurersGuildhouse(this);
    }
}
