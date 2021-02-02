package mage.cards.m;

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

import java.util.UUID;

/**
 * @author L_J
 */
public final class MountainStronghold extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Red legendary creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public MountainStronghold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Red legendary creatures you control have "bands with other legendary creatures."
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(
                new BandsWithOtherAbility(SuperType.LEGENDARY), Duration.WhileOnBattlefield, filter)
                .withForceQuotes()
        ));
    }

    private MountainStronghold(final MountainStronghold card) {
        super(card);
    }

    @Override
    public MountainStronghold copy() {
        return new MountainStronghold(this);
    }
}
