package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
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
public final class TimidShieldbearer extends CardImpl {

    public TimidShieldbearer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {4}{W}: Creatures you control get +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostControlledEffect(1, 1, Duration.EndOfTurn), new ManaCostsImpl<>("{4}{W}")
        ));
    }

    private TimidShieldbearer(final TimidShieldbearer card) {
        super(card);
    }

    @Override
    public TimidShieldbearer copy() {
        return new TimidShieldbearer(this);
    }
}
