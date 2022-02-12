package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.AttacksAloneControlledTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.HasteAbility;
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
public final class AsariCaptain extends CardImpl {

    private static final DynamicValue xValue
            = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_SAMURAI_OR_WARRIOR);
    private static final Hint hint = new ValueHint("Samurai and Warriors you control", xValue);

    public AsariCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever a Samurai or Warrior you control attacks alone, it gets +1/+0 until end of turn for each Samurai or Warrior you control.
        this.addAbility(new AttacksAloneControlledTriggeredAbility(
                new BoostTargetEffect(xValue, StaticValue.get(0), Duration.EndOfTurn, true)
                        .setText("it gets +1/+0 until end of turn for each Samurai or Warrior you control"),
                StaticFilters.FILTER_CONTROLLED_SAMURAI_OR_WARRIOR, true, false
        ).addHint(hint));
    }

    private AsariCaptain(final AsariCaptain card) {
        super(card);
    }

    @Override
    public AsariCaptain copy() {
        return new AsariCaptain(this);
    }
}
