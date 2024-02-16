package mage.cards.g;

import mage.abilities.condition.common.CitysBlessingCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.keyword.AscendEffect;
import mage.abilities.hint.common.CitysBlessingHint;
import mage.abilities.hint.common.PermanentsYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GoldenDemise extends CardImpl {

    public GoldenDemise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // Ascend (If you control ten or more permanents, you get the city’s blessing for the rest of the game.)
        this.getSpellAbility().addEffect(new AscendEffect());
        this.getSpellAbility().addHint(CitysBlessingHint.instance);
        this.getSpellAbility().addHint(PermanentsYouControlHint.instance);

        // All creatures get -2/-2 until end of turn. If you have the city's blessing, instead only creatures your opponents control get -2/-2 until end of turn.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(new BoostAllEffect(-2, -2, Duration.EndOfTurn, StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES, false)),
                new AddContinuousEffectToGame(new BoostAllEffect(-2, -2, Duration.EndOfTurn)),
                CitysBlessingCondition.instance,
                "All creatures get -2/-2 until end of turn. If you have the city's blessing, instead only creatures your opponents control get -2/-2 until end of turn"
        ));
    }

    private GoldenDemise(final GoldenDemise card) {
        super(card);
    }

    @Override
    public GoldenDemise copy() {
        return new GoldenDemise(this);
    }
}
