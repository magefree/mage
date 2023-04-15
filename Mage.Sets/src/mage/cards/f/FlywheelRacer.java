package mage.cards.f;

import mage.MageInt;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.ActivateIfConditionManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlywheelRacer extends CardImpl {

    private static final Condition condition = new SourceMatchesFilterCondition(
            "{this} is a creature", StaticFilters.FILTER_PERMANENT_CREATURE
    );

    public FlywheelRacer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {T}: Add one mana of any color. Activate only if Flywheel Racer is a creature.
        this.addAbility(new ActivateIfConditionManaAbility(
                Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(),
                new TapSourceCost(), condition
        ));

        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private FlywheelRacer(final FlywheelRacer card) {
        super(card);
    }

    @Override
    public FlywheelRacer copy() {
        return new FlywheelRacer(this);
    }
}
