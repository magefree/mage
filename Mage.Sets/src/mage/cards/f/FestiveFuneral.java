package mage.cards.f;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FestiveFuneral extends CardImpl {

    private static final DynamicValue cardsInGraveyard = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CARDS, null);
    private static final DynamicValue xValue = new SignInversionDynamicValue(cardsInGraveyard);
    private static final Hint hint = new ValueHint("Cards in your graveyard", cardsInGraveyard);

    public FestiveFuneral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{B}");

        // Target creature gets -X/-X until end of turn, where X is the number of cards in your graveyard.
        this.getSpellAbility().addEffect(new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(hint);
    }

    private FestiveFuneral(final FestiveFuneral card) {
        super(card);
    }

    @Override
    public FestiveFuneral copy() {
        return new FestiveFuneral(this);
    }
}
