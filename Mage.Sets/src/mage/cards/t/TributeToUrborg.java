package mage.cards.t;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.KickerAbility;
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
public final class TributeToUrborg extends CardImpl {

    private static final DynamicValue count
            = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD);
    private static final DynamicValue xValue = new MultipliedValue(count, -1);
    private static final Hint hint = new ValueHint("Instant and sorcery cards in your graveyard", count);

    public TributeToUrborg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Kicker {1}{U}
        this.addAbility(new KickerAbility("{1}{U}"));

        // Target creature gets -2/-2 until end of turn. If this spell was kicked, that creature gets an additional -1/-1 until end of turn for each instant and sorcery card in your graveyard.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-2, -2));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn)),
                KickedCondition.ONCE, "If this spell was kicked, that creature gets an additional " +
                "-1/-1 until end of turn for each instant and sorcery card in your graveyard"
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(hint);
    }

    private TributeToUrborg(final TributeToUrborg card) {
        super(card);
    }

    @Override
    public TributeToUrborg copy() {
        return new TributeToUrborg(this);
    }
}
