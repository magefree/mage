package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
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
public final class BoltHound extends CardImpl {

    public BoltHound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Bolt Hound attacks, other creatures you control get +1/+0 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostControlledEffect(
                1, 0, Duration.EndOfTurn, true
        ), false));
    }

    private BoltHound(final BoltHound card) {
        super(card);
    }

    @Override
    public BoltHound copy() {
        return new BoltHound(this);
    }
}
