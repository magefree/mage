package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SunlitHoplite extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPlaneswalkerPermanent(SubType.ELSPETH);
    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public SunlitHoplite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // As long as it's your turn, Sunlit Hoplite has first strike.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield),
                MyTurnCondition.instance, "As long as it's your turn, {this} has first strike."
        )).addHint(MyTurnHint.instance));

        // Sunlit Hoplite gets +1/+0 as long as you control an Elspeth planeswalker.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 0, Duration.WhileOnBattlefield),
                condition, "{this} gets +1/+0 as long as you control an Elspeth planeswalker"
        )).addHint(new ConditionHint(condition, "You control an Elspeth planeswalker")));
    }

    private SunlitHoplite(final SunlitHoplite card) {
        super(card);
    }

    @Override
    public SunlitHoplite copy() {
        return new SunlitHoplite(this);
    }
}
