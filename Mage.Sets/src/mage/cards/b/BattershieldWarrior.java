package mage.cards.b;

import mage.MageInt;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.BoastAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BattershieldWarrior extends CardImpl {

    public BattershieldWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Boast — {1}{W}: Creatures you control get +1/+1 until end of turn.
        this.addAbility(new BoastAbility(new BoostControlledEffect(
                1, 1, Duration.EndOfTurn
        ), "{1}{W}"));
    }

    private BattershieldWarrior(final BattershieldWarrior card) {
        super(card);
    }

    @Override
    public BattershieldWarrior copy() {
        return new BattershieldWarrior(this);
    }
}
