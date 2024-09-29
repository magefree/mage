package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.GreatestPowerAmongControlledCreaturesValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RubblebeltRioters extends CardImpl {

    public RubblebeltRioters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Rubblebelt Rioters attacks, it gets +X/+0 until end of turn, where X is the greatest power among creatures you control.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(
                GreatestPowerAmongControlledCreaturesValue.instance, StaticValue.get(0),
                Duration.EndOfTurn
        ), false));
    }

    private RubblebeltRioters(final RubblebeltRioters card) {
        super(card);
    }

    @Override
    public RubblebeltRioters copy() {
        return new RubblebeltRioters(this);
    }
}
