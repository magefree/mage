package mage.cards.v;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VaynesTreachery extends CardImpl {

    public VaynesTreachery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Kicker--Sacrifice an artifact or creature.
        this.addAbility(new KickerAbility(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE)));

        // Target creature gets -2/-2 until end of turn. If this spell was kicked, that creature gets -6/-6 until end of turn instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(new BoostTargetEffect(-6, -6)),
                new AddContinuousEffectToGame(new BoostTargetEffect(-2, -2)),
                KickedCondition.ONCE, "target creature gets -2/-2 until end of turn. " +
                "If this spell was kicked, that creature gets -6/-6 until end of turn instead"
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private VaynesTreachery(final VaynesTreachery card) {
        super(card);
    }

    @Override
    public VaynesTreachery copy() {
        return new VaynesTreachery(this);
    }
}
