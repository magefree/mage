package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LashwhipPredator extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterOpponentsCreaturePermanent("your opponents control three or more creatures"),
            ComparisonType.MORE_THAN, 2
    );
    private static final Hint hint = new ValueHint(
            "Creatures your opponents control",
            new PermanentsOnBattlefieldCount(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE)
    );

    public LashwhipPredator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // This spell costs {2} less to cast if your opponents control three or more creatures.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(2, condition).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true).addHint(hint));

        // Reach
        this.addAbility(ReachAbility.getInstance());
    }

    private LashwhipPredator(final LashwhipPredator card) {
        super(card);
    }

    @Override
    public LashwhipPredator copy() {
        return new LashwhipPredator(this);
    }
}
