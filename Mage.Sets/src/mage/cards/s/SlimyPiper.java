package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.abilities.keyword.IndestructibleAbility;
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
public final class SlimyPiper extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            StaticFilters.FILTER_CONTROLLED_CREATURE, ComparisonType.MORE_THAN, 3
    );

    public SlimyPiper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.FUNGUS);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Slimy Piper attacks, it gets +1/+1 until end of turn. If you control four or more creatures, it gets +2/+2 and gains indestructible until end of turn instead.
        this.addAbility(new AttacksTriggeredAbility(new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(
                        new BoostSourceEffect(2, 2, Duration.EndOfTurn),
                        new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn)
                ),
                new AddContinuousEffectToGame(new BoostSourceEffect(1, 1, Duration.EndOfTurn)),
                condition, "it gets +1/+1 until end of turn. If you control four or more creatures, " +
                "it gets +2/+2 and gains indestructible until end of turn instead"
        )).addHint(CreaturesYouControlHint.instance));
    }

    private SlimyPiper(final SlimyPiper card) {
        super(card);
    }

    @Override
    public SlimyPiper copy() {
        return new SlimyPiper(this);
    }
}
