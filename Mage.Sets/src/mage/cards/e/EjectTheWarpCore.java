package mage.cards.e;

import java.util.UUID;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class EjectTheWarpCore extends CardImpl {

    public EjectTheWarpCore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Kicker -- Sacrifice an artifact or creature.
        this.addAbility(new KickerAbility(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE)));

        // Target creature gets -2/-2 until end of turn. If this spell was kicked, that creature gets -6/-6 until end of turn instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(new BoostTargetEffect(-6, -6, Duration.EndOfTurn)),
                new AddContinuousEffectToGame(new BoostTargetEffect(-2, -2, Duration.EndOfTurn)),
                KickedCondition.ONCE, "target creature gets -2/-2 until end of turn. " +
                "If this spell was kicked, that creature gets -6/-6 until end of turn instead"
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("creature that gets -/-"));
    }

    private EjectTheWarpCore(final EjectTheWarpCore card) {
        super(card);
    }

    @Override
    public EjectTheWarpCore copy() {
        return new EjectTheWarpCore(this);
    }
}
