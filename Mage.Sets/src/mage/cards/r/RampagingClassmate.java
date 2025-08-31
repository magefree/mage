package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
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
public final class RampagingClassmate extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_ATTACKING_CREATURE, 1);

    public RampagingClassmate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever this creature attacks, it gets +1/+0 until end of turn for each other attacking creature.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(
                xValue, StaticValue.get(0), Duration.EndOfTurn, "it"
        )));
    }

    private RampagingClassmate(final RampagingClassmate card) {
        super(card);
    }

    @Override
    public RampagingClassmate copy() {
        return new RampagingClassmate(this);
    }
}
