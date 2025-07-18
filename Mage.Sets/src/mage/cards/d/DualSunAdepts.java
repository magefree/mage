package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DualSunAdepts extends CardImpl {

    public DualSunAdepts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // {5}: Creatures you control get +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostControlledEffect(1, 1, Duration.EndOfTurn), new GenericManaCost(5)
        ));
    }

    private DualSunAdepts(final DualSunAdepts card) {
        super(card);
    }

    @Override
    public DualSunAdepts copy() {
        return new DualSunAdepts(this);
    }
}
