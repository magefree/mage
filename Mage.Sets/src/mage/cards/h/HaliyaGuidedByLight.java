package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.WarpAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HaliyaGuidedByLight extends CardImpl {

    private static final Condition condition = new YouGainedLifeCondition(ComparisonType.MORE_THAN, 2);

    public HaliyaGuidedByLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Haliya or another creature or artifact you control enters, you gain 1 life.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new GainLifeEffect(1),
                StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE_OR_ARTIFACT,
                false, false
        ));

        // At the beginning of your end step, draw a card if you've gained 3 or more life this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1), condition,
                "draw a card if you've gained 3 or more life this turn"
        )).addHint(ControllerGainedLifeCount.getHint()), new PlayerGainedLifeWatcher());

        // Warp {W}
        this.addAbility(new WarpAbility(this, "{W}"));
    }

    private HaliyaGuidedByLight(final HaliyaGuidedByLight card) {
        super(card);
    }

    @Override
    public HaliyaGuidedByLight copy() {
        return new HaliyaGuidedByLight(this);
    }
}
