
package mage.cards.g;

import java.util.UUID;
import mage.abilities.condition.common.CitysBlessingCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.keyword.AscendEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author LevelX2
 */
public final class GoldenDemise extends CardImpl {

    public GoldenDemise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // Ascend
        this.getSpellAbility().addEffect(new AscendEffect());

        // All creatures get -2/-2 until end of turn. If you have the city's blessing, instead only creatures your opponents control get -2/-2 until end of turn.
        FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures your opponents control");
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(
                new BoostAllEffect(-2, -2, Duration.EndOfTurn, filter, false),
                new BoostAllEffect(-2, -2, Duration.EndOfTurn),
                CitysBlessingCondition.instance,
                "All creatures get -2/-2 until end of turn. If you have the city's blessing, instead only creatures your opponents control get -2/-2 until end of turn"
        ));
    }

    public GoldenDemise(final GoldenDemise card) {
        super(card);
    }

    @Override
    public GoldenDemise copy() {
        return new GoldenDemise(this);
    }
}
