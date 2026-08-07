package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class EagleOfTheGreatShelf extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_OTHER_CONTROLLED_CREATURE, 1);
    private static final Hint hint = new ValueHint("Other creatures you control", xValue);

    public EagleOfTheGreatShelf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever this creature attacks, it gets +1/+1 until end of turn for each other creature you control.
        this.addAbility(new AttacksTriggeredAbility(
            new BoostSourceEffect(xValue, xValue, Duration.EndOfTurn)
                .setText("it gets +1/+1 until end of turn for each other creature you control")
        ).addHint(hint));
    }

    private EagleOfTheGreatShelf(final EagleOfTheGreatShelf card) {
        super(card);
    }

    @Override
    public EagleOfTheGreatShelf copy() {
        return new EagleOfTheGreatShelf(this);
    }
}
