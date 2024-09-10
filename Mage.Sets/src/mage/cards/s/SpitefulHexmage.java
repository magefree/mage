package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
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
public final class SpitefulHexmage extends CardImpl {

    public SpitefulHexmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Spiteful Hexmage enters the battlefield, create a Cursed Role token attached to target creature you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateRoleAttachedTargetEffect(RoleType.CURSED));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private SpitefulHexmage(final SpitefulHexmage card) {
        super(card);
    }

    @Override
    public SpitefulHexmage copy() {
        return new SpitefulHexmage(this);
    }
}
