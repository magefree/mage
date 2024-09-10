package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SyrAlinTheLionsClaw extends CardImpl {

    public SyrAlinTheLionsClaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever Syr Alin, the Lion's Claw attacks, other creatures you control get +1/+1 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostControlledEffect(
                1, 1, Duration.EndOfTurn, true
        ), false));
    }

    private SyrAlinTheLionsClaw(final SyrAlinTheLionsClaw card) {
        super(card);
    }

    @Override
    public SyrAlinTheLionsClaw copy() {
        return new SyrAlinTheLionsClaw(this);
    }
}
