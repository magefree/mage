package mage.cards.l;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MoreThanStartingLifeTotalCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.replacement.GainPlusOneLifeReplacementEffect;
import mage.abilities.keyword.LeylineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LeylineOfHope extends CardImpl {

    public LeylineOfHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // If Leyline of Hope is in your opening hand, you may begin the game with it on the battlefield.
        this.addAbility(LeylineAbility.getInstance());

        // If you would gain life, you gain that much life plus 1 instead.
        this.addAbility(new SimpleStaticAbility(new GainPlusOneLifeReplacementEffect()));

        // As long as you have at least 7 life more than your starting life total, creatures you control get +2/+2.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostControlledEffect(2, 2, Duration.WhileOnBattlefield),
                MoreThanStartingLifeTotalCondition.SEVEN, "as long as you have at least " +
                "7 life more than your starting life total, creatures you control get +2/+2"
        )));
    }

    private LeylineOfHope(final LeylineOfHope card) {
        super(card);
    }

    @Override
    public LeylineOfHope copy() {
        return new LeylineOfHope(this);
    }
}
