package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.permanent.token.BeastieToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TobyBeastieBefriender extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            StaticFilters.FILTER_CREATURE_TOKENS, ComparisonType.MORE_THAN, 3, true
    );
    private static final Hint hint = new ConditionHint(
            condition, "You control four or more creature tokens"
    );

    public TobyBeastieBefriender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Toby, Beastie Befriender enters, create a 4/4 white Beast creature token with "This creature can't attack or block alone."
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new BeastieToken())));

        // As long as you control four or more creature tokens, creature tokens you control have flying.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(new GainAbilityControlledEffect(
                FlyingAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURE_TOKENS
        ), condition, "as long as you control four or more creature tokens, creature tokens you control have flying")).addHint(hint));
    }

    private TobyBeastieBefriender(final TobyBeastieBefriender card) {
        super(card);
    }

    @Override
    public TobyBeastieBefriender copy() {
        return new TobyBeastieBefriender(this);
    }
}
