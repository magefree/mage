package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateRoleAttachedTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.RoleType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpellbookVendor extends CardImpl {

    public SpellbookVendor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // At the beginning of combat on your turn, you may pay {1}. When you do, create a Sorcerer Role token attached to target creature you control.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new CreateRoleAttachedTargetEffect(RoleType.SORCERER), false
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(new BeginningOfCombatTriggeredAbility(new DoWhenCostPaid(
                ability, new GenericManaCost(1), "Pay {1}?"
        ), TargetController.YOU, false));
    }

    private SpellbookVendor(final SpellbookVendor card) {
        super(card);
    }

    @Override
    public SpellbookVendor copy() {
        return new SpellbookVendor(this);
    }
}
