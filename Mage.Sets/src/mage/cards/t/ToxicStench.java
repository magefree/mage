package mage.cards.t;

import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ToxicStench extends CardImpl {

    public ToxicStench(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Target nonblack creature gets -1/-1 until end of turn.
        // Threshold - If seven or more cards are in your graveyard, instead destroy that creature. It can't be regenerated.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DestroyTargetEffect(true),
                new AddContinuousEffectToGame(new BoostTargetEffect(-1, -1)),
                ThresholdCondition.instance, "target nonblack creature gets -1/-1 until end of turn.<br>" +
                AbilityWord.THRESHOLD.formatWord() + "If seven or more cards are in your graveyard, " +
                "instead destroy that creature. It can't be regenerated."
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK));
    }

    private ToxicStench(final ToxicStench card) {
        super(card);
    }

    @Override
    public ToxicStench copy() {
        return new ToxicStench(this);
    }
}
