
package mage.cards.u;

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
import mage.filter.predicate.mageobject.SupertypePredicate;

/**
 *
 * @author L_J
 */
public final class UnholyCitadel extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Black legendary creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
        filter.add(new SupertypePredicate(SuperType.LEGENDARY));
    }

    public UnholyCitadel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Black legendary creatures you control have "bands with other legendary creatures."
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(new BandsWithOtherAbility(SuperType.LEGENDARY), Duration.WhileOnBattlefield, filter)));
    }

    public UnholyCitadel(final UnholyCitadel card) {
        super(card);
    }

    @Override
    public UnholyCitadel copy() {
        return new UnholyCitadel(this);
    }
}
