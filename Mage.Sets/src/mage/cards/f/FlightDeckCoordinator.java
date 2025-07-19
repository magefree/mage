package mage.cards.f;

import mage.MageInt;
import mage.abilities.condition.common.TwoTappedCreaturesCondition;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlightDeckCoordinator extends CardImpl {

    public FlightDeckCoordinator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of your end step, if you control two or more tapped creatures, you gain 2 life.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new GainLifeEffect(2))
                .withInterveningIf(TwoTappedCreaturesCondition.instance).addHint(TwoTappedCreaturesCondition.getHint()));
    }

    private FlightDeckCoordinator(final FlightDeckCoordinator card) {
        super(card);
    }

    @Override
    public FlightDeckCoordinator copy() {
        return new FlightDeckCoordinator(this);
    }
}
