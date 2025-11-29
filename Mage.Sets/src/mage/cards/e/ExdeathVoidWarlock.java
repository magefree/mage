package mage.cards.e;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExdeathVoidWarlock extends TransformingDoubleFacedCard {

    private static final Condition condition = new CardsInControllerGraveyardCondition(6, StaticFilters.FILTER_CARD_PERMANENTS);
    private static final Hint hint = new ValueHint(
            "Permanent cards in your graveyard",
            new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_PERMANENT)
    );
    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_PERMANENTS);

    public ExdeathVoidWarlock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPIRIT, SubType.WARLOCK}, "{1}{B}{G}",
                "Neo Exdeath, Dimension's End",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPIRIT, SubType.AVATAR}, "BG"
        );

        // Exdeath, Void Warlock
        this.getLeftHalfCard().setPT(3, 3);

        // When Exdeath enters, you gain 3 life.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(3)));

        // At the beginning of your end step, if there are six or more permanent cards in your graveyard, transform Exdeath.
        this.getLeftHalfCard().addAbility(new BeginningOfEndStepTriggeredAbility(new TransformSourceEffect())
                .withInterveningIf(condition).addHint(hint));

        // Neo Exdeath, Dimension's End
        this.getRightHalfCard().setPT(0, 3);

        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // Neo Exdeath's power is equal to the number of permanent cards in your graveyard.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new SetBasePowerSourceEffect(xValue)));
    }

    private ExdeathVoidWarlock(final ExdeathVoidWarlock card) {
        super(card);
    }

    @Override
    public ExdeathVoidWarlock copy() {
        return new ExdeathVoidWarlock(this);
    }
}
