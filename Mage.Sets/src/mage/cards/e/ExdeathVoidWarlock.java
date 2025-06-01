package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExdeathVoidWarlock extends CardImpl {

    private static final Condition condition = new CardsInControllerGraveyardCondition(6, StaticFilters.FILTER_CARD_PERMANENTS);
    private static final Hint hint = new ValueHint(
            "Permanent cards in your graveyard",
            new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_PERMANENT)
    );

    public ExdeathVoidWarlock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.secondSideCardClazz = mage.cards.n.NeoExdeathDimensionsEnd.class;

        // When Exdeath enters, you gain 3 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(3)));

        // At the beginning of your end step, if there are six or more permanent cards in your graveyard, transform Exdeath.
        this.addAbility(new TransformAbility());
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new TransformSourceEffect())
                .withInterveningIf(condition).addHint(hint));
    }

    private ExdeathVoidWarlock(final ExdeathVoidWarlock card) {
        super(card);
    }

    @Override
    public ExdeathVoidWarlock copy() {
        return new ExdeathVoidWarlock(this);
    }
}
