
package mage.cards.r;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author TheElk801
 */
public final class RighteousWar extends CardImpl {

    private static final FilterCreaturePermanent whiteFilter = new FilterCreaturePermanent("white creatures");
    private static final FilterCreaturePermanent blackFilter = new FilterCreaturePermanent("black creatures");

    static {
        whiteFilter.add(new ColorPredicate(ObjectColor.WHITE));
        blackFilter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public RighteousWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{B}");

        // White creatures you control have protection from black.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new GainAbilityControlledEffect(
                        ProtectionAbility.from(ObjectColor.BLACK),
                        Duration.WhileOnBattlefield,
                        whiteFilter
                )
        ));

        // Black creatures you control have protection from white.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new GainAbilityControlledEffect(
                        ProtectionAbility.from(ObjectColor.WHITE),
                        Duration.WhileOnBattlefield,
                        blackFilter
                )
        ));
    }

    private RighteousWar(final RighteousWar card) {
        super(card);
    }

    @Override
    public RighteousWar copy() {
        return new RighteousWar(this);
    }
}
