
package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CloudsculptTechnician extends CardImpl {

    private static final Condition condition =
            new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT);

    public CloudsculptTechnician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.JELLYFISH);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // As long as you control an artifact, this creature gets +1/+0.
        this.addAbility(new SimpleStaticAbility(
                new ConditionalContinuousEffect(
                        new BoostSourceEffect(1, 0, Duration.WhileOnBattlefield),
                        condition, "As long as you control an artifact, this creature gets +1/+0"
                )
        ));
    }

    private CloudsculptTechnician(final CloudsculptTechnician card) {
        super(card);
    }

    @Override
    public CloudsculptTechnician copy() {
        return new CloudsculptTechnician(this);
    }
}
