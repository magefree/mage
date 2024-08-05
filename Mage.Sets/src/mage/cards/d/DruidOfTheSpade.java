package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.TrampleAbility;
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
public final class DruidOfTheSpade extends CardImpl {

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_PERMANENT_TOKEN, true);
    private static final Hint hint = new ConditionHint(condition, "You control a token");

    public DruidOfTheSpade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.RABBIT);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // As long as you control a token, Druid of the Spade gets +2/+0 and has trample.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 0, Duration.WhileOnBattlefield),
                condition, "as long as you control a token, {this} gets +2/+0"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(TrampleAbility.getInstance()),
                condition, "and has trample"
        ));
        this.addAbility(ability.addHint(hint));
    }

    private DruidOfTheSpade(final DruidOfTheSpade card) {
        super(card);
    }

    @Override
    public DruidOfTheSpade copy() {
        return new DruidOfTheSpade(this);
    }
}
