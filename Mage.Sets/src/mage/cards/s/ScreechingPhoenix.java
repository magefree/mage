package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author TheElk801
 */
public final class ScreechingPhoenix extends CardImpl {

    public ScreechingPhoenix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.subtype.add(SubType.PHOENIX);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {2}{R}: Creatures you control get +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostControlledEffect(1, 0, Duration.EndOfTurn),
                new ManaCostsImpl<>("{2}{R}")
        ));
    }

    private ScreechingPhoenix(final ScreechingPhoenix card) {
        super(card);
    }

    @Override
    public ScreechingPhoenix copy() {
        return new ScreechingPhoenix(this);
    }
}
