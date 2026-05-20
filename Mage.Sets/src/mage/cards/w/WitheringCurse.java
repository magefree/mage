package mage.cards.w;

import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WitheringCurse extends CardImpl {

    public WitheringCurse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // All creatures get -2/-2 until end of turn.
        // Infusion -- If you gained life this turn, destroy all creatures instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURE),
                new AddContinuousEffectToGame(new BoostAllEffect(-2, -2, Duration.EndOfTurn)),
                YouGainedLifeCondition.getZero(), "all creatures get -2/-2 until end of turn.<br>" +
                AbilityWord.INFUSION.formatWord() + "If you gained life this turn, destroy all creatures instead"
        ));
        this.getSpellAbility().addWatcher(new PlayerGainedLifeWatcher());
        this.getSpellAbility().addHint(ControllerGainedLifeCount.getHint());
    }

    private WitheringCurse(final WitheringCurse card) {
        super(card);
    }

    @Override
    public WitheringCurse copy() {
        return new WitheringCurse(this);
    }
}
