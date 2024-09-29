package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateRoleAttachedTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.RoleType;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MerryBards extends CardImpl {

    public MerryBards(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Merry Bards enters the battlefield, you may pay {1}. When you do, create a Young Hero Role token attached to target creature you control.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new CreateRoleAttachedTargetEffect(RoleType.YOUNG_HERO), false
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DoWhenCostPaid(ability, new GenericManaCost(1), "Pay {1}?")
        ));
    }

    private MerryBards(final MerryBards card) {
        super(card);
    }

    @Override
    public MerryBards copy() {
        return new MerryBards(this);
    }
}
