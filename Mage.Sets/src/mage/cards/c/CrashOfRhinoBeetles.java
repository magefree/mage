package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.SubType;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author TheElk801
 */
public final class CrashOfRhinoBeetles extends CardImpl {

    public CrashOfRhinoBeetles(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Crash of Rhino Beetles gets +10/+10 as long as you control ten or more lands.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new BoostSourceEffect(
                                10, 10, Duration.WhileOnBattlefield
                        ),
                        new PermanentsOnTheBattlefieldCondition(
                                StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND,
                                ComparisonType.MORE_THAN, 9
                        ),
                        "{this} gets +10/+10 as long as you control ten or more lands"
                )
        ));
    }

    private CrashOfRhinoBeetles(final CrashOfRhinoBeetles card) {
        super(card);
    }

    @Override
    public CrashOfRhinoBeetles copy() {
        return new CrashOfRhinoBeetles(this);
    }
}
