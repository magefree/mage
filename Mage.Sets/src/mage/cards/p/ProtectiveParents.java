package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateRoleAttachedTargetEffect;
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
public final class ProtectiveParents extends CardImpl {

    public ProtectiveParents(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Protective Parents dies, create a Young Hero Role token attached to up to one target creature you control.
        Ability ability = new DiesSourceTriggeredAbility(new CreateRoleAttachedTargetEffect(RoleType.YOUNG_HERO));
        ability.addTarget(new TargetControlledCreaturePermanent(0, 1));
        this.addAbility(ability);
    }

    private ProtectiveParents(final ProtectiveParents card) {
        super(card);
    }

    @Override
    public ProtectiveParents copy() {
        return new ProtectiveParents(this);
    }
}
