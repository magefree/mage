package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.common.LandsYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScorpionSentinel extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND, ComparisonType.MORE_THAN, 7
    );

    public ScorpionSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.SCORPION);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // As long as you control seven or more lands, this creature gets +3/+0.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(3, 0, Duration.WhileOnBattlefield),
                condition, "as long as you control seven or more lands, this creature gets +3/+0"
        )).addHint(LandsYouControlHint.instance));
    }

    private ScorpionSentinel(final ScorpionSentinel card) {
        super(card);
    }

    @Override
    public ScorpionSentinel copy() {
        return new ScorpionSentinel(this);
    }
}
