package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.AttacksWhileSaddledTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.SaddleAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DroverGrizzly extends CardImpl {

    public DroverGrizzly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.BEAR);
        this.subtype.add(SubType.MOUNT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Whenever Drover Grizzly attacks while saddled, creatures you control gain trample until end of turn.
        this.addAbility(new AttacksWhileSaddledTriggeredAbility(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES
        )));

        // Saddle 1
        this.addAbility(new SaddleAbility(1));
    }

    private DroverGrizzly(final DroverGrizzly card) {
        super(card);
    }

    @Override
    public DroverGrizzly copy() {
        return new DroverGrizzly(this);
    }
}
