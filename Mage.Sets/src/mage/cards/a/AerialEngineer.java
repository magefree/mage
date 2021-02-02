package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author TheElk801
 */
public final class AerialEngineer extends CardImpl {

    public AerialEngineer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // As long as you control an artifact, Aerial Engineer gets +2/+0 and has flying.
        Ability ability = new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new BoostSourceEffect(2, 0, Duration.WhileOnBattlefield),
                        new PermanentsOnTheBattlefieldCondition(
                                StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT
                        ),
                        "As long as you control an artifact, {this} gets +2/+0"
                )
        );
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(
                        FlyingAbility.getInstance(),
                        Duration.WhileOnBattlefield
                ),
                new PermanentsOnTheBattlefieldCondition(
                        StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT
                ),
                "and has flying"
        ));
        this.addAbility(ability);
    }

    private AerialEngineer(final AerialEngineer card) {
        super(card);
    }

    @Override
    public AerialEngineer copy() {
        return new AerialEngineer(this);
    }
}
