package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.NotMyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.AddCardTypeSourceEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MidnightMangler extends CardImpl {

    public MidnightMangler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{U}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // During turns other than yours, this Vehicle is an artifact creature.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new AddCardTypeSourceEffect(Duration.WhileOnBattlefield, CardType.ARTIFACT, CardType.CREATURE),
                NotMyTurnCondition.instance, "during turns other than yours, this Vehicle is an artifact creature"
        )));

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private MidnightMangler(final MidnightMangler card) {
        super(card);
    }

    @Override
    public MidnightMangler copy() {
        return new MidnightMangler(this);
    }
}
