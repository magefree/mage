package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateRoleAttachedSourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.RoleType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CursedCourtier extends CardImpl {

    public CursedCourtier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When Cursed Courtier enters the battlefield, create a Cursed Role token attached to it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateRoleAttachedSourceEffect(RoleType.CURSED)));
    }

    private CursedCourtier(final CursedCourtier card) {
        super(card);
    }

    @Override
    public CursedCourtier copy() {
        return new CursedCourtier(this);
    }
}
