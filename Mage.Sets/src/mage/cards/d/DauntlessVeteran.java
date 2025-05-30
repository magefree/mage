package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DauntlessVeteran extends CardImpl {

    public DauntlessVeteran(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever this creature attacks, creatures you control get +1/+1 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostControlledEffect(1, 1, Duration.EndOfTurn)));
    }

    private DauntlessVeteran(final DauntlessVeteran card) {
        super(card);
    }

    @Override
    public DauntlessVeteran copy() {
        return new DauntlessVeteran(this);
    }
}
