package mage.cards.u;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateRoleAttachedSourceEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.RoleType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnassumingSage extends CardImpl {

    public UnassumingSage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PEASANT);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Unassuming Sage enters the battlefield, you may pay {2}. If you do, create a Sorcerer Role token attached to it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoIfCostPaid(
                new CreateRoleAttachedSourceEffect(RoleType.SORCERER), new GenericManaCost(2)
        )));
    }

    private UnassumingSage(final UnassumingSage card) {
        super(card);
    }

    @Override
    public UnassumingSage copy() {
        return new UnassumingSage(this);
    }
}
