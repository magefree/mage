package mage.cards.f;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
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

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD, -1);

    public FestiveFuneral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{B}");

        // Target creature gets -X/-X until end of turn, where X is the number of cards in your graveyard.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                xValue, xValue, Duration.EndOfTurn, true
        ).setText("target creature gets -X/-X until end of turn, where X is the number of cards in your graveyard"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private FestiveFuneral(final FestiveFuneral card) {
        super(card);
    }

    @Override
    public FestiveFuneral copy() {
        return new FestiveFuneral(this);
    }
}
