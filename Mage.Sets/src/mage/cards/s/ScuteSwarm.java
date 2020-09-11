package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.InsectToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScuteSwarm extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            StaticFilters.FILTER_LAND, ComparisonType.MORE_THAN, 5, true
    );
    private static final DynamicValue xValue
            = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND);
    private static final Hint hint = new ValueHint("Lands you control", xValue);

    public ScuteSwarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Landfall — Whenever a land enters the battlefield under your control, create a 1/1 green Insect creature token. If you control six or more lands, create a token that's a copy of Scute Swarm instead.
        this.addAbility(new LandfallAbility(new ConditionalOneShotEffect(
                new CreateTokenCopySourceEffect(), new CreateTokenEffect(new InsectToken()),
                condition, "create a 1/1 green Insect creature token. If you control six or more lands, " +
                "create a token that's a copy of {this} instead"
        )).addHint(hint));
    }

    private ScuteSwarm(final ScuteSwarm card) {
        super(card);
    }

    @Override
    public ScuteSwarm copy() {
        return new ScuteSwarm(this);
    }
}
