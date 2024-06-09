package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateRoleAttachedTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.EnchantedPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SyrArmontTheRedeemer extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("enchanted creatures");

    static {
        filter.add(EnchantedPredicate.instance);
    }

    public SyrArmontTheRedeemer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Syr Armont, the Redeemer enters the battlefield, create a Monster Role token attached to another target creature you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateRoleAttachedTargetEffect(RoleType.MONSTER));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);

        // Enchanted creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter
        )));
    }

    private SyrArmontTheRedeemer(final SyrArmontTheRedeemer card) {
        super(card);
    }

    @Override
    public SyrArmontTheRedeemer copy() {
        return new SyrArmontTheRedeemer(this);
    }
}
