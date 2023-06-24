package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.AmassEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvadingManticore extends CardImpl {

    public InvadingManticore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.MANTICORE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When Invading Manticore enters the battlefield, amass 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AmassEffect(2, SubType.ZOMBIE)));
    }

    private InvadingManticore(final InvadingManticore card) {
        super(card);
    }

    @Override
    public InvadingManticore copy() {
        return new InvadingManticore(this);
    }
}
