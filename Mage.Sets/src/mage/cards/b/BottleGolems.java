package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BottleGolems extends CardImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount(false);

    public BottleGolems(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Bottle Golems dies, you gain life equal to its power.
        this.addAbility(new DiesSourceTriggeredAbility(new GainLifeEffect(xValue, "you gain life equal to its power")));
    }

    private BottleGolems(final BottleGolems card) {
        super(card);
    }

    @Override
    public BottleGolems copy() {
        return new BottleGolems(this);
    }
}
